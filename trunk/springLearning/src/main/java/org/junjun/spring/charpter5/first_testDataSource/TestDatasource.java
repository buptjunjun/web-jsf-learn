package org.junjun.spring.charpter5.first_testDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ≤‚ ‘ datasource µƒ¡¨Ω”
 * @author junjun
 *
 */
public class TestDatasource
{
	public static void main(String [] args)
	{
		ApplicationContext app = new ClassPathXmlApplicationContext("charpter5/springdb.xml");
		DataSource dr = (DataSource) app.getBean("dataSource");
		
		try
		{
			Connection con = dr.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from user limit 10");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
