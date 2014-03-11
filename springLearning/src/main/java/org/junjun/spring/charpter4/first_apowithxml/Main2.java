package org.junjun.spring.charpter4.first_apowithxml;
import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * ��ʾ���Դ������
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
		 * ��ʾ���Դ������
		 */
		System.out.println("��ʾ���Դ������");
		Thinker volunteer = (Thinker) app.getBean("volunteer");
		volunteer.thinkOfSomething("mama baba");
		MindReader magician = (MindReader) app.getBean("magician");
		System.out.println("MindReader have read :"+magician.getThoughts());
	}			
	

}
