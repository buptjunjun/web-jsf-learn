package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 观众 使用aop的annotation功能
 * @author junjun
 *
 */
@Component
@Aspect
public class Audience1
{
	/**
	 * 指定一个pointcut 名字为performance()
	 */
	@Pointcut("execution(* org.junjun.spring.charpter2.first_competetion.Performer.perform(..))")
	public void performance(){};
	
	/**
	 * 使用 aop:around
	 */
	@Around("performance()")
	public void watchPerformance(ProceedingJoinPoint jointpoint)
	{
		try
		{
		System.out.println("The audience is taking their seats. (around)");
		System.out.println("The audience is turning off their cellphones (around)");
		//记录时间
		long start = System.currentTimeMillis();
		
		//调用performer.perform()
		jointpoint.proceed();
		
		//还可以调用2次
		jointpoint.proceed();
		
		//记录时间
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
