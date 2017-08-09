/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-1-5
 */

package org.sniper.http.httpclient.v4.handler.request;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.sniper.http.form.HttpForm;

/**
 * 请求处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RequestHandler {
	
	/**
	 * 根据URL和表单对象设置处理HttpEntityEnclosingRequestBase对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpRequest
	 * @param url
	 * @param requestBody
	 * @param form
	 * @throws Exception
	 */
	public void handle(HttpEntityEnclosingRequestBase httpRequest, 
			String url, Object requestBody, HttpForm form) throws Exception;
		
}