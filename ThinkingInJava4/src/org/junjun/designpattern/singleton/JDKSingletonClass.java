package org.junjun.designpattern.singleton;

import java.io.IOException;

/**
 * 
 * ʹ�����ַ�ʽ,SingletonClass����SingletonClass���ʼ����ʱ��ʵ������
 * ע����Lazy��ʽ������
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
