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
		
		//構造器注入 value
		System.out.println("\n 構造器注入 value");
		Performer dukee = (Performer) app.getBean("dukee");
		dukee.perform();
		
		//構造器注入 ref
		System.out.println("\n 構造器注入 ref");
		Performer poeticDuke = (Performer) app.getBean("poeticDuke");
		poeticDuke.perform();
		
		// init-method destroy-method 屬性注入
		System.out.println("\n init-method destroy-method 屬性注入");
		Performer kenny = (Performer) app.getBean("kenny");
		kenny.perform();
		
		//inner bean
		System.out.println("\ninner bean");
		Performer lucy = (Performer) app.getBean("lucy");
		lucy.perform();
		
		//使用p名字空間代替property
		System.out.println("\n使用p名字空間代替property");
		Performer lucyp = (Performer) app.getBean("lucyp");
		lucyp.perform();

		//注入一个list
		System.out.println("\n注入一个list");
		Performer hank = (Performer) app.getBean("hank");
		hank.perform();
	}			

}
