package org.junjun.spring.charpter4.first_apowithxml;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ����
 * @author junjun
 *
 */
public class Audience1
{
	/**
	 * ʹ�� aop:around
	 */
	public void watchPerformance(ProceedingJoinPoint jointpoint)
	{
		try
		{
		System.out.println("The audience is taking their seats.");
		System.out.println("The audience is turning off their cellphones");
		//��¼ʱ��
		long start = System.currentTimeMillis();
		
		//����performer.perform()
		jointpoint.proceed();
		
		//�����Ե���2��
		jointpoint.proceed();
		
		//��¼ʱ��
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
