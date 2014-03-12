package org.junjun.spring.charpter4.second_apoAnnotation;
import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 演示aop的annotation方式
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

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter4/aopAnnotation.xml");
			
		/**
		 * 演示可以处理参数
		 */
		Performer dukee  = (Performer) app.getBean("dukee");
		dukee.perform();
				
		//动态加入方法
		((Contestant)dukee).receiveReward();
		
		/**
		 * annotation 传递参数
		 */
		Thinker volunteer = (Thinker) app.getBean("volunteer");
		volunteer.thinkOfSomething("dada mama");
		MindReader mindReader = (MindReader) app.getBean("magician");
		System.out.println(mindReader.getThoughts());
	}			
	

}
