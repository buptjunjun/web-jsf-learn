package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 观众 使用annotation来实现aop
 * @author junjun
 *
 */
@Component
@Aspect
public class Audience
{
	/**
	 * 顶一个一个pointcut 名字为performance()
	 */
	@Pointcut("execution(* org.junjun.spring.charpter2.first_competetion.Performer.perform(..))")
	public void performance(){};
	
	/**
	 * 在performance之前执行
	 * performance()是前面顶一个pointcut
	 */
	@Before("performance()")
	public void takeSeats()
	{
		System.out.println("The audience is taking their seats");
	}
	
	
	/**
	 * 在performance之前执行
	 */
	@Before("performance()")
	public void turnOffCellphone()
	{
		System.out.println("The audience is  turning off cellphone");
	}
	
	/**
	 * 在performance之后执行
	 */
	@AfterReturning("performance()")
	public void applaud()
	{
		System.out.println("clap clap.....");
	}
	
	/**
	 * 在performance之后执行
	 */
	@AfterThrowing("performance()")
	public void demandRefund()
	{
		System.out.println("I want our money back");
	}
}
