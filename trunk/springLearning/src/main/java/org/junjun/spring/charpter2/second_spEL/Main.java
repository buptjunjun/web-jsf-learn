package org.junjun.spring.charpter2.second_spEL;


import java.util.List;
import java.util.Properties;

import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Expression language 表达式语言
 * @author andyWebsense
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException  {

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter2/springEL.xml");
		
		/**
		 * 基本类型
		 */
		System.out.println("\n基本类型");
		SpELBasicType basictype = (SpELBasicType) app.getBean("basicType");
		System.out.println(basictype.toString());
		
		/**
		 * 使用 el引用其他bean 引用其他bean的属性
		 */
		System.out.println("\n使用 el引用其他bean 引用其他bean的属性");
		Performer lucy = (Performer) app.getBean("lucy");
		lucy.perform();
		
		/**
		 * 使用 el引用其他bean 调用其他bean的方法
		 */
		System.out.println("\n使用 el引用其他bean 调用其他bean的方法");
		Performer lucyy = (Performer) app.getBean("lucyy");
		lucyy.perform();
		
		/**
		 * 要使用 静态方法 或者class级别的参数需要使用，T()
		 */
		System.out.println("\n要使用 静态方法 或者class级别的参数需要使用，T()");
		SpELBasicType basictype1 = (SpELBasicType) app.getBean("basicType1");
		System.out.println(basictype1.toString());
	
		
		/**
		 * 加减乘除乘方等：+ - * / % ^  , 字符串连接 逻辑运算 正则表达式
		 */
		System.out.println("\n加减乘除乘方等：+ - * / % ^  , 字符串连接 逻辑运算 正则表达式");
		SpELBasicType basictype2 = (SpELBasicType) app.getBean("basicType2");
		System.out.println(basictype2.toString());
		
		
		/**
		 * EL 表达式操作collection
		 */
		System.out.println("\nEL 表达式操作collection");
		Country country = (Country) app.getBean("country");
		System.out.println(country.toString());	
		
		
		/**
		 * 加载 properties 文件 
		 */
		System.out.println("\n加载 properties 文件 ");
		Properties settings = (Properties) app.getBean("settings");
		System.out.println(settings.toString());	
		
		
		/**
		 *  从集合中选择满足条件的元素.?[] 
		 *  从集合中选择满足条件的第一个元素.^[] 
		 *  从集合中选择满足条件的第最后一个元素.^[] 
		 *  将集合中元素的属性映射到一个集合 .![]
		 */
		
		System.out.println("\n从集合中选择满足条件的元素.?[]  从集合中选择满足条件的第一个元素.^[]  从集合中选择满足条件的第最后一个元素.$[] ");
		Country country1 = (Country) app.getBean("country1");
		System.out.println(country1.toString());	
		
		List<City> cityes3 =  (List<City>) app.getBean("cityes3");
		System.out.println(cityes3);
		
		List<String> cityNameState =  (List<String>) app.getBean("cityNameState");
		System.out.println(cityNameState);
		
	}			

}
