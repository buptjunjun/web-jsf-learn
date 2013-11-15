package org.junjun.designpattern.singleton;

/**
 * 
 * ʹ�����ַ�ʽ,SingletonClass����SingletonClass���ʼ����ʱ��ʵ������
 * ע����Lazy��ʽ������
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
