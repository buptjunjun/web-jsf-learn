package org.junjun.spring.charpter2.first_competetion;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException {

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter2/competition.xml");
		Performer duke = (Performer) app.getBean("duke");
		duke.perform();
		
		//������ע�� value
		System.out.println("\n ������ע�� value");
		Performer dukee = (Performer) app.getBean("dukee");
		dukee.perform();
		
		//������ע�� ref
		System.out.println("\n ������ע�� ref");
		Performer poeticDuke = (Performer) app.getBean("poeticDuke");
		poeticDuke.perform();
		
		// init-method destroy-method ����ע��
		System.out.println("\n init-method destroy-method ����ע��");
		Performer kenny = (Performer) app.getBean("kenny");
		kenny.perform();
		
		//inner bean
		System.out.println("\ninner bean");
		Performer lucy = (Performer) app.getBean("lucy");
		lucy.perform();
		
		//ʹ��p���ֿ��g����property
		System.out.println("\nʹ��p���ֿ��g����property");
		Performer lucyp = (Performer) app.getBean("lucyp");
		lucyp.perform();

		//ע��һ��list
		System.out.println("\nע��һ��list");
		Performer hank = (Performer) app.getBean("hank");
		hank.perform();
	}			

}
