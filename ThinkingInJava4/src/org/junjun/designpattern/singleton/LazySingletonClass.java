package org.junjun.designpattern.singleton;

public class LazySingletonClass {

	private static LazySingletonClass instance = null;
	/**
	 * lazyģʽ�У�����Ҫ��ʱ��Ž���ʵ�������������ʱ����뿼�� ͬ�����⣬��Ϊ�����ͬ�����ܿ���ϵͳ�л���
	 * �����ͬ��ʵ��������ģʽʧ�ܡ�
	 * @return
	 */
	static public synchronized  LazySingletonClass getInstance()
	{
		
		if(instance == null)
			instance = new LazySingletonClass();
		return  instance;
	}
}
