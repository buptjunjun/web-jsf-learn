package org.junjun.spring.charpter5.second_jdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junjun.spring.charpter5.common.Spitter;
import org.junjun.spring.charpter5.common.SpitterDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ≤‚ ‘ datasource µƒ¡¨Ω”
 * @author junjun
 *
 */
public class Main
{
	public static void main(String [] args)
	{
		ApplicationContext app = new ClassPathXmlApplicationContext("charpter5/springdb.xml");
		SpitterDAO jdbcTemplateDao = (SpitterDAO) app.getBean("jdbcTemplateSpitterDao");
		
		/**
		 * ≤‚ ‘ jdbcTepmlate
		 */
		System.out.println("≤‚ ‘ jdbcTepmlate");
		Spitter spitter1 = jdbcTemplateDao.getSpitterById(1l);
		System.out.println(spitter1==null?null:spitter1);
		
		/**
		 * ≤‚ ‘ NamedParameterJdbcTepmlate
		 */
		System.out.println("≤‚ ‘ NamedParameterJdbcTepmlate");
		spitter1.setUsername("xl");
		spitter1.setPassword("12321");
		spitter1.setFullname("xl wan");
		jdbcTemplateDao.addSpitter(spitter1);
		System.out.println(spitter1==null?null:spitter1);
		
	}
}
