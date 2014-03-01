package org.junjun.spring.charpter1.second_aop;

import org.junjun.spring.charpter1.first_di.Knight;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 带
 * @author junjun
 *
 */
public class KnightMain {

	public static void main(String[] args) 
	{
		/**
		 * 在spring应用中有一个 应用上下文(application context)负责 转载和创建bean 并将bean
		 * 装配在一起。ApplicationContext 有几种实现，主要的区别就是他们是加载方式
		 * ClassPathXmlApplicationContext 是从classpath 中加载xml类型的 配置文件
		 */
		ApplicationContext app = new ClassPathXmlApplicationContext("charpter1/knight_di.xml","charpter1/knight_aop.xml");
		
		// applicationContext 根据bean的id找到bean
		Knight knight = (Knight) app.getBean("knight");
		
		knight.embarkOnQuest();
	}

}
