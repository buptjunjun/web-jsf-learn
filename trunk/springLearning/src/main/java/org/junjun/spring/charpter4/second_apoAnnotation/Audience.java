package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ���� ʹ��annotation��ʵ��aop
 * @author junjun
 *
 */
@Component
@Aspect
public class Audience
{
	/**
	 * ��һ��һ��pointcut ����Ϊperformance()
	 */
	@Pointcut("execution(* org.junjun.spring.charpter2.first_competetion.Performer.perform(..))")
	public void performance(){};
	
	/**
	 * ��performance֮ǰִ��
	 * performance()��ǰ�涥һ��pointcut
	 */
	@Before("performance()")
	public void takeSeats()
	{
		System.out.println("The audience is taking their seats");
	}
	
	
	/**
	 * ��performance֮ǰִ��
	 */
	@Before("performance()")
	public void turnOffCellphone()
	{
		System.out.println("The audience is  turning off cellphone");
	}
	
	/**
	 * ��performance֮��ִ��
	 */
	@AfterReturning("performance()")
	public void applaud()
	{
		System.out.println("clap clap.....");
	}
	
	/**
	 * ��performance֮��ִ��
	 */
	@AfterThrowing("performance()")
	public void demandRefund()
	{
		System.out.println("I want our money back");
	}
}
