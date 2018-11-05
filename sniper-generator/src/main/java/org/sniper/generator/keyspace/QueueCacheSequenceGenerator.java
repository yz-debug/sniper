/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2018-10-30
 */

package org.sniper.generator.keyspace;

import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.lock.ParameterizeLock;

/**
 * 基于队列的缓存序列生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QueueCacheSequenceGenerator<V> extends AbstractCacheableGenerator<Object, Queue<V>, V> {
		
	/** 代理的键空间生成器接口 */
	protected final KeyspaceGenerator<Object, V> keyspaceGenerator;
	
	private final QueueCachePoller queueCachePoller;
	
	public QueueCacheSequenceGenerator(KeyspaceGenerator<Object, V> keyspaceGenerator) {
		this(keyspaceGenerator, true);
	}
	
	public QueueCacheSequenceGenerator(KeyspaceGenerator<Object, V> keyspaceGenerator, boolean fixQueueCache) {
		this(null, keyspaceGenerator, fixQueueCache);
	}

	public QueueCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceGenerator<Object, V> keyspaceGenerator) {
		this(keyLock, keyspaceGenerator, true);
	}
	
	public QueueCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceGenerator<Object, V> keyspaceGenerator, boolean fixQueueCache) {
		super(keyLock, keyspaceGenerator.getDefaultKeyspace());
		this.keyspaceGenerator = keyspaceGenerator;
		this.queueCachePoller = (fixQueueCache ? new FixedQueueCachePoller() : new UnfixedQueueCachePoller());
	}
	
	@Override
	protected V doGenerateByKey(Object key) {
		Queue<V> queue = cache.get(key);
		if (queue == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(key)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					cache.put(key, queue);
					
					/* 队列创建成功后立即进行缓存并返回生成结果，不延迟到下一个keyLock块中进行，
					 * 目的是减少一次加解锁过程提高性能 */
					return queueCachePoller.cacheAndPoll(queue, key);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		/* 由于isEmpty和cache方法组合在一起是非原子性的，
		 * 因此存在多线程"先检查后执行"问题，需加锁操作 */
		keyLock.lock(key);
		try {
			if (queue.isEmpty()) {
				return queueCachePoller.cacheAndPoll(queue, key);
			}
			return queue.poll();
		} finally {
			keyLock.unlock(key);
		}
	}

	@Override
	protected List<V> doBatchGenerateByKey(Object key, int count) {
		Queue<V> queue = cache.get(key);
		if (queue == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(key)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					cache.put(key, queue);
					return queueCachePoller.cacheAndBatchPoll(queue, key, count);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			int queueRemain = queue.size();
			if (queueRemain < count) {
				return queueCachePoller.cacheAndBatchPoll(queue, key, queueRemain, count);
			}
			return queueCachePoller.batchPoll(queue, key, count);
		} finally {
			keyLock.unlock(key);
		}
	}
	
	/**
	 * 基于队列结束的缓存轮询器，其作用在于将KeyspaceGenerator生成的结果缓存到队列中，并获取指定个数(count)的结果。
	 * 现提供如下两种实现方式：</P>
	 * 1.利用键空间生成器批量生成cacheStepSize+count个元素，将第count个元素之前所有的元素作为出列结果，而将之后所有的元素全部缓存入列。
	 * FixedQueueCachePoller， QueueCacheSequenceGenerator默认的内联实现方式；</P>
	 * 2.利用键空间生成器批量生成cacheStepSize个元素，将第count(count < cacheStepSize)个元素之前所有的元素作为出列结果，而将之后所有的元素全部缓存入列；</P>
	 * 
	 * 方式1：本地队列中缓存的元素个数固定为cacheStepSize，生成器生成的元素个数不固定。例如：cacheStepSize=5，当某一次消费方需要生成10(count)个结果时，
	 * 如果队列剩余的个数(queueRemain)不足以满足要求，则生成器实际会先生成cacheStepSize+(count-queueRemain)个元素(queueRemain=0表示队列无任何剩余，此时生成15个元素)
	 * 然后将队列剩余的queueRemain(0)个元素和新生成的前count-queueRemain(10)个元素作为出列结果，最后将第count-queueRemain(5，正好等于cacheStepSize)个元素之后的所有元素全部缓存入列。</P>
	 * 优点：这种方式可以尽最大可能的让消费方从本地缓存队列里直接获取结果，减少生成器的生成频次，性能要优于方式2。</P>
	 * 缺点：由于生成器生成的个数会受消费方影响，因此在宕机和重启恢复的情况下，比方式2造成的丢失范围要大。另外也不方便根据生成器已生成的个数推算出缓存批次。</P>
	 * 
	 * 方式2：本地队列中缓存的元素个数不固定，生成器生成的元素个数固定为n倍cacheStepSize个，其中n=count/cacheStepSize+(1/0)。
	 * 例如：cacheStepSize=3，当某一次消费方需要生成5(count)个结果时，如果队列剩余的个数(queueRemain=2)不足以满足要求， 则生成器会先生成固定的cacheStepSize(3)个元素，
	 * 然后将队列剩余的queueRemain(2)个元素和新生成的前count-queueRemain(3)个元素作为出列结果，最后将第count-queueRemain(3)个元素之后的所有元素全部缓存入列。</P>
	 * 优点：由于生成器生成的个数不会受消费方影响，因此这种方式在宕机和重启恢复的情况下，比方式1造成的丢失范围要小。另外可以很方便的根据生成器已生成的个数和cacheStepSize推算出缓存批次。</P>
	 * 缺点：可能会使本地队列缓存失效， 例如：消费方如果每次要求生成的个数都大于cacheStepSize，则每次在返回出列结果之前，都会使生成器重新新生新元素并进行缓存入列，性能将急剧下降。
	 * 极端情况，当cacheStepSize=1或cacheStepSize=count时，队列实际上是没有缓存任何元素的，这会导致缓存失去意义。</P>
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	protected abstract class QueueCachePoller {
		
		protected final Logger logger;
		
		protected QueueCachePoller() {
			this.logger = LoggerFactory.getLogger(getClass());
		}
		
		/**
		 * 缓存并出列1个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @return
		 */
		protected V cacheAndPoll(Queue<V> queue, Object key) {
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount());
			
			queue.addAll(list.subList(1, list.size()));
			logger.debug("Keyspace '{}' cache {} elements in queue", key, queue.size());
			
			V polled = list.get(0);
			logger.debug("Keyspace '{}' polled a element from list:[{}]", key, polled);
			
			return polled;
		}
		
		/**
		 * 缓存并批量出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<V> cacheAndBatchPoll(Queue<V> queue, Object key, int count) {
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount(count));
			
			queue.addAll(list.subList(count, list.size()));
			logger.debug("Keyspace '{}' cache {} elements in queue", key, queue.size());
			
			List<V> polled = list.subList(0, count);
			logger.debug("Keyspace '{}' batch polled {} elements from list:{}", key, polled.size(), polled);
			
			return polled;
		}
		
		/**
		 * 缓存并批量出列。当队列的剩余个数小于批量出列个数(queueRemain<count)时被调用，其实现方式如下：</P>
		 * 1.如果queueRemain==0，表明队列已经没有剩余元素了，缓存并出列count个结果；</P>
		 * 2.如果queueRemain!=0，表明队列还剩余有元素未出列，缓存并批量出列包括剩余的queueRemain个元素在内的count(总计)个结果。</P>
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param queueRemain
		 * @param count
		 * @return
		 */
		protected List<V> cacheAndBatchPoll(Queue<V> queue, Object key, int queueRemain, int count) {
			if (queueRemain == 0) {
				/* 如果queueRemain==0，表明队列已经没有剩余元素了，
				 * 将调用重载的cacheAndBatchPoll方法缓存并出列count个结果。 */
				logger.debug("Keyspace '{}' nothing remaining element in queue, will cache and batch poll {} elements", key, count);
				return cacheAndBatchPoll(queue, key, count);
			}
			
			// 1.计算出还需要补偿出列的个数(compensateCount)="指定生成的个数-队列剩余的个数"
			int compensateCount = count - queueRemain;
			
			/* 2.计算出批量生成的实际个数后利用代理的键空间生成器批量生成结果 */
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount(compensateCount));
			logger.debug("Keyspace '{}' remaining {} elements in queue, compensate {} elements, generate {} elements",
					queueRemain, compensateCount, list.size());
			
			/* 3.以compensateCount为界，将第compensateCount个元素以后的所有元素全部存入缓存队列 */
			List<V> queueSubList = list.subList(compensateCount, list.size());
			queue.addAll(queueSubList);
			logger.debug("Keyspace '{}' cache {} elements in queue", key, queueSubList.size());
			
			/* 4.从队列中出列前queueRemain个元素，即进行补偿生成前，缓存队列中剩余的元素。
			 * 注意：此步不要放在第2步批量生成结果之前进行，因为如果先进行了剩余元素的出列操作，而后再进行批量生成操作时由于某种原因(例如：异常)导致失败时，
			 * 由于基于队列的出列属于一次性的消费过程，这会导致已出列的剩余元素不能被调用方正常接收到，造成缓存丢失。*/
			List<V> polled = batchPoll(queue, key, queueRemain);
			
			/* 5.再次以compensateCount为界，第compensateCount个元素之前的所有元素是当前批次需要补偿出列的，因此进行出列操作 */
			polled.addAll(list.subList(0, compensateCount));
			logger.debug("Keyspace '{}' batch polled {} elements from queue and list:{}", key, polled.size(), polled);
			
			return polled;
		}
		
		/**
		 * 从队列中批量出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<V> batchPoll(Queue<V> queue, Object key, int count) {
			List<V> list = CollectionUtils.newArrayList(count);
			for (int i = 0; i < count; i++) {
				list.add(queue.poll());
			}
			
			logger.debug("Keyspace '{}' batch polled {} elements from queue:{}", key, list.size(), list);
			return list;
		}
		
		/**
		 * 计算出实际需要批量生成的个数
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @return
		 */
		protected abstract int calculateBatchCount();
		
		/**
		 * 根据指定的个数计算出生成器实际要批量生成的个数
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param count
		 * @return
		 */
		protected abstract int calculateBatchCount(int count);
	}
	
	/**
	 * 固定步长队列缓存轮询器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	private class FixedQueueCachePoller extends QueueCachePoller {
		
		@Override
		protected int calculateBatchCount() {
			return getCacheStepSize() + 1;
		}
		
		@Override
		protected int calculateBatchCount(int count) {
			return getCacheStepSize() + count;
		}
		
	}
	
	/**
	 * 无固定步长队列缓存轮询器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	private class UnfixedQueueCachePoller extends QueueCachePoller {
		
		@Override
		protected int calculateBatchCount() {
			return getCacheStepSize();
		}
		
		@Override
		protected int calculateBatchCount(int count) {
			int cacheStepSize = getCacheStepSize();
			int batchCount = (count / cacheStepSize) + (count % cacheStepSize == 0 ? 0 : 1);
			return cacheStepSize * batchCount;
		}

	}
	
}
