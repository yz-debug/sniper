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
 * Create Date : 2017-11-13
 */

package org.sniper.generator;

import java.text.MessageFormat;
import java.util.List;

import org.sniper.commons.util.AssertUtils;

/**
 * 参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractParameterizeGenerator<P, T> implements ParameterizeGenerator<P, T> {
	
	/** 全局参数 */
	protected P parameter;
	
	public P getParameter() {
		return parameter;
	}

	public void setParameter(P parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public T generate() {
		return generate(this.parameter);
	}
	
	@Override
	public List<T> batchGenerate(int count) {
		return batchGenerate(this.parameter, count);
	}
	
	@Override
	public List<T> batchGenerate(P parameter, int count) {
		AssertUtils.assertTrue(count > 0,MessageFormat.format(
				"{0} batch generation count [{1}] must greater than 0", this.getClass().getName(), count));
		return doBatchGenerate(parameter, count);
	}

	/**
	 * 执行批量生成操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param count
	 * @return
	 */
	protected abstract List<T> doBatchGenerate(P parameter, int count);
	
}
