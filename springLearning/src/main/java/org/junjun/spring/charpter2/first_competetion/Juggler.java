package org.junjun.spring.charpter2.first_competetion;

/**
 * ��ˣ�� Ҳ�Ǳ�����
 * @author junjun
 *
 */
public class Juggler implements Performer {
	
	//��ˣ�߱��ݵĶ�������
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
