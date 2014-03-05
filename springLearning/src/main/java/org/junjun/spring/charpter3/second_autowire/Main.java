package org.junjun.spring.charpter3.second_autowire;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用注解注入
 * @author andyWebsense
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws PerformanceException 
	 */
	public static void main(String[] args) throws PerformanceException {

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter3/autowireAnotation.xml");
		/**
		 * 使用type=assignable 所有可以赋值给Instrument的类都要实例化 这里有saxphone和guitar两个
		 * <context:include-filter type="assignable" expression="org.junjun.spring.charpter3.second_autowire.Instrument"/>
		 */
		Instrument guitar = (Instrument) app.getBean("guitar");
		Instrument saxPhone = (Instrument) app.getBean("saxPhone");
		guitar.play();
		saxPhone.play();
		
		
		/**
		 * <!-- 带有@Component的都被实例化为Bean 这里是Juggle -->
			<context:include-filter type="annotation" expression="org.springframework.stereotype.Component"/>
		 */
		Performer juggler = (Performer) app.getBean("juggler");
		juggler.perform();
		
		/**
		 * <!-- 使用正则表达式 org.junjun.spring.charpter3.second_autowire.Sonnet* 不被实例化-->
		<context:exclude-filter type="regex" expression="org.junjun.spring.charpter3.second_autowire.Sonnet*"/>
		 */
		
		try
		{
			Poem sonnet29 = (Poem)app.getBean("sonnet29");
			sonnet29.recite();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}			

}
