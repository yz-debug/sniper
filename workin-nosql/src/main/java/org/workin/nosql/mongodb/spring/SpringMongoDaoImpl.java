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
 * Create Date : 2016-8-25
 */

package org.workin.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.List;

import org.workin.nosql.mongodb.dao.MongoDao;

import com.mongodb.WriteResult;

/**
 * @description Spring MongoDB数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SpringMongoDaoImpl<T, PK extends Serializable> extends
		SpringMongoDaoSupport<T> implements MongoDao<T, PK> {

	@Override
	public void save(T entity) {
		getMongoTemplate().save(entity);
	}

	@Override
	public T findById(PK primaryKey) {
		return getMongoTemplate().findById(primaryKey, getBeanClass());
	}

	@Override
	public List<T> findAll() {
		return getMongoTemplate().findAll(getBeanClass());
	}

	@Override
	public void save(T entity, String collection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WriteResult remove(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param collection
	 * @return 
	 */
	@Override
	public WriteResult remove(Object object, String collection) {
		// TODO Auto-generated method stub
		return null;
	}

}
