package org.buptjunjun.exception;
/**
 * ���� exception����
 * @author junjun
 *
 */

/**
 * �򵥲��ṩ�κη���
 */
class SimpleException extends Exception {}

/**
 * �ṩ�������Ĺ��췽��
 * @author junjun
 *
 */

public class ExceptionTest1
{
	static public void func()throws MyException
	{
		System.out.println("throwing exception from func");
		throw new MyException("Origated in func()");
	}
	public static  void main(String [] args)
	{
		
		try
		{
			throw new SimpleException();
		}
		catch(SimpleException se)
		{
			System.out.println("catch simple exception");
		}
		catch(Exception e)
		{
			//û��ִ��
			System.out.println("catch exception");
		}
		
		System.out.println("--------------------------------");
		try
		{
			func();
		}
		catch(MyException se)
		{
			//��ӡ�� ��exception������ ��ǰcatch�ĵ��õķ�������Ϣ����ջ��Ϣ��
			//printStackTrace������Throwable,����Thread�ĸ���
			se.printStackTrace(System.err);
		}
		catch(Exception e)
		{
			//û��ִ��
			System.out.println("catch exception");
		}
	}
}
