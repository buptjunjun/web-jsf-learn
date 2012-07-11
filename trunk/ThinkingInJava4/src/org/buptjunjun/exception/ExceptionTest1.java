package org.buptjunjun.exception;
/**
 * 测试 exception机制
 * @author junjun
 *
 */

/**
 * 简单不提供任何方法
 */
class SimpleException extends Exception {}

/**
 * 提供带参数的构造方法
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
			//没有执行
			System.out.println("catch exception");
		}
		
		System.out.println("--------------------------------");
		try
		{
			func();
		}
		catch(MyException se)
		{
			//打印出 从exception产生到 当前catch的调用的方法的信息（堆栈信息）
			//printStackTrace是来自Throwable,它是Thread的父类
			se.printStackTrace(System.err);
		}
		catch(Exception e)
		{
			//没有执行
			System.out.println("catch exception");
		}
	}
}
