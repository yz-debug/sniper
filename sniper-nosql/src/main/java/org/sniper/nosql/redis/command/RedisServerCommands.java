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
 * Create Date : 2015-5-11
 */

package org.sniper.nosql.redis.command;

import java.util.Properties;

import org.sniper.nosql.redis.enums.Section;

/**
 * Redis服务终端命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisServerCommands {
	
	/**
	 * 获取Redis服务器的各种信息和统计数值。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Properties info();
	
	/**
	 * 获取Redis服务器指定部分的信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param section
	 * @return
	 */
	public Properties info(Section section);
	
	/**
	 * 获取Redis服务器指定的信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <T> T info(String key);
	
	/**
	 * 获取Redis服务器指定类型的信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param messageType
	 * @return
	 */
	public <T> T info(String key, Class<T> messageType);
	
	/**
	 * 在当前库中执行dbSize命令，获取当前库中键的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Long dbSize();
	
	/**
	 * 在指定库中执行dbSize命令，获取当前库中键的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	public Long dbSize(String dbName);
	
	/**
	 * 执行flushAll命令，清空所有库中的数据
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void flushAll();
	
	/**
	 * 在当前库中执行flushDb命令，清空当前库中的所有数据
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void flushDb();
	
	/**
	 * 在指定库中执行flushDb命令，清空当前库中的所有数据
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 */
	public void flushDb(String dbName);
	
	/**
	 * 执行shutdown命令，关闭服务器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void shutdown();
	
}
