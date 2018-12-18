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
 * Create Date : 2015-2-26
 */

package org.sniper.beans.reflector;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.beans.BeanUtils;
import org.sniper.beans.expression.BeanPropertyHandler;
import org.sniper.beans.expression.DefaultPropertyParser;
import org.sniper.beans.expression.MapPropertyHandler;
import org.sniper.beans.expression.MappedPropertyHandler;
import org.sniper.beans.expression.PropertyHandler;
import org.sniper.beans.expression.PropertyParser;
import org.sniper.commons.exception.NestedNullPointerException;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Bean映射器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultBeanReflector implements BeanReflector {
	
	protected final PropertyParser propertyParser;
	
	protected final List<PropertyHandler<?>> propertyHandlers;
	
	public DefaultBeanReflector() {
		this(null, null);
	}
	
	public DefaultBeanReflector(PropertyParser propertyParser) {
		this(propertyParser, null);
	}
	
	public DefaultBeanReflector(List<PropertyHandler<?>> propertyHandlers) {
		this(null, propertyHandlers);
	}
	
	public DefaultBeanReflector(PropertyParser propertyParser, List<PropertyHandler<?>> propertyHandlers) {
		this.propertyParser = (propertyParser != null ? propertyParser : new DefaultPropertyParser());
		
		if (CollectionUtils.isNotEmpty(propertyHandlers))
			this.propertyHandlers = propertyHandlers;
		else {
			this.propertyHandlers = CollectionUtils.newArrayList();
			
			// TODO 继续添加ParametersPropertyHandler、ArrayPropertyHandler、ListPropertyHandler和IndexedPropertyHandler
			this.propertyHandlers.add(new MapPropertyHandler());
			this.propertyHandlers.add(new MappedPropertyHandler());
			
			this.propertyHandlers.add(new BeanPropertyHandler());
		}
	}
	
	@Override
	public String findGetterName(Object bean, String propertyName) {
		Class<?> currentType = ClassUtils.getCurrentType(bean);
		if (currentType == null)
			return null;
		
		while (propertyParser.hasNested(propertyName)) {
			String nestedPropertyName = propertyParser.resolve(propertyName);
			Class<?> nestedPropertyType = BeanUtils.findPropertyTypeByNameOrGetter(currentType, nestedPropertyName);
			
			/* 如果未获取到成员类型，说明此成员属性以及对应的Getter方法都没有在当前类中定义。
			 * 因此，无法根据当前成员推导出下一级成员，直接返回空结果  */
			if (nestedPropertyType == null)
				return null;
			
			currentType = nestedPropertyType;
			propertyName = propertyParser.next(propertyName);
		}
		
		return BeanUtils.findGetterName(currentType, propertyName);
	}
	
	@Override
	public String findSetterName(Object bean, String propertyName) {
		return findSetterName(bean, propertyName, null);
	}

	@Override
	public String findSetterName(Object bean, String propertyName, Class<?> parameterType) {
		Class<?> currentType = ClassUtils.getCurrentType(bean);
		if (currentType == null)
			return null;
		
		while (propertyParser.hasNested(propertyName)) {
			String nestedPropertyName = propertyParser.resolve(propertyName);
			Class<?> nestedPropertyType = BeanUtils.findPropertyTypeByNameOrSetter(currentType, nestedPropertyName);
			
			/* 如果未获取到成员类型，说明此成员属性以及对应的Setter方法都没有在当前类中定义。
			 * 因此，无法根据当前成员推导出下一级成员，直接返回空结果  */
			if (nestedPropertyType == null)
				return null;
			
			currentType = nestedPropertyType;
			propertyName = propertyParser.next(propertyName);
		}
		
		return BeanUtils.findSetterName(currentType, propertyName, parameterType);
	}
	
	@Override
	public <V> V getPropertyValue(Object bean, String propertyName) throws Exception {
		String currentPropertyName = propertyName;
		while (propertyParser.hasNested(currentPropertyName)) {
			String nestedPropertyName = propertyParser.resolve(currentPropertyName);
			Object nestedPropertyValue = getMemberValue(bean, nestedPropertyName);
			
			/* 嵌套成员值为空时，无法根据当前成员推导出下一级成员，直接抛出异常 */
			if (nestedPropertyValue == null)
				throw new NestedNullPointerException(String.format(
						"Null nested property value for '%s' on bean class '%s'", propertyName, bean.getClass()));
			
			bean = nestedPropertyValue;
			currentPropertyName = propertyParser.next(currentPropertyName);
		}
		
		return getMemberValue(bean, currentPropertyName);
	}
	
	@Override
	public void setPropertyValue(Object bean, String propertyName, Object parameterValue) throws Exception {
		setPropertyValue(bean, propertyName, null, parameterValue);
	}
	
	@Override
	public void setPropertyValue(Object bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		String currentPropertyName = propertyName;
		while (propertyParser.hasNested(currentPropertyName)) {
			String nestedPropertyName = propertyParser.resolve(currentPropertyName);
			Object nestedPropertyValue = getConstructedMemberValue(bean, nestedPropertyName, propertyName);				
			bean = nestedPropertyValue;
			currentPropertyName = propertyParser.next(currentPropertyName);
		}
		
		try {
			setMemberValue(bean, currentPropertyName, parameterType, parameterValue);
		} catch (NestedNullPointerException e) {
			throw new NestedNullPointerException(String.format(
					"Null nested property value for '%s' on bean class '%s'", propertyName, bean.getClass()));
		}
	}
	
	/**
	 * 获取指定对象的成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <V> V getMemberValue(Object obj, String propertyName) throws Exception {
		for (PropertyHandler propertyHandler : propertyHandlers) {
			if (propertyHandler.support(obj, propertyName)) {
				return (V) propertyHandler.getPropertyValue(obj, propertyName);
			}
		}
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 获取指定对象中已构建成功的成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <V> V getConstructedMemberValue(Object obj, String memberName, String propertyName) throws Exception {
		for (PropertyHandler propertyHandler : propertyHandlers) {
			if (propertyHandler.support(obj, memberName)) {
				V memberValue = (V) propertyHandler.getConstructedPropertyValue(obj, memberName);
				if (memberValue == null)
					throw new NestedNullPointerException(String.format(
							"Null nested property value for '%s' on bean class '%s'", propertyName, obj.getClass()));
				
				return memberValue;
			}
		}
		
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, V> T create(String className, Map<String, V> properties) throws Exception {
		AssertUtils.assertNotBlank(className, "Bean class name must not be null or blank");
		
		try {
			return (T) create(Class.forName(className.trim()), properties);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T, V> T create(Class<T> clazz, Map<String, V> properties) throws Exception {
		T bean = ReflectionUtils.newInstance(clazz);
		
		if (MapUtils.isNotEmpty(properties)) {
			Set<Entry<String, V>> propertyValue = properties.entrySet();
			for (Entry<String, V> pv : propertyValue) {
				String propertyName = pv.getKey();
				if (StringUtils.isNotBlank(propertyName))
					try {
						setPropertyValue(bean, propertyName, pv.getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		return bean;
	}
	
	/**
	 * 在指定对象中设置指定属性成员的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param propertyName
	 * @param parameterType
	 * @param parameterValue
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setMemberValue(Object obj, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		for (PropertyHandler propertyHandler : propertyHandlers) {
			if (propertyHandler.support(obj, propertyName)) {
				propertyHandler.setPropertyValue(obj, propertyName, parameterType, parameterValue);
				return;
			}
		}
		
		throw new UnsupportedOperationException();
	}
		
}
