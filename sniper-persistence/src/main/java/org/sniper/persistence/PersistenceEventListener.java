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
 * Create Date : 2015-1-27
 */

package org.sniper.persistence;

/**
 * 持久化事件监听器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PersistenceEventListener<T> {
	
	/**
	 * 实体准备进行新增操作之前触发
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void onPrePersist(T entity);
	
	/**
	 * 实体准备进行更新操作之前触发
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void onPreUpdate(T entity);
	
	/**
	 * 实体准备进行删除操作之前触发
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void onPreRemove(T entity); 
	
	/**
	 * 准备进行销毁之前触发
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void onPreDestroy();

}
