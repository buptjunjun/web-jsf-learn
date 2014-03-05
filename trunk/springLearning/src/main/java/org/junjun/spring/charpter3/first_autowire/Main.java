package org.junjun.spring.charpter3.first_autowire;


import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException {

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter3/autowire.xml");
		
		/**
		 * autowire="byName" Instrumentalist ��һ��������instrument ������application�в���name(id)Ϊinstrument
		 * ��bean Ȼ���Զ�ע�� ������saxphone�������� ȱ���Ǳ���������������һ���� bean����
		 */
		System.out.println("\n");
		Performer kenny = (Performer) app.getBean("kenny");
		kenny.perform();
		
		
				
		/**
		 * autowire="byType" Instrumentalist ��һ��������instrument ������application�в���typeΪInstrument
		��bean Ȼ���Զ�ע�� �����ж��bean��������ʱ��spring���׳��쳣��������3���������� ���ԣ�spring��Ҫѡ��������һ����
		ͨ������bean��primary�������ﵽĿ��primary=true�Ļ�ѡ�У�Ϊfalse����ѡ��autowire-candidate=false��ʾ���ڿ������bean��ע��primary Ĭ��Ϊtrue
		������Ҫ������ʾ������Ϊfalse��
		 */
		System.out.println("\n");
		Performer kenny1 = (Performer) app.getBean("kenny1");
		kenny1.perform();
		
		/**
		 * autowire="constructor" ͨ�����캯�������Զ�ע�룬���ж�����캯���������ʱ��spring���׳��쳣  ��bean��ѡ��� byTypeһ��
		 */
		System.out.println("\n ͨ�����캯�������Զ�ע�룬���ж�����캯���������ʱ��spring���׳��쳣  ��bean��ѡ��� byTypeһ��");
		Performer poeticDuke = (Performer) app.getBean("poeticDuke");
		poeticDuke.perform();
		
	
		
		
	}			

}
