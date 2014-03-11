package org.junjun.spring.charpter4.first_apowithxml;
import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * aop:around
 * 动态加入方法
 * @author andyWebsense
 *
 */
public class Main1 {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException 
	{

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter4/aopxml1.xml");
		
		/**
		 * aop:around
		 */
		Performer dukee  = (Performer) app.getBean("dukee");
		dukee.perform();
		
		//动态加入方法
		((Contestant)dukee).receiveReward();
		
	}			
	

}
