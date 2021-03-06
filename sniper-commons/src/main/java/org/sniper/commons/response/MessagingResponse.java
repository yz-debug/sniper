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
 * Create Date : 2017-3-16
 */

package org.sniper.commons.response;

import org.sniper.commons.constant.status.ExecutionStatus;

/**
 * 消息化响应对象接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MessagingResponse<C> extends GenericResponse<C> {
	
	/** 默认成功消息 */
	public static final String DEFAULT_SUCCESS_MESSAGE = ExecutionStatus.SUCCESS.getMessage();
	
	/** 默认失败消息 */
	public static final String DEFAULT_FAILED_MESSAGE = ExecutionStatus.FAILED.getMessage();
	
	/** 默认异常消息 */
	public static final String DEFAULT_EXCEPTION_MESSAGE = ExecutionStatus.EXCEPTION.getMessage();
	
	/**
	 * 获取响应消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 设置响应消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 */
	public void setMessage(String message);
	
}
