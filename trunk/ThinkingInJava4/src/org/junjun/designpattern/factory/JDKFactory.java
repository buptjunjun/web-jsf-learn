package org.junjun.designpattern.factory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * jdk中的工厂模式
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
		 * 在JDK中经常见到使用工厂模式的地方。例如：抽象类java.util.Calendar的getInstance方法将根据不同的情况返回不同的Calendar子类的对象，具体逻辑如下：
 
		... ... ...
		if ("th".equals(aLocale.getLanguage())
		             && ("TH".equals(aLocale.getCountry()))) {
		             return new sun.util.BuddhistCalendar(zone, aLocale);
		// 如果地区信息是泰国将返回BuddhistCalendar类型的对象
		         } else if ("JP".equals(aLocale.getVariant())
		                      && "JP".equals(aLocale.getCountry())
		                      && "ja".equals(aLocale.getLanguage())) {
		             return new JapaneseImperialCalendar(zone, aLocale);
		                   // 如果地区信息是日本将返回JapaneseImplerialCalendar类型的对象
		         }           
		 
		         // 除上述地区之外将返回GergorianCalendar类型对象，此对象代表标准公历
		        return new GregorianCalendar(zone, aLocale);    
		}
		 */
		
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		
	}

}
