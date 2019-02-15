/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-1-15
 */

package org.sniper.nosql.test;

import org.junit.Before;
import org.sniper.nosql.redis.command.RedisCommands;
import org.sniper.test.spring.JUnit4SpringTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Redis单元测试抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext-redis.xml" })
public abstract class AbstractRedisTest extends JUnit4SpringTestCase {
	
	@Autowired
	protected RedisCommands redisCommands;
	
	protected final String key = "test";
	
	protected final String[] keys = new String[] { key + "_0", key + "_1", key + "_2", key + "_3"};
	
	protected final String[] values = new String[] { "a", "b", "c", "d", "e" };
	
	protected final long expireTime = 10;
	
	@Before
	public void init() {
		redisCommands.flushAll();
		before();
	}
	
	protected void before() {}

}