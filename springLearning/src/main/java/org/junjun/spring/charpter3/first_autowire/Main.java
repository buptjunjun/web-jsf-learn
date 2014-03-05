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
		 * autowire="byName" Instrumentalist 有一个属性是instrument 它会在application中查找name(id)为instrument
		 * 的bean 然后自动注入 ，这里saxphone满足条件 缺点是必须有与属性名字一样的 bean才行
		 */
		System.out.println("\n");
		Performer kenny = (Performer) app.getBean("kenny");
		kenny.perform();
		
		
				
		/**
		 * autowire="byType" Instrumentalist 有一个属性是instrument 它会在application中查找type为Instrument
		的bean 然后自动注入 ，当有多个bean满足条件时候，spring会抛出异常。这里有3个满足条件 所以，spring需要选择其他的一个，
		通过设置bean的primary属性来达到目的primary=true的会选中，为false不会选择，autowire-candidate=false表示不在考虑这个bean。注意primary 默认为true
		所以需要将其显示地设置为false。
		 */
		System.out.println("\n");
		Performer kenny1 = (Performer) app.getBean("kenny1");
		kenny1.perform();
		
		/**
		 * autowire="constructor" 通过构造函数进行自动注入，当有多个构造函数都满足的时候，spring会抛出异常  ，bean的选择跟 byType一样
		 */
		System.out.println("\n 通过构造函数进行自动注入，当有多个构造函数都满足的时候，spring会抛出异常  ，bean的选择跟 byType一样");
		Performer poeticDuke = (Performer) app.getBean("poeticDuke");
		poeticDuke.perform();
		
	
		
		
	}			

}
