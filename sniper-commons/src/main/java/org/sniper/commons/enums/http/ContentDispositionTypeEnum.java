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
 * Create Date : 2017-8-22
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * Content-Disposition消息头类型枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum ContentDispositionTypeEnum {
	
	/** 内联形式 */
	INLINE("inline"),
	
	/** 附件形式 */
	ATTACHMENT("attachment"),
	
	/** 表单数据 */
	FORM_DATA("form-data");
	
	private static final Map<String, ContentDispositionTypeEnum> mappings = MapUtils.newHashMap(3);
	
	/** 类型模式 */
	private final String mode;
	
	static {
		for (ContentDispositionTypeEnum dispositionType : values()) {
			mappings.put(dispositionType.mode, dispositionType);
		}
	}
	
	private ContentDispositionTypeEnum(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	/**
	 * 判断指定的类型模式是否匹配一个DispositionType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public boolean matches(String name) {
		return this.mode.equalsIgnoreCase(name);
	}

	/**
	 * 将指定的类型模式解析成ContentDispositionTypeEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mode
	 * @return
	 */
	public static ContentDispositionTypeEnum resolve(String mode) {
		return (mode != null ? mappings.get(mode.toLowerCase()) : null);
	}

}
