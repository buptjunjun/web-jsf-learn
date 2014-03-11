package org.junjun.spring.charpter4.first_apowithxml;
import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 演示可以处理参数
 * @author andyWebsense
 *
 */
public class Main2 {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException 
	{

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter4/aopxml2.xml");
		
		
		
		/**
		 * 演示可以处理参数
		 */
		System.out.println("演示可以处理参数");
		Thinker volunteer = (Thinker) app.getBean("volunteer");
		volunteer.thinkOfSomething("mama baba");
		MindReader magician = (MindReader) app.getBean("magician");
		System.out.println("MindReader have read :"+magician.getThoughts());
	}			
	

}
