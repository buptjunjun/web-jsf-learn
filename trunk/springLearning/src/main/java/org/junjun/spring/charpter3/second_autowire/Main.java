package org.junjun.spring.charpter3.second_autowire;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ʹ��ע��ע��
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
		 * ʹ��type=assignable ���п��Ը�ֵ��Instrument���඼Ҫʵ���� ������saxphone��guitar����
		 * <context:include-filter type="assignable" expression="org.junjun.spring.charpter3.second_autowire.Instrument"/>
		 */
		Instrument guitar = (Instrument) app.getBean("guitar");
		Instrument saxPhone = (Instrument) app.getBean("saxPhone");
		guitar.play();
		saxPhone.play();
		
		
		/**
		 * <!-- ����@Component�Ķ���ʵ����ΪBean ������Juggle -->
			<context:include-filter type="annotation" expression="org.springframework.stereotype.Component"/>
		 */
		Performer juggler = (Performer) app.getBean("juggler");
		juggler.perform();
		
		/**
		 * <!-- ʹ��������ʽ org.junjun.spring.charpter3.second_autowire.Sonnet* ����ʵ����-->
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
