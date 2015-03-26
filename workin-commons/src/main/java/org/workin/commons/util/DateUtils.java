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
 * Create Date : 2014-9-28
 */

package org.workin.commons.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 日期时间工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class DateUtils {
	
	/** 默认的年格式 */
	public static final String DEFAULT_YEAR_FORMAT = "yyyy";
	
	/** 默认的日期格式 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/** 默认的日期时间格式 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/** 默认的日期时间(带毫秒)格式 */
	public static final String DEFAULT_DATETIME_PLUS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
	
	/** 星期一字符串 */
	public static final String MONDAY = "Monday";
	
	/** 星期二字符串 */
	public static final String TUESDAY = "Tuesday";
	
	/** 星期三字符串 */
	public static final String WEDNESDAY = "Wednesday";
	
	/** 星期四字符串 */
	public static final String THURSDAY = "Thursday";
	
	/** 星期五字符串 */
	public static final String FRIDAY = "Friday";
	
	/** 星期六字符串 */
	public static final String SATURDAY = "Saturday";
	
	/** 星期日字符串 */
	public static final String SUNDAY = "Sunday";
	
	/** 模式与日期时间格式关系映射集线程局部变量 */
	private static final ThreadLocal<Map<String, SimpleDateFormat>> dateFormates = new ThreadLocal<Map<String,SimpleDateFormat>>();
	
	/** 计量单位与毫秒时间的映射关系 */
	private static final Map<String,Long> UM_MS = new HashMap<String, Long>();
	
	static {
		
		UM_MS.put("ms", 1L);
		UM_MS.put("msec", 1L);
		
		/* 每秒的毫秒数 */
		UM_MS.put("s", 1000L);
		UM_MS.put("sec", 1000L);
		
		/* 每分钟的毫秒数 */
		UM_MS.put("min", 60000L);
		
		/* 每小时的毫秒数 */
		UM_MS.put("h", 3600000L);
		UM_MS.put("hr", 3600000L);
		
		/* 每天的毫秒数 */
		UM_MS.put("d", 86400000L);
		UM_MS.put("day", 86400000L);
		
		/* 每周的毫秒数 */
		UM_MS.put("w", 604800000L);
		
		/* 每个小月的毫秒数 */
		UM_MS.put("m", 2592000000L);
		UM_MS.put("lm", 2592000000L);
		/* 每个大月的毫秒数 */
		UM_MS.put("bm", 2678400000L);
		
		/* 二月的毫秒数 */
		UM_MS.put("feb", 2419200000L);
		/* 闰年二月的毫秒数 */
		UM_MS.put("lyFeb", 2505600000L);
		
		/* 一个平年的毫秒数 */
		UM_MS.put("y", 31536000000L);
		/* 一个闰年的毫秒数 */
		UM_MS.put("ly", 31622400000L);
		
	}
	
	/**
	 * @description 根据指定的模式在当前线程的局部变量中获取已定义的日期格式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern))
			pattern = DEFAULT_DATETIME_FORMAT;
		
		Map<String, SimpleDateFormat> formateMap = dateFormates.get();
		if (formateMap == null)
			formateMap = MapUtils.newConcurrentHashMap();
		
		if (formateMap.get(pattern) == null) {
			formateMap.put(pattern, new SimpleDateFormat(pattern));
			dateFormates.set(formateMap);
		}
		
		return dateFormates.get().get(pattern);
	}
	
	/**
	 * @description 以默认格式将字符串转换成Date对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @return
	 */
	public static Date stringToDate(String dateString){
		return stringToDate(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * @description 以指定的格式将字符串转换成Date对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String dateString, String pattern) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(dateString), 
				"Date string can not be null or blank.");
		return getDateFormat(pattern).parse(dateString, new ParsePosition(0));
	}
	
	/**
	 * @description 以默认格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @return 
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * @description 以指定格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @param pattern
	 * @return 
	 */
	public static String dateToString(Date date, String pattern) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		return getDateFormat(pattern).format(date);
	}
	
	/**
	 * @description 以默认格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param time
	 * @return 
	 */
	public static String timeToString(long time) {
		return timeToString(time, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * @description 以指定格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param time
	 * @param pattern
	 * @return 
	 */
	public static String timeToString(long time, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time); 
		return dateToString(calendar.getTime(), pattern);			
	}
	
	/**
	 * @description 以默认格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param dateString
	 * @return 
	 */
	public static long stringToMillis(String dateString) {
		return stringToMillis(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * @description 以指定格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param dateString
	 * @param pattern
	 * @return 
	 */
	public static long stringToMillis(String dateString, String pattern) {
		Date date = stringToDate(dateString, pattern);
		return date != null ? date.getTime() : 0L;
	}
	
	/**
	 * @description 判断两个日期是否为同一天
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param now
	 * @param then
	 * @return 
	 */
	public static boolean isSameDay(Date now, Date then) {
		if (now == null || then == null)
			return false;
		
		return getIntervalMillis(now, then) / UM_MS.get("day") == 0;
	}
	
	/**
	 * @description 判断指定的日期是否为今天
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @return 
	 */
	public static boolean isToday(Date date) {
		return isSameDay(new Date(), date);
	}
	
	/**
	 * @description 计算两日期间隔的毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param start
	 * @param end
	 * @return 
	 */
	public static long getIntervalMillis(Date start, Date end) {
		AssertUtils.assertNotNull(start, "Start date must not be null.");
		AssertUtils.assertNotNull(end, "End date must not be null.");
		return Math.abs(end.getTime() - start.getTime());
	}
	
	/**
	 * @description 计算两日期间隔的秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getIntervalSecond(Date start, Date end) {
		return getIntervalMillis(start, end) / UM_MS.get("sec");
	}
	
	/**
	 * @description 计算两日期间隔的分钟数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getIntervalMinute(Date start, Date end) {
		return getIntervalMillis(start, end) / UM_MS.get("min");
	}
	
	/**
	 * @description 计算两日期间隔的小时数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getIntervalHour(Date start, Date end) {
		return getIntervalMillis(start, end) / UM_MS.get("hr");
	}
	
	/**
	 * @description 计算两日期间隔的天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getIntervalDay(Date start, Date end) {
		return getIntervalMillis(start, end) / UM_MS.get("day");
	}
	
	/**
	 * @description 计算两日期间隔的周数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getIntervalWeek(Date start, Date end) {
		return getIntervalMillis(start, end) / UM_MS.get("w");
	}
	
	/**
	 * @description 获取计量单位对应的毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param um
	 * @return 
	 */
	public static long unitMillis(String um) {
		Long ms = UM_MS.get(um);
		return ms != null ? ms : UM_MS.get("ms");
	}
	
	/**
	 * @description 将单位时间换算成毫秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time
	 * @param um
	 * @return
	 */
	public static long getMillis(long time, String um) {
		return time * unitMillis(um);
	}
	
	/**
	 * @description 将单位时间换算成秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time
	 * @param um
	 * @return
	 */
	public static long getSecond(long time, String um) {
		return getMillis(time, um) / unitMillis("sec");
	}
	
	/**
	 * @description 获取指定日期这周内每一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param now
	 * @return
	 */
	public static Date[] getEveryDayOfWeek(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		// 本周第一天(星期天)的字段编号
		int day = calendar.getFirstDayOfWeek();
		Date []dayOfWeek = new Date[7];
		for (int i = 0; i < 7; i++) {
			calendar.set(Calendar.DAY_OF_WEEK, day++);
			dayOfWeek[i] = calendar.getTime();
		}
		return dayOfWeek;
	}
	
	/**
	 * @description 判断指定的日期是否为一个周末
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		if (date == null)
			return false;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 当前日期在本周的字段编号
		int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
		return currentDay == Calendar.SATURDAY || currentDay == Calendar.SUNDAY;
	}
		
	/**
	 * @description 获取指定日期所在周的周末
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date[] getWeekend(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		Date[] dates = new Date[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		dates[0] = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		dates[1] = calendar.getTime();
		return dates;
	}
	
	/**
	 * @description 获取指定日期所在周内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param weekday
	 * @return
	 */
	public static Date getDayOfWeek(Date date, int weekday) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		if (weekday > 0)
			calendar.set(Calendar.DAY_OF_WEEK, weekday + 1);
		else
			// 本周第一天
			calendar.set(Calendar.DAY_OF_WEEK, 2);
		return calendar.getTime();
	}
	
	/**
	 * @description 获取在指定日期第几天以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param offset 距离指定日期之前(负数)或之后(正数)的天数
	 * @return
	 */
	public static Date getOffsetDay(Date date, int offset) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, offset);
		return calendar.getTime();
	}
	
	/**
	 * @description 获取昨天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date getYesterday(){
		return getOffsetDay(new Date(), -1);
	}
	
	/**
	 * @description 获取明天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date getTomorrow(){
		return getOffsetDay(new Date(), 1);
	}
	
	/**
	 * @description 判断指定日期是否为闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(Date date) {
		if (date == null)
			return false;
		return isLeapYear(Integer.valueOf(dateToString(date, "yyyy")));
	}
	
	/**
	 * @description 判断指定数字是否为闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * @description 计算两日期间经历有多少个闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getLeapYearCount(Date start, Date end) {
		if (start == null)
			return isLeapYear(end) ? 1 : 0;
		if (end == null)
			return isLeapYear(start) ? 1 : 0;
		
		int startYear = Integer.valueOf(dateToString(start, DEFAULT_YEAR_FORMAT));
		int endYear = Integer.valueOf(dateToString(end, DEFAULT_YEAR_FORMAT));
		if (startYear == endYear)
			return isLeapYear(startYear) ? 1 : 0;
		
		int leapYears = 0;
		int offset = Math.abs(startYear - endYear);
		int year = Math.min(startYear, endYear);
		do {
			if (isLeapYear(year++))
				leapYears++;
			offset--;
		} while (offset > -1);
		return leapYears;
	}
	
	/**
	 * @description 获取两日期间经历的所有闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date[] getLeapYears(Date start, Date end) {
		
		if (start == null)
			return isLeapYear(end) ? new Date[] { end } : new Date[] {};
		if (end == null)
			return isLeapYear(start) ? new Date[] { start } : new Date[] {};
			
		int startYear = Integer.valueOf(dateToString(start, DEFAULT_YEAR_FORMAT));
		int endYear = Integer.valueOf(dateToString(end, DEFAULT_YEAR_FORMAT));
		if (startYear == endYear)
			return isLeapYear(startYear) ? new Date[] { start } : new Date[] {};
		
		int offset = Math.abs(startYear - endYear);
		int year = Math.min(startYear, endYear);
		List<Date> leapYears = new ArrayList<Date>();
		do {
			if (isLeapYear(year))
				leapYears.add(stringToDate(String.valueOf(year), DEFAULT_YEAR_FORMAT));
			year++;
			offset--;
		} while (offset > -1);
		return leapYears.toArray(new Date[] {});
	}
	
	/**
	 * @description 获取指定的日期是星期几
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param now
	 * @return
	 */
	public static String getDayOfWeek(Date now){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		String result = null;
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case 1:
				result = SUNDAY;
				break;
			case 2:
				result = MONDAY;
				break;
			case 3:
				result = TUESDAY;
				break;
			case 4:
				result = WEDNESDAY;
				break;
			case 5:
				result = THURSDAY;
				break;
			case 6:
				result = FRIDAY;
				break;
			case 7:
				result = SATURDAY;
				break;
		}
		return result;
	}
	
	public static void main(String[] args) {
		Date start = new Date();
		for (int i = 0; i < 100000; i++)
			dateToString(new Date());
		System.out.println(getIntervalMillis(start, new Date()));
	}
				
}