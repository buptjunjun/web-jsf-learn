package org.junjun.designpattern.singleton;

/**
 * 
 * 使用这种方式,SingletonClass将在SingletonClass类初始化的时候实例化。
 * 注意与Lazy方式的区别。
 * @author junjun
 *
 */
public class SingletonClass {

	private static SingletonClass instance = new SingletonClass();
	
	static public SingletonClass getInstance()
	{
		return  instance;
	}
}
