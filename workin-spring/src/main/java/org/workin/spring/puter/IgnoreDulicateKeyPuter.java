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
 * Create Date : 2015-7-1
 */

package org.workin.spring.puter;

import java.util.Map;

/**
 * @description 重复键处理器，当put键出现重复时将直接被忽略不处理
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class IgnoreDulicateKeyPuter implements DulicateKeyPuter {

	@Override
	public void put(Map<Object, Object> constant, Object key, Object value) {
		if (!constant.containsKey(key))
			constant.put(key, value);
	}

}