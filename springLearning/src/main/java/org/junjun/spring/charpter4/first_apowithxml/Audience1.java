package org.junjun.spring.charpter4.first_apowithxml;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 观众
 * @author junjun
 *
 */
public class Audience1
{
	/**
	 * 使用 aop:around
	 */
	public void watchPerformance(ProceedingJoinPoint jointpoint)
	{
		try
		{
		System.out.println("The audience is taking their seats.");
		System.out.println("The audience is turning off their cellphones");
		//记录时间
		long start = System.currentTimeMillis();
		
		//调用performer.perform()
		jointpoint.proceed();
		
		//还可以调用2次
		jointpoint.proceed();
		
		//记录时间
		long end = System.currentTimeMillis();
		
		System.out.println("clap clap ...");
		System.out.println("The performance took " + (end - start)
				+ " milliseconds.");
		}
		catch(Throwable e)
		{
			System.out.println("I want my money back");
		}
	}
	
	
}
