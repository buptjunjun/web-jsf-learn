package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ���� ʹ��aop��annotation����
 * @author junjun
 *
 */
@Component
@Aspect
public class Audience1
{
	/**
	 * ָ��һ��pointcut ����Ϊperformance()
	 */
	@Pointcut("execution(* org.junjun.spring.charpter2.first_competetion.Performer.perform(..))")
	public void performance(){};
	
	/**
	 * ʹ�� aop:around
	 */
	@Around("performance()")
	public void watchPerformance(ProceedingJoinPoint jointpoint)
	{
		try
		{
		System.out.println("The audience is taking their seats. (around)");
		System.out.println("The audience is turning off their cellphones (around)");
		//��¼ʱ��
		long start = System.currentTimeMillis();
		
		//����performer.perform()
		jointpoint.proceed();
		
		//�����Ե���2��
		jointpoint.proceed();
		
		//��¼ʱ��
		long end = System.currentTimeMillis();
		
		System.out.println("clap clap ... (around)");
		System.out.println("The performance took " + (end - start)
				+ " milliseconds. (around)");
		}
		catch(Throwable e)
		{
			System.out.println("I want my money back");
		}
	}
	
	
}
