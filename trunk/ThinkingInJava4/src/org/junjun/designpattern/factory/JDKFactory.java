package org.junjun.designpattern.factory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * jdk�еĹ���ģʽ
 * @author junjun
 *
 */
public class JDKFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		DateFormat dateformat = DateFormat.getDateInstance(3);	
		String datestr = dateformat.format(new Date());
		System.out.println(datestr);
		
		Integer i = Integer.valueOf(1);
		
		/*
		 * ��JDK�о�������ʹ�ù���ģʽ�ĵط������磺������java.util.Calendar��getInstance���������ݲ�ͬ��������ز�ͬ��Calendar����Ķ��󣬾����߼����£�
 
		... ... ...
		if ("th".equals(aLocale.getLanguage())
		             && ("TH".equals(aLocale.getCountry()))) {
		             return new sun.util.BuddhistCalendar(zone, aLocale);
		// ���������Ϣ��̩��������BuddhistCalendar���͵Ķ���
		         } else if ("JP".equals(aLocale.getVariant())
		                      && "JP".equals(aLocale.getCountry())
		                      && "ja".equals(aLocale.getLanguage())) {
		             return new JapaneseImperialCalendar(zone, aLocale);
		                   // ���������Ϣ���ձ�������JapaneseImplerialCalendar���͵Ķ���
		         }           
		 
		         // ����������֮�⽫����GergorianCalendar���Ͷ��󣬴˶�������׼����
		        return new GregorianCalendar(zone, aLocale);    
		}
		 */
		
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		
	}

}
