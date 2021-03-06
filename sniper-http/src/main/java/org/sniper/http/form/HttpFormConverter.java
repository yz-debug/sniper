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
 * Create Date : 2015-7-7
 */

package org.sniper.http.form;

import java.util.Map;

/**
 * HTTP表单转换器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpFormConverter {
	
	/**
	 * 将表单映射集转换成URL字符串映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param formMap
	 * @return
	 */
	public Map<String, String> convert(Map<String, HttpForm> formMap);

	/** 
	 * 将单个表单转换成URL字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param form
	 * @return 
	 */
	public String convert(String name, HttpForm form);
	
}
