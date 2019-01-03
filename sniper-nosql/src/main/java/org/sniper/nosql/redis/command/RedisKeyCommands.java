/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-3-30
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis键命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisKeyCommands {
	
	/**
	 * 获取当前库中所有的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <K> Set<K> keys();
	
	/**
	 * 获取当前库中所有指定类型的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keys(Class<K> keyType);
	
	/**
	 * 获取指定库中所有的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	public <K> Set<K> keys(String dbName);
	
	/**
	 * 获取库中所有指定类型的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keys(String dbName, Class<K> keyType);
	
	/**
	 * 获取当前库中指定模式的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keysByPattern(String pattern);
	
	/**
	 * 获取当前库中指定模式和类型的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keysByPattern(String pattern, Class<K> keyType);
	
	/**
	 * 获取指定库中满足模式的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keysByPattern(String dbName, String pattern);
	
	/**
	 * 获取指定库中满足模式和类型的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param pattern
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keysByPattern(String dbName, String pattern, Class<K> keyType);
	
	/**
	 * 删除当前库指定的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long del(K key);
	
	/**
	 * 删除指定库的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long del(String dbName, K key);
	
	/**
	 * 删除当前库指定的多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long del(Collection<K> keys);
	
	/**
	 * 删除指定库多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long del(String dbName, Collection<K> keys);
	
	/**
	 * 删除当前库指定的多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long del(K[] keys);
	
	/**
	 * 删除指定库多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long del(String dbName, K[] keys);
	
	/**
	 * 判断当前库中指定键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(K key);
	
	/**
	 * 判断指定库中的键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(String dbName, K key);
	
	/**
	 * 判断当前库中多个键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Map<K, Boolean> exists(K[] keys);
	
	/**
	 * 判断指定库中多个键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Map<K, Boolean> exists(String dbName, K[] keys);
	
	/**
	 * 判断当前库中多个键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Map<K, Boolean> exists(Collection<K> keys);
	
	/**
	 * 判断指定库中多个键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Map<K, Boolean> exists(String dbName, Collection<K> keys);
	
	/**
	 * 设置当前库中指定键的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(K key, long seconds);
	
	/**
	 * 设置指定库中键的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(String dbName, K key, long seconds);
	
	/**
	 * 设置当前库中指定键的过期时间戳</P>
	 * 注意：参数unixTimestamp为Unix时间戳，即"毫秒时间戳/1000"后的值，
	 * 因此该命令只能达到"秒级过期"控制。如果要进行"毫秒级过期"控制，请使用pExpireAt命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param unixTimestamp Unix时间戳
	 * @return
	 */
	public <K> Boolean expireAt(K key, long unixTimestamp); 
	
	/**
	 * 设置指定库中键的过期时间戳</P>
	 * 注意：参数unixTimestamp为Unix时间戳，即"毫秒时间戳/1000"后的值，
	 * 因此该命令只能满足"秒级过期"控制。如果要进行"毫秒级过期"控制，请使用pExpireAt命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param unixTimestamp Unix时间戳
	 * @return
	 */
	public <K> Boolean expireAt(String dbName, K key, long unixTimestamp); 
	
	/**
	 * 设置当前库中指定键的过期日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(K key, Date date); 
	
	/**
	 * 设置指定库中键的过期日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(String dbName, K key, Date date); 
	
	/**
	 * 设置当前库中指定键的过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param millis
	 * @return
	 */
	public <K> Boolean pExpire(K key, long millis);
	
	/**
	 * 设置指定库中键的过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param millis
	 * @return
	 */
	public <K> Boolean pExpire(String dbName, K key, long millis);
	
	/**
	 * 设置当前库中指定键的过期时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean pExpireAt(K key, long timestamp); 
	
	/**
	 * 设置指定库中键的过期时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean pExpireAt(String dbName, K key, long timestamp); 
	
	/**
	 * 设置当前库中指定键的过期日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean pExpireAt(K key, Date date); 
	
	/**
	 * 设置指定库中键的过期时间日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean pExpireAt(String dbName, K key, Date date); 
		
	/**
	 * 将当前库的键移动到目标库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param targetIndex
	 * @return
	 */
	public <K> Boolean move(K key, int targetIndex);
	
	/**
	 * 将指定库中的键移动到目标库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param targetIndex
	 * @return
	 */
	public <K> Boolean move(String dbName, K key, int targetIndex);
	
	/**
	 * 获取当前库的指定键的剩余秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long ttl(K key);
	
	/**
	 * 获取指定库中的键的剩余秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long ttl(String dbName, K key);
	
	/**
	 * 获取当前库中所有键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <V> List<V> values();
	
	/**
	 * 获取当前库中所有键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param valueType
	 * @return
	 */
	public <V> List<V> values(Class<V> valueType);
	
	/**
	 * 获取指定库中所有键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	public <V> List<V> values(String dbName);
	
	/**
	 * 获取指定库中所有键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueType
	 * @return
	 */
	public <V> List<V> values(String dbName, Class<V> valueType);
	
	/**
	 * 获取当前库中匹配模式键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	public <V> List<V> valuesByPattern(String pattern);
	
	/**
	 * 获取当前库中匹配模式键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @param valueType
	 * @return
	 */
	public <V> List<V> valuesByPattern(String pattern, Class<V> valueType);
	
	/**
	 * 获取指定库中匹配模式键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param pattern
	 * @return
	 */
	public <V> List<V> valuesByPattern(String dbName, String pattern);
	
	/**
	 * 获取指定库中匹配模式键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param pattern
	 * @param valueType
	 * @return
	 */
	public <V> List<V> valuesByPattern(String dbName, String pattern, Class<V> valueType);
	
}
