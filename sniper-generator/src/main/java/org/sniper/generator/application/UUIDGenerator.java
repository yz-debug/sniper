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
 * Create Date : 2017-11-6
 */

package org.sniper.generator.application;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.AbstractGenerator;

/**
 * UUID生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class UUIDGenerator extends AbstractGenerator<String> {
	
	/** 是否无符号生成 */
	private boolean unsigned = true;
	
	/** 是否全大写生成 */
	private boolean upperCase;
	
	public boolean isUnsigned() {
		return unsigned;
	}

	public void setUnsigned(boolean unsigned) {
		this.unsigned = unsigned;
	}

	public boolean isUpperCase() {
		return upperCase;
	}

	public void setUpperCase(boolean upperCase) {
		this.upperCase = upperCase;
	}
	
	@Override
	public String generate() {
		return unsigned ? StringUtils.unsignedUUID(upperCase) : StringUtils.UUID(upperCase);
	}
	
	@Override
	public List<String> batchGenerate(int count) {
		checkBatchCount(count);
		
		List<String> results = CollectionUtils.newArrayList(count);
		if (unsigned) {
			if (upperCase) {
				for (int i = 0; i < count; i++) {
					results.add(StringUtils.unsignedUUIDUpperCase());
				}
			} else {
				for (int i = 0; i < count; i++) {
					results.add(StringUtils.unsignedUUID());
				}
			}
		} else {
			if (upperCase) {
				for (int i = 0; i < count; i++) {
					results.add(StringUtils.UUIDUpperCase());
				}
			} else {
				for (int i = 0; i < count; i++) {
					results.add(StringUtils.UUID());
				}
			}
		}
		
		return results;
	}
	
}
