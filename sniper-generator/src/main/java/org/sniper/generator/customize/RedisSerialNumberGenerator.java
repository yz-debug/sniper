/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-11-14
 */

package org.sniper.generator.customize;

import org.sniper.commons.util.AssertUtils;
import org.sniper.generator.dimension.DateDimensionGenerator;
import org.sniper.generator.dimension.DimensionGenerator;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * Redis流水号生成器实现类，生成结果满足几点要求：</P>
 * 1.在指定的维度区间内流水号是唯一的；</P>
 * 2.在指定的维度区间内流水号是趋势递增的；</P>
 * 3.在指定的维度区间内流水号是连续的；</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSerialNumberGenerator extends AbstractCustomizeNumberGenerator {
	
	private final RedisCommandsDao redisCommandsDao;
	
	public RedisSerialNumberGenerator(RedisCommandsDao redisCommandsDao) {
		this(DEFAULT_MIN_LENGTH, redisCommandsDao);
	}
		
	public RedisSerialNumberGenerator(int minLength, RedisCommandsDao redisCommandsDao) {
		this(minLength, new DateDimensionGenerator(), redisCommandsDao);
	}
	
	public RedisSerialNumberGenerator(DimensionGenerator<?> dimensionGenerator, RedisCommandsDao redisCommandsDao) {
		this(DEFAULT_MIN_LENGTH, dimensionGenerator, redisCommandsDao);
	}
	
	public RedisSerialNumberGenerator(int minLength, DimensionGenerator<?> dimensionGenerator, RedisCommandsDao redisCommandsDao) {
		super(minLength, dimensionGenerator);
		
		AssertUtils.assertNotNull(redisCommandsDao, "Redis commands dao not be null");
		this.redisCommandsDao = redisCommandsDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T generateByDimension(Object dimension) {
		return (T) redisCommandsDao.incr(dimension);
	}
	
}
