package org.junjun.designpattern.singleton;

import java.io.IOException;

/**
 * 
 * 使用这种方式,SingletonClass将在SingletonClass类初始化的时候实例化。
 * 注意与Lazy方式的区别。
 * @author junjun
 *
 */
public class JDKSingletonClass {

	public static void main(String [] args) throws IOException
	{
		Runtime  runtime = Runtime.getRuntime();
		System.out.println("processors:"+runtime.availableProcessors());
	}
}
