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

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.MapUtils;

/**
 * Redis哈希命令单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisHashCommandsTest extends AbstractRedisTest {
	
	protected final Map<String, Object> hashKeyValues = MapUtils.newHashMap();
	
	@Override
	protected void before() {
		hashKeyValues.put("id", 1L);
		hashKeyValues.put("name", "dubin");
		hashKeyValues.put("age", 35);
	}
	
//	@Test
	public void testHSetAndHGet() {
		String hashKey = "name";
		String value = "dubin";
		
		assertTrue(redisCommands.hSet(key, hashKey, value));
		assertFalse(redisCommands.hSet(key, hashKey, value));
		
		assertEquals(value, redisCommands.hGet(key, hashKey));
	}
	
//	@Test
	public void testHMSetAndHGetAll() {
		redisCommands.hMSet(key, hashKeyValues);
		
		Map<String, Object> result = redisCommands.hGetAll(key);
		assertEquals(hashKeyValues.size(), result.size());
		System.out.println(result);
	}
	
//	@Test
	public void testHMSetAndHMGet() {
		redisCommands.hMSet(key, hashKeyValues);
		
		List<Object> values = redisCommands.hMGet(key, hashKeyValues.keySet());
		assertEquals(hashKeyValues.size(), values.size());
		System.out.println(values);
	}
	
//	@Test
	public void testHMSetAndHVals() {
		redisCommands.hMSet(key, hashKeyValues);
		
		List<Object> values = redisCommands.hVals(key);
		assertEquals(hashKeyValues.size(), values.size());
		System.out.println(values);
	}
	
//	@Test
	public void testHIncr() {
		int max = 10;
		
		for (int i = 0; i < max; i++) {
			assertEquals((long) (i + 1), redisCommands.hIncr(key, keys[1]));
		}
		System.out.println(redisCommands.hGet(key, keys[1]));
		
		long increment = 5L;
		for (int i = 0; i < max; i++) {
			assertEquals(increment * (i + 1), redisCommands.hIncrBy(key, keys[0], increment));
		}
		System.out.println(redisCommands.hGet(key, keys[0]));
	}
	
	@Test
	public void testHDecr() {
		int max = 10;
		
		for (int i = 0; i < max; i++) {
			assertEquals((long)-(i + 1), redisCommands.hDecr(key, keys[1]));
		}
		System.out.println(redisCommands.hGet(key, keys[1]));
		
		long decrement = 5L;
		for (int i = 0; i < max; i++) {
			assertEquals(-(decrement * (i + 1)), redisCommands.hDecrBy(key, keys[0], decrement));
		}
		System.out.println(redisCommands.hGet(key, keys[0]));
	}

}