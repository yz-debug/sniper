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
 * Create Date : 2016-8-26
 */

package org.workin.nosql.mongodb.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.workin.commons.util.StringUtils;
import org.workin.spring.beans.AbstractGenricBean;

/**
 * Spring MongoDB数据访问支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class SpringMongoDaoSupport<T> extends AbstractGenricBean<T> {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	protected void checkProperties() {
		if (this.mongoTemplate == null)
			throw new IllegalArgumentException("Property 'mongoTemplate' is required");
	}
	
	/**
	 * 获取转换器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected MongoConverter getConverter() {
		return mongoTemplate.getConverter();
	}
	
	/**
	 * 获取映射上下文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected MappingContext<?, ?> getMappingContext() {
		return getConverter().getMappingContext();
	}
	
	/**
	 * 获取当前实体对象对应的MongoDB持久化实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected MongoPersistentEntity<?> getMongoPersistentEntity() {
		return (MongoPersistentEntity<?>) getMappingContext().getPersistentEntity(getBeanClass());
	}
	
	/**
	 * 获取当前实体对象对应的MongoDB主键名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected String getPrimaryKeyName() {		
		MongoPersistentEntity<?> persistentEntity = getMongoPersistentEntity();
		MongoPersistentProperty idProperty = (persistentEntity != null ? persistentEntity.getIdProperty() : null);
		return idProperty != null ? idProperty.getName() : "_id";
	}
	
	/**
	 * 获取当前实体对象属性对应的MongoDB键名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @return
	 */
	protected String getKeyName(String propertyName) {
		if (StringUtils.isBlank(propertyName))
			return StringUtils.EMPTY_STRING;
				
		propertyName = propertyName.trim();
		MongoPersistentEntity<?> persistentEntity = getMongoPersistentEntity();
		MongoPersistentProperty property = (persistentEntity != null ? persistentEntity.getPersistentProperty(propertyName) : null);
		return property != null ? property.getName() : propertyName;
	}
	
}
