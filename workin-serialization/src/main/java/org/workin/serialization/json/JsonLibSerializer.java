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
 * Create Date : 2016-7-12
 */

package org.workin.serialization.json;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.serialization.SerializationException;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 * JsonLib序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonLibSerializer extends AbstractJsonSerializer {
	
	private MorpherRegistry morpherRegistry;
	
	private JsonConfig jsonConfig;
		
	public JsonLibSerializer() {
		buildDefaultMorpher();
		buildDefaultJsonConfig();
	}
	
	public MorpherRegistry getMorpherRegistry() {
		return morpherRegistry;
	}

	public void setMorpherRegistry(MorpherRegistry morpherRegistry) {
		if (morpherRegistry != null)
			this.morpherRegistry = morpherRegistry;
	}

	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(JsonConfig jsonConfig) {
		this.jsonConfig = jsonConfig;
	}
	
	/**
	 * 构建全局默认的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void buildDefaultMorpher() {
		this.morpherRegistry = JSONUtils.getMorpherRegistry();
		this.morpherRegistry.registerMorpher(new DateMorpher(new String[] {
				DateUtils.DEFAULT_DATETIME_FORMAT, DateUtils.DEFAULT_DATE_FORMAT
			}
		)); 
	}
	
	/**
	 * 构建全局默认的JsonConfig
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void buildDefaultJsonConfig() {
		this.jsonConfig = new JsonConfig();
		this.jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			
			@Override
			public Object processObjectValue(String propertyName, Object date, JsonConfig cfg) {
				return date != null ? DateUtils.objectToString(date, getDateFormat()) : date;
			}

			@Override
			public Object processArrayValue(Object date, JsonConfig cfg) {
				return date != null ? DateUtils.objectToString(date, getDateFormat()) : date;
			}
		});
	}
	
	/**
	 * 注册全局的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param morpher
	 */
	public void registerMorpher(Morpher morpher) {
		if (morpher != null)
			morpherRegistry.registerMorpher(morpher);
	}
		
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		return CodecUtils.getBytes(JSONSerializer.toJSON(t, getJsonConfig()).toString(), getEncoding());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String jsonString, Class<T> type) throws SerializationException {
		
		Class<?> clazz = (type != null ? type : getType());
		
		try {
			if (!isJsonArray(jsonString)) {
				if (clazz != null) {
					if (!ClassUtils.isCollection(clazz)) {
						return (T) (!ClassUtils.isArray(clazz) ? 
								beanDeserialize(jsonString, clazz) : beanDeserializeToArray(jsonString, clazz));
					} else 
						// 指定的类型为Collection、List或其它集合类型时，则统一返回Collection<JSONObject>
						return beanDeserializeToCollection(jsonString);
				} else 
					// 指定的类型为null时，则返回JSONObject
					return beanDeserializeToObject(jsonString);
			} else {
				if (clazz != null && !ClassUtils.isCollection(clazz)) {
					return (T) (!ClassUtils.isArray(clazz) ? 
							multipleBeanDeserializeToElementTypeCollection(jsonString, clazz) : multipleBeanDeserializeToArray(jsonString, clazz));
				} else 
					// 指定的类型为null、Collection、List或其它集合类型时，则统一返回Collection<JSONObject>
					return multipleBeanDeserializeToCollection(jsonString, clazz);
			}
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}
	
	/**
	 * 将JsonBean字符串反序列化为指定类型的bean对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserialize(String jsonBean, Class<?> beanClazz) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(jsonBean, getJsonConfig());
		return (T) JSONObject.toBean(jsonObject, beanClazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToArray(String jsonBean, Class<?> arrayClazz) throws Exception {
		Class<?> componentType = arrayClazz.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) beanDeserialize(jsonBean, componentType);
		return (T) array;
	}
	
	/**
	 * 将JsonBean字符串反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToCollection(String jsonBean) throws Exception {
		List<Object> list = CollectionUtils.newArrayList();
		list.add(beanDeserializeToObject(jsonBean));
		return (T) list;
	}
	
	/**
	 * 将JsonBean字符串反序列化为Object
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToObject(String jsonBean) throws Exception {
		return (T) JSONObject.fromObject(jsonBean, getJsonConfig());
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定元素类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToElementTypeCollection(String jsonArray, Class<?> beanClazz) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, getJsonConfig());
		return (T) JSONArray.toCollection(array, beanClazz);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param arrayClazz
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToArray(String jsonArray, Class<?> arrayClazz) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, getJsonConfig());
		return (T) JSONArray.toArray(array, arrayClazz.getComponentType());
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param collectionClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToCollection(String jsonArray, Class<?> collectionClazz) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, getJsonConfig());
		return (T) CollectionUtils.newArrayList(Arrays.asList(array.toArray()));
		
//		JSONArray array = JSONArray.fromObject(jsonArray, getJsonConfig());
		// return List<MorphDynaBean>
//		return (T) JSONArray.toCollection(array, getJsonConfig());
	}
	
}