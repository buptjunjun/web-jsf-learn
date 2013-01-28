package junjun.chapter8;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.Driver;
/**
 * �г����ݿ������
 * @author andyWebsense
 *
 */
public class ListServlet extends HttpServlet
{
	private String url ; 	//�����ݿ������
	private String user;    // �û���
	private String password;//����
	
	@Override
	public void init() throws ServletException
	{
		super.init();
		initUsingDriverManager();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		Connection conn = null;
		Statement stmt = null;
		try
		{
			//conn = DriverManager.getConnection(url,user,password);
			/**
			 * ʹ�� ����Դ��ȡ�����ݿ������
			 */
			Context initialContext = new javax.naming.InitialContext();
			Context envContext  = (Context)initialContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/bookstore");
			conn = ds.getConnection();
			
			//��ȡһ�����Ը������ݿ��statement 
			stmt = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//��ӡ�����鼮����Ϣ
			PrintWriter p = resp.getWriter();
			printBook(stmt,p);
		
			//��ӡ���ݿ��Ԫ����
			p.println("<br/>all the  meta Data of database bookstore<br/>");
			//�������ݿ��������Ϣ
			DatabaseMetaData dbmeta = conn.getMetaData();
			//��ȡ������ݿ������е�table����Ϣ
			ResultSet resTable = dbmeta.getTables(null, null, null, new String[] {"TABLE"});
			while(resTable.next())
			{
				p.println(resTable.getString("TABLE_NAME"));
				p.println("<br>");
			}
			
			// ��ӡbookinfo����Ϣ:����,���ͣ�����
			p.println("<br/>all the meta info of table bookinfo<br/>");
			ResultSet res = stmt.executeQuery("select * from bookinfo");
			ResultSetMetaData resMeta = res.getMetaData();
			int columntCount = resMeta.getColumnCount();
			for(int i = 1;i < columntCount; i++)
			{
				p.println(resMeta.getColumnName(i)+"  "+resMeta.getColumnTypeName(i)+"  "+resMeta.getColumnDisplaySize(i));
				p.println("<br>");
			}
			
			/*
			 * �ڽ�����в���
			 */
			//update �����
			res.first();
			//��������е�һ���е�title�޸�Ϊ1.11,����updateRow�Ż���Ч
			res.updateFloat("price", (float) 1.11);
			res.updateString("title","updated row");
			res.updateRow();
		
			//ɾ�����һ�������
			res.last();
			res.deleteRow();
			
			//����һ��  
			//���α��ƶ������Բ���Ļ�����
			res.moveToInsertRow();
			res.updateString("author", "daniangdaye");
			res.updateString("title","inserted row");
			res.updateDate("publis_date", new java.sql.Date(new Date().getTime()));		
			res.updateFloat("price", (float) 1.11);
			res.insertRow();
		
			//��ӡ�����鼮����Ϣ
			printBook(stmt,p);
			
			
			
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
	
	/**��ӡ���е���
	 * @param stmt
	 * @param p
	 * @throws SQLException
	 */
	private void printBook(Statement stmt,PrintWriter p) throws SQLException
	{
	
		ResultSet res = stmt.executeQuery("select * from bookinfo");
		p.println("<br/>all the information of books<br/>");
		while(res.next())
		{
			String title = res.getString("title");
			String author = res.getString("author");
			Float price = res.getFloat("price");
			Date publishDate = res.getDate("publis_date");
			
			p.println(title+"  " +author+ "  "+price+"  "+publishDate);
			p.println("<br>");
		}
		
	}
	
	/**
	 * ʹ�� DriverManager ������mysql������
	 * @throws ServletException
	 */
	private void initUsingDriverManager() throws ServletException
	{
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
	
	/**
	 * ʹ�� DataResource ������mysql������
	 * @throws ServletException
	 */
	private void initUsingDataResource() throws ServletException
	{
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
}
