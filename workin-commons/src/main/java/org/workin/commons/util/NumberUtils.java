/*
 * Copyright 2014 the original author or authors.
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
 * Create Date : 2014-9-27
 */

package org.workin.commons.util;

/**
 * @description 数字工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NumberUtils {
	
	/**
	 * @description 获取不为空的双精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static double safeDouble(Double value) {
		return safeDouble(value, 0);
	}
	
	/**
	 * @description 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double safeDouble(Double value, double defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取不为空的单精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static float safeFloat(Float value) {
		return safeFloat(value, 0);
	}
	
	/**
	 * @description 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float safeFloat(Float value, float defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取不为空的整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value) {
		return safeInteger(value, 0);
	}
	
	/**
	 * @description 获取不为空的整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value, int defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的长整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static long safeLong(Long value) {
		return safeLong(value, 0);
	}
	
	/**
	 * @description 获取不为空的长整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long safeLong(Long value, long defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的短整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static short safeShort(Short value) {
		return safeShort(value, (short) 0);
	}
	
	/**
	 * @description 获取不为空的短整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short safeShort(Short value, short defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的比特数，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static byte safeByte(Byte value) {
		return safeByte(value, (byte) 0);
	}
	
	/**
	 * @description 获取不为空的比特数，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte safeByte(Byte value, byte defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 生成指定值之内的随机双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static double randomIn(double in) {
		return (double) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static float randomIn(float in) {
		return (float) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static int randomIn(int in) {
		return (int) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机长整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static long randomIn(long in) {
		return (long) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机短整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static short randomIn(short in) {
		return (short) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static byte randomIn(byte in) {
		return (byte) (Math.random() * in);
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double minLimit(double d, double limit) {
		return d < limit ? limit : d;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float minLimit(float f, float limit) {
		return f < limit ? limit : f;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int minLimit(int i, int limit) {
		return i < limit ? limit : i;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long minLimit(long l, long limit) {
		return l < limit ? limit : l;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short minLimit(short s, short limit) {
		return s < limit ? limit : s;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte minLimit(byte b, byte limit) {
		return b < limit ? limit : b;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char minLimit(char c, char limit) {
		return c < limit ? limit : c;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double maxLimit(double d, double limit) {
		return d > limit ? limit : d;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float maxLimit(float f, float limit) {
		return f > limit ? limit : f;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int maxLimit(int i, int limit) {
		return i > limit ? limit : i;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long maxLimit(long l, long limit) {
		return l > limit ? limit : l;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short maxLimit(short s, short limit) {
		return s > limit ? limit : s;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte maxLimit(byte b, byte limit) {
		return b > limit ? limit : b;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char maxLimit(char c, char limit) {
		return c > limit ? limit : c;
	}
	
}
