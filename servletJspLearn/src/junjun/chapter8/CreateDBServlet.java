package junjun.chapter8;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Driver;
/**
 * �������ݿ�
 * ������
 * ��������
 * @author andyWebsense
 *
 */
public class CreateDBServlet extends HttpServlet
{
	private String url ; 	//�����ݿ������
	private String user;    // �û���
	private String password;//����
	
	@Override
	public void init() throws ServletException
	{
		super.init();
		
		// ��web.xml�ж�һ��servlet��ȥȡ�ó�ʼ������
		String driverClass=this.getInitParameter("driverClass");
		url = this.getInitParameter("url");
		user = this.getInitParameter("user");
		password = this.getInitParameter("password");
		
		//�������ݿ�����
		try
		{
			Class.forName(driverClass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// ����ʧ���׳�UnavailableException����� servlet�����ò����á��Ժ������������ʷ������᷵��404��
			throw new UnavailableException("�������ݿ�����ʧ��");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DriverManager.getConnection(url,user,password);
			stmt = conn.createStatement();
			
			// ����Ѿ����ڸ����ݿ� ɾ��
			stmt.execute("drop database if exists bookstore");
			//�������ݿ�			
			stmt.execute("create database bookstore");
			stmt.execute("use bookstore");

			//�������ݱ�
			stmt.executeUpdate(
					" create table bookinto(" +
					"id INT not null primary key, " +
					"author varchar(50)," +
					"title varchar(50) not null," +
					" publis_date Date not null, " +
					"price float(4,2) not null ," +
					"amount smallint," +
					"remark varchar(200) )" +
					"engine=InnoDB");
			
			//��������һ������
			stmt.addBatch("insert into bookinto values(1,'junjun','java','2012-1-1',12.11,10,null)");
			stmt.addBatch("insert into bookinto values(2,'xiaolan','python','2012-11-1',2.11,10,null)");
			stmt.addBatch("insert into bookinto values(3,'junjun','ruby','2012-12-1',1.11,10,null)");
			stmt.executeBatch();
			PrintWriter p = resp.getWriter();
			p.println("success!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

			if(stmt!=null)
			{
				try
				{
					stmt.close();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				stmt = null;
			}
			
			if(conn!=null)
			{
				try
				{
					conn.close();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				conn = null;
			}
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
}