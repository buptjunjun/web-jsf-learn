package org.junjun.spring.charpter1.first_di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KnightMain {

	public static void main(String[] args) 
	{
		/**
		 * ��springӦ������һ�� Ӧ��������(application context)���� ת�غʹ���bean ����bean
		 * װ����һ��ApplicationContext �м���ʵ�֣���Ҫ��������������Ǽ��ط�ʽ
		 * ClassPathXmlApplicationContext �Ǵ�classpath �м���xml���͵� �����ļ�
		 */
		ApplicationContext app = new ClassPathXmlApplicationContext("charpter1/knight_di.xml");
		
		// applicationContext ����bean��id�ҵ�bean
		Knight knight = (Knight) app.getBean("knight");
		
		knight.embarkOnQuest();
	}

}
