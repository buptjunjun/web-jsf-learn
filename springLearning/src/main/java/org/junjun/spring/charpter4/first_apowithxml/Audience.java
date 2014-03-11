package org.junjun.spring.charpter4.first_apowithxml;

/**
 * 观众
 * @author junjun
 *
 */
public class Audience
{
	/**
	 * 在performance之前执行
	 */
	public void takeSeats()
	{
		System.out.println("The audience is taking their seats");
	}
	
	
	/**
	 * 在performance之前执行
	 */
	public void turnOffCellphone()
	{
		System.out.println("The audience is  turning off cellphone");
	}
	
	/**
	 * 在performance之后执行
	 */
	public void applaud()
	{
		System.out.println("clap clap.....");
	}
	
	/**
	 * 在performance之后执行
	 */
	public void demandRefund()
	{
		System.out.println("I want our money back");
	}
}
