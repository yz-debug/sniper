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
 * Create Date : 2015年8月14日
 */

package org.workin.jms.strategy;

import javax.jms.Destination;

/**
 * @description JMS会话访问策略接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JmsSessionAccessStrategy {
	
	/**
	 * @description 设置是否使用事务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionTransacted
	 */
	public void setSessionTransacted(boolean sessionTransacted);
	
	/**
	 * @description 判断是否使用了事务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isSessionTransacted();
	
	/**
	 * @description 设置会话确认模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionAcknowledgeMode
	 */
	public void setSessionAcknowledgeMode(int sessionAcknowledgeMode);
	
	/**
	 * @description 获取会话确认模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getSessionAcknowledgeMode();
	
	 /**
     * @description 设置消息的目的地
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param destination
     */
    public void setDestination(Destination destination);

    /**
     * @description 获取消息的目的地
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @return
     */
    public Destination getDestination();

}
