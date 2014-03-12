package org.junjun.spring.charpter4.second_apoAnnotation;
import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * ��ʾaop��annotation��ʽ
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
		 * ��ʾ���Դ������
		 */
		Performer dukee  = (Performer) app.getBean("dukee");
		dukee.perform();
				
		//��̬���뷽��
		((Contestant)dukee).receiveReward();
		
		/**
		 * annotation ���ݲ���
		 */
		Thinker volunteer = (Thinker) app.getBean("volunteer");
		volunteer.thinkOfSomething("dada mama");
		MindReader mindReader = (MindReader) app.getBean("magician");
		System.out.println(mindReader.getThoughts());
	}			
	

}
