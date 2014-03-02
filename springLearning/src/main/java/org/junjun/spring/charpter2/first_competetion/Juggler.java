package org.junjun.spring.charpter2.first_competetion;

/**
 * 杂耍者 也是表演者
 * @author junjun
 *
 */
public class Juggler implements Performer {
	
	//杂耍者表演的豆袋个数
	private int beanBags = 3;
	
	public Juggler() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Juggler( int beanBags) 
	{
		this.beanBags = beanBags;
	}
	
	public void perform() throws PerformanceException
	{
		
		System.out.println("juggler is juggling "+this.beanBags +" bean bags");
	}

}
