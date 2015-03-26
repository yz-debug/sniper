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
 * Create Date : 2014-12-15
 */

package org.workin.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.Channel;
import java.util.List;

/**
 * @description IO工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class IOUtils {
	
	/** 默认缓存区字节大小 */
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	
	/**
	 * @description 创建指定大小的字节缓存区
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bufferSize
	 * @return
	 */
	public static byte[] newByteBuffer(int bufferSize) {
		return new byte[bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE];
	}
	
	/**
	 * @description 创建指定大小的字符缓存区
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bufferSize
	 * @return
	 */
	public static char[] newCharBuffer(int bufferSize) {
		return new char[bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE];
	}
	
	/**
	 * @description 创建有默认字符串编码格式和缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(InputStream in) throws IOException {
		return newBufferedReader(in, MessageUtils.UTF8_ENCODING, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有指定的字符串编码格式和默认缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(InputStream in, String encoding) throws IOException {
		return newBufferedReader(in, encoding, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有默认字符串编码格式和指定缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(InputStream in, int bufferSize) throws IOException {
		return newBufferedReader(in, MessageUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 创建有指定的字符串编码格式和缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(InputStream in, String encoding, int bufferSize) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
		if (bufferSize <= 0)
			bufferSize = DEFAULT_BUFFER_SIZE;
		return new BufferedReader(new InputStreamReader(in, encoding), bufferSize);
	}
	
	/**
	 * @description 创建有默认缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(Reader reader) throws IOException {
		return newBufferedReader(reader, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有指定缓冲区大小的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param bufferSize 缓冲区大小，仅当Reader参数不是BufferedReader的实例时有效
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(Reader reader, int bufferSize) throws IOException {
		BufferedReader bufferedReader;
		if (reader instanceof BufferedReader)
			bufferedReader = (BufferedReader) reader;
		else 
			bufferedReader = new BufferedReader(reader, bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE);
		return bufferedReader;
	}
	
	/**
	 * @description 以默认的字符集编码和缓存区大小创建文件的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(File file) throws IOException {
		return newBufferedReader(new FileInputStream(file));
	}
	
	/**
	 * @description 以指定的字符集编码和默认的缓存区大小创建文件的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(File file, String encoding) throws IOException {
		return newBufferedReader(new FileInputStream(file), encoding);
	}
	
	/**
	 * @description 以默认的字符集编码和指定的缓存区大小创建文件的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(File file, int bufferSize) throws IOException {
		return newBufferedReader(new FileInputStream(file), bufferSize);
	}
	
	/**
	 * @description 以指定的字符集编码和缓存区大小创建文件的BufferedReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader newBufferedReader(File file, String encoding, int bufferSize) throws IOException {
		return newBufferedReader(new FileInputStream(file), encoding, bufferSize);
	}
	
	/**
	 * @description 创建有默认字符串编码格式和缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(OutputStream out) throws IOException {
		return newBufferedWriter(out, MessageUtils.UTF8_ENCODING, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有指定字符串编码格式和默认缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(OutputStream out, String encoding) throws IOException {
		return newBufferedWriter(out, encoding, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有默认字符串编码格式和指定缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(OutputStream out, int bufferSize) throws IOException {
		return newBufferedWriter(out, MessageUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 创建有指定字符串编码格式和缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(OutputStream out, String encoding, int bufferSize) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
		if (bufferSize <= 0)
			bufferSize = DEFAULT_BUFFER_SIZE;
		return new BufferedWriter(new OutputStreamWriter(out, encoding), bufferSize);
	}
	
	/**
	 * @description 创建有默认缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(Writer writer) throws IOException {
		return newBufferedWriter(writer, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 创建有指定缓冲区大小的BufferedWriter对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @param bufferSize 缓冲区大小，仅当Writer参数不是BufferedWriter的实例时有效
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter newBufferedWriter(Writer writer, int bufferSize) throws IOException {
		BufferedWriter bufferedWriter;
		if (writer instanceof BufferedWriter)
			bufferedWriter = (BufferedWriter) writer;
		else 
			bufferedWriter = new BufferedWriter(writer, bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE);
		return bufferedWriter;
	}
	
	/**
	 * @description 根据输入流创建有默认字符集编码和缓冲区大小的LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(InputStream in) throws IOException {
		return newLineNumberReader(in, MessageUtils.UTF8_ENCODING, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 根据输入流创建有指定字符集编码和默认缓冲区大小的LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(InputStream in, String encoding) throws IOException {
		return newLineNumberReader(in, encoding, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 根据输入流创建有默认字符集编码和指定缓冲区大小的LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(InputStream in, int bufferSize) throws IOException {
		return newLineNumberReader(in, MessageUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 根据输入流创建有指定字符集编码和缓冲区大小的LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(InputStream in, String encoding, int bufferSize) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
		if (bufferSize <= 0)
			bufferSize = DEFAULT_BUFFER_SIZE;
		
		return newLineNumberReader(newBufferedReader(in, encoding, bufferSize), bufferSize);
	}
	
	/**
	 * @description 根据读取器创建LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(Reader reader) throws IOException {
		return newLineNumberReader(reader, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 根据读取器创建有指定缓冲区大小的LineNumberReader对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param bufferSize 缓冲区大小，只针对读取器参数不是LineNumberReader和BufferedReader的实例时有效
	 * @return
	 * @throws IOException
	 */
	public static LineNumberReader newLineNumberReader(Reader reader, int bufferSize) throws IOException {
		LineNumberReader lineNumberReader;
		if (reader instanceof LineNumberReader)
			lineNumberReader = (LineNumberReader)reader;
		else {
			if (reader instanceof BufferedReader)
				lineNumberReader = new LineNumberReader(reader);
			else
				lineNumberReader = new LineNumberReader(reader, bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE);
		}
		return lineNumberReader;
	}
	
	/**
	 * @description 读取出输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in) throws IOException {
		return read(newBufferedReader(in, MessageUtils.UTF8_ENCODING, DEFAULT_BUFFER_SIZE));
	}
	
	/**
	 * @description 按指定的字符集编码格式读取出输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in, String encoding) throws IOException {
		return read(newBufferedReader(in, encoding, DEFAULT_BUFFER_SIZE));
	}
	
	/**
	 * @description 按指定的缓存区大小读取出输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in, int bufferSize) throws IOException {
		return read(newBufferedReader(in, MessageUtils.UTF8_ENCODING, bufferSize));
	}
	
	/**
	 * @description 按指定的字符集编码格式和缓存区大小读取出输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in, String encoding, int bufferSize) throws IOException {
		return read(newBufferedReader(in, encoding, bufferSize));
	}
	
	/**
	 * @description 读出读取器的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String read(Reader reader) throws IOException {
		return read(reader, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按指定的缓存区大小读出读取器的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String read(Reader reader, int bufferSize) throws IOException {
		BufferedReader bufferedReader = newBufferedReader(reader, bufferSize);
		StringBuilder lines = new StringBuilder();
		
		String textNewline = SystemUtils.getTextNewline();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (lines.length() > 0)
				lines.append(textNewline);
			lines.append(line);
		}
		return lines.toString();
	}
	
	/**
	 * @description 按行读取出输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream in) throws IOException {
		return readLines(in, MessageUtils.UTF8_ENCODING);
	}
	
	/**
	 * @description 以指定的字符集编码格式按行读取输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream in, String encoding) throws IOException {
		return readLines(in, encoding, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 以指定的缓存区大小按行读取输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream in, int bufferSize) throws IOException {
		return readLines(in, MessageUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 以指定的字符集编码格式和缓存区大小按行读取输入流的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream in, String encoding, int bufferSize) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
				
		return readLines(newBufferedReader(in, encoding, bufferSize));
	}
	
	/**
	 * @description 按行读出读取器的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(Reader reader) throws IOException {
		return readLines(reader, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 以指定的缓存区大小按行读出读取器的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param bufferSize 缓存区大小，仅当Reader参数不是BufferedReader实例时有效
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(Reader reader, int bufferSize) throws IOException {
		BufferedReader bufferedReader = newBufferedReader(reader, bufferSize);
		List<String> lines = CollectionUtils.newArrayList();
		String line;
		while ((line = bufferedReader.readLine()) != null) 
			lines.add(line);
		
		return lines;
	}
	
	/**
	 * @description 将字节数组写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param bytes
	 * @throws IOException
	 */
	public static void write(OutputStream out, byte[] bytes) throws IOException {
		out.write(ArrayUtils.nullToEmpty(bytes));
	}
	
	/**
	 * @description 将字符串写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param content
	 * @throws IOException
	 */
	public static void write(OutputStream out, String content) throws IOException {
		write(out, content, "");
	}
	
	/**
	 * @description 将字符串按指定的编码格式写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param content
	 * @param encoding
	 * @throws IOException
	 */
	public static void write(OutputStream out, String content, String encoding) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
		out.write(StringUtils.safeString(content).getBytes(encoding));
	}
	
	/**
	 * @description 将字符数组输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @param charArray
	 * @throws IOException
	 */
	public static void write(Writer writer, char[] charArray) throws IOException {
		writer.write(ArrayUtils.nullToEmpty(charArray));
		writer.flush();
	}
	
	/**
	 * @description 将字符串输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @param content
	 * @throws IOException
	 */
	public static void write(Writer writer, String content) throws IOException {
		writer.write(StringUtils.safeString(content));
		writer.flush();
	}
	
	/**
	 * @description 将字节数组按默认字符集编码输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @param bytes
	 * @throws IOException
	 */
	public static void write(Writer writer, byte[] bytes) throws IOException {
		write(writer, bytes, "");
	}
	
	/**
	 * @description 将字节数组按指定字符集编码输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @param bytes
	 * @param encoding
	 * @throws IOException
	 */
	public static void write(Writer writer, byte[] bytes, String encoding) throws IOException {
		if (StringUtils.isBlank(encoding))
			encoding = MessageUtils.UTF8_ENCODING;
		writer.write(new String(ArrayUtils.nullToEmpty(bytes), encoding));
		writer.flush();
	}
	
	/**
	 * @description 将输入流的字节写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void write(InputStream in, OutputStream out) throws IOException {
		write(in, out, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 将输入流的字节按指定的缓存区大小读出后写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param out
	 * @param bufferSize
	 * @throws IOException
	 */
	public static void write(InputStream in, OutputStream out, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE];
		int length;
		while ((length = in.read(buffer)) > 0) 
			out.write(buffer, 0, length);
	}

	/**
	 * @description 将输入流的字节按默认的字符集编码格式和缓存区大小读出后输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param writer
	 * @throws IOException
	 */
	public  static void write(InputStream in, Writer writer) throws IOException {
		write(newBufferedReader(in), newBufferedWriter(writer));
	}
	
	/**
	 * @description 将输入流的字节按指定的字符集编码格式和默认的缓存区大小读出后输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param writer
	 * @param encoding 字符集编码，只针对输入流(InputStream)参数有效
	 * @throws IOException
	 */
	public static void write(InputStream in, Writer writer, String encoding) throws IOException {
		write(newBufferedReader(in, encoding), newBufferedWriter(writer));
	}
	
	/**
	 * @description 将输入流的字节按默认的字符集编码格式和指定的缓存区大小读出后输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param writer
	 * @param bufferSize 缓存区大小，对输入流(InputStream)和非缓冲写入流(BufferedWriter)参数有效
	 * @throws IOException
	 */
	public static void write(InputStream in, Writer writer, int bufferSize) throws IOException {
		write(newBufferedReader(in, bufferSize), newBufferedWriter(writer, bufferSize), bufferSize);
	}
	
	/**
	 * @description 将输入流的字节按指定的字符集编码格式和缓存区大小读出后输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param writer
	 * @param encoding 字符集编码，只针对输入流(InputStream)参数有效
	 * @param bufferSize 缓存区大小，对输入流(InputStream)和非缓冲写入器(BufferedWriter)参数有效
	 * @throws IOException
	 */
	public static void write(InputStream in, Writer writer, String encoding, int bufferSize) throws IOException {
		write(newBufferedReader(in, encoding, bufferSize), newBufferedWriter(writer, bufferSize), bufferSize);
	}
	
	/**
	 * @description 将读取器的字符按默认的字符集编码格式和缓存区大小写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param out
	 * @throws IOException
	 */
	public static void write(Reader reader, OutputStream out) throws IOException {
		write(newBufferedReader(reader), newBufferedWriter(out));
	}
	
	/**
	 * @description 将读取器的字符按指定的字符集编码格式和默认的缓存区大小写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param out
	 * @param encoding 
	 * @throws IOException
	 */
	public static void write(Reader reader, OutputStream out, String encoding) throws IOException {
		write(newBufferedReader(reader), newBufferedWriter(out, encoding), DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 将读取器的字符按默认的字符集编码格式和指定的缓存区大小写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param out
	 * @param bufferSize 缓存区大小，对非缓冲读取器(BufferedReader)和写入流(OutputStream)参数有效
	 * @throws IOException
	 */
	public static void write(Reader reader, OutputStream out, int bufferSize) throws IOException {
		write(newBufferedReader(reader, bufferSize), newBufferedWriter(out, bufferSize), DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 将读取器的字符按指定的字符集编码格式和缓存区大小写入到输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param out
	 * @param encoding 字符集编码，只针对输出流(OutputStream)参数有效
	 * @param bufferSize 
	 * @throws IOException
	 */
	public static void write(Reader reader, OutputStream out, String encoding, int bufferSize) throws IOException {
		write(newBufferedReader(reader, bufferSize), newBufferedWriter(out, encoding, bufferSize), bufferSize);
	}
	
	/**
	 * @description 将读取器的字符按默认的缓冲区大小读出后再输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param writer
	 * @throws IOException
	 */
	public static void write(Reader reader, Writer writer) throws IOException {
		write(reader, writer, DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 将读取器的字符按指定的缓冲区大小读出后再输出到写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param writer
	 * @param bufferSize 
	 * @throws IOException
	 */
	public static void write(Reader reader, Writer writer, int bufferSize) throws IOException {
		BufferedReader bufferedReader = newBufferedReader(reader, bufferSize);
		BufferedWriter bufferedWriter = newBufferedWriter(writer, bufferSize);
		
		char[] buffer = new char[bufferSize > 0 ? bufferSize : DEFAULT_BUFFER_SIZE];
		int length;
		while ((length = bufferedReader.read(buffer)) > -1) {
			bufferedWriter.write(buffer,0,length);
			bufferedWriter.flush();
		}
	}
	
	/**
	 * @description 获取BufferedReader对象的总行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static long lines(BufferedReader reader) throws IOException {
		if (reader == null)
			return 0;
		
		long lineNum = 0;
		while (reader.readLine() != null)
			lineNum++;
		
		return lineNum;
	}
	
	/**
	 * @description 关闭输入流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @throws IOException
	 */
	public static void close(InputStream in) throws IOException {
		if (in != null)
			in.close();
	}
	
	/**
	 * @description 关闭输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @throws IOException
	 */
	public static void close(OutputStream out) throws IOException {
		if (out != null)
			out.close();
	}
	
	/**
	 * @description 关闭读取器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @throws IOException
	 */
	public static void close(Reader reader) throws IOException {
		if (reader != null)
			reader.close();
	}
	
	/**
	 * @description 关闭写入器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param writer
	 * @throws IOException
	 */
	public static void close(Writer writer) throws IOException {
		if (writer != null)
			writer.close();
	}
	
	/**
	 * @description 关闭通道
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param channel
	 * @throws IOException
	 */
	public static void close(Channel channel) throws IOException {
		if (channel != null)
			channel.close();
	}
	
}