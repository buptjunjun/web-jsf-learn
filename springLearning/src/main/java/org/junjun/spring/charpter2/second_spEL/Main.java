package org.junjun.spring.charpter2.second_spEL;


import java.util.List;
import java.util.Properties;

import org.junjun.spring.charpter2.first_competetion.PerformanceException;
import org.junjun.spring.charpter2.first_competetion.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Expression language ���ʽ����
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
		 * ��������
		 */
		System.out.println("\n��������");
		SpELBasicType basictype = (SpELBasicType) app.getBean("basicType");
		System.out.println(basictype.toString());
		
		/**
		 * ʹ�� el��������bean ��������bean������
		 */
		System.out.println("\nʹ�� el��������bean ��������bean������");
		Performer lucy = (Performer) app.getBean("lucy");
		lucy.perform();
		
		/**
		 * ʹ�� el��������bean ��������bean�ķ���
		 */
		System.out.println("\nʹ�� el��������bean ��������bean�ķ���");
		Performer lucyy = (Performer) app.getBean("lucyy");
		lucyy.perform();
		
		/**
		 * Ҫʹ�� ��̬���� ����class����Ĳ�����Ҫʹ�ã�T()
		 */
		System.out.println("\nҪʹ�� ��̬���� ����class����Ĳ�����Ҫʹ�ã�T()");
		SpELBasicType basictype1 = (SpELBasicType) app.getBean("basicType1");
		System.out.println(basictype1.toString());
	
		
		/**
		 * �Ӽ��˳��˷��ȣ�+ - * / % ^  , �ַ������� �߼����� ������ʽ
		 */
		System.out.println("\n�Ӽ��˳��˷��ȣ�+ - * / % ^  , �ַ������� �߼����� ������ʽ");
		SpELBasicType basictype2 = (SpELBasicType) app.getBean("basicType2");
		System.out.println(basictype2.toString());
		
		
		/**
		 * EL ���ʽ����collection
		 */
		System.out.println("\nEL ���ʽ����collection");
		Country country = (Country) app.getBean("country");
		System.out.println(country.toString());	
		
		
		/**
		 * ���� properties �ļ� 
		 */
		System.out.println("\n���� properties �ļ� ");
		Properties settings = (Properties) app.getBean("settings");
		System.out.println(settings.toString());	
		
		
		/**
		 *  �Ӽ�����ѡ������������Ԫ��.?[] 
		 *  �Ӽ�����ѡ�����������ĵ�һ��Ԫ��.^[] 
		 *  �Ӽ�����ѡ�����������ĵ����һ��Ԫ��.^[] 
		 *  ��������Ԫ�ص�����ӳ�䵽һ������ .![]
		 */
		
		System.out.println("\n�Ӽ�����ѡ������������Ԫ��.?[]  �Ӽ�����ѡ�����������ĵ�һ��Ԫ��.^[]  �Ӽ�����ѡ�����������ĵ����һ��Ԫ��.$[] ");
		Country country1 = (Country) app.getBean("country1");
		System.out.println(country1.toString());	
		
		List<City> cityes3 =  (List<City>) app.getBean("cityes3");
		System.out.println(cityes3);
		
		List<String> cityNameState =  (List<String>) app.getBean("cityNameState");
		System.out.println(cityNameState);
		
	}			

}
