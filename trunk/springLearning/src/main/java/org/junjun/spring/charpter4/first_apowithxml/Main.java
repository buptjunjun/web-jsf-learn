package org.junjun.spring.charpter4.first_apowithxml;



import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用注解注入
 * @author andyWebsense
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException 
	{

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter4/aopxml.xml");
		Performer dukee  = (Performer) app.getBean("dukee");
		dukee.perform();
	}			
	

}
