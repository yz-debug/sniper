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
 * Create Date : 2015-11-13
 */

package org.workin.payment.signature;

import java.util.Map;

/**
 * @description 签名接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Signature {
	
	/** 
	 * @description 设置签名类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 */
	public void setType(String type);
	
	/**
	 * @description 获取签名类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getType();
	
	/**
	 * @description 按指定的类型将支付参数项进行签名操作，并返回签名结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paymentParameters
	 * @param type
	 * @return
	 */
	public String excute(Map<String, Object> paymentParameters);
	
}
