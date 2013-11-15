package org.junjun.designpattern.singleton;

public class LazySingletonClass {

	private static LazySingletonClass instance = null;
	/**
	 * lazy模式中，在需要的时候才进行实例化，但是这个时候必须考虑 同步问题，因为如果不同步，很可能系统中会有
	 * 多个不同的实例，单例模式失败。
	 * @return
	 */
	static public synchronized  LazySingletonClass getInstance()
	{
		
		if(instance == null)
			instance = new LazySingletonClass();
		return  instance;
	}
}
