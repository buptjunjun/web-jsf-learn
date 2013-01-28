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
 * 列出数据库的内容
 * @author andyWebsense
 *
 */
public class ListServlet extends HttpServlet
{
	private String url ; 	//到数据库的连接
	private String user;    // 用户名
	private String password;//密码
	
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
			 * 使用 数据源来取得数据库的连接
			 */
			Context initialContext = new javax.naming.InitialContext();
			Context envContext  = (Context)initialContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/bookstore");
			conn = ds.getConnection();
			
			//获取一个可以更新数据库的statement 
			stmt = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//打印所有书籍的信息
			PrintWriter p = resp.getWriter();
			printBook(stmt,p);
		
			//打印数据库的元数据
			p.println("<br/>all the  meta Data of database bookstore<br/>");
			//返回数据库的所有信息
			DatabaseMetaData dbmeta = conn.getMetaData();
			//获取这个数据库中所有的table的信息
			ResultSet resTable = dbmeta.getTables(null, null, null, new String[] {"TABLE"});
			while(resTable.next())
			{
				p.println(resTable.getString("TABLE_NAME"));
				p.println("<br>");
			}
			
			// 打印bookinfo的信息:名字,类型，长度
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
			 * 在结果集中操作
			 */
			//update 结果集
			res.first();
			//将结果集中第一行行的title修改为1.11,调用updateRow才会生效
			res.updateFloat("price", (float) 1.11);
			res.updateString("title","updated row");
			res.updateRow();
		
			//删除最后一个结果集
			res.last();
			res.deleteRow();
			
			//插入一行  
			//将游标移动到可以插入的缓存行
			res.moveToInsertRow();
			res.updateString("author", "daniangdaye");
			res.updateString("title","inserted row");
			res.updateDate("publis_date", new java.sql.Date(new Date().getTime()));		
			res.updateFloat("price", (float) 1.11);
			res.insertRow();
		
			//打印所有书籍的信息
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
	
	/**打印所有的书
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
	 * 使用 DriverManager 来管理mysql的连接
	 * @throws ServletException
	 */
	private void initUsingDriverManager() throws ServletException
	{
		// 从web.xml中顶一顶servlet中去取得初始化参数
		String driverClass=this.getInitParameter("driverClass");
		url = this.getInitParameter("url");
		user = this.getInitParameter("user");
		password = this.getInitParameter("password");
		
		//加载数据库驱动
		try
		{
			Class.forName(driverClass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// 加载失败抛出UnavailableException后这个 servlet将永久不可用。以后如果有请求访问服务器会返回404。
			throw new UnavailableException("加载数据库驱动失败");
		}
	}
	
	/**
	 * 使用 DataResource 来管理mysql的连接
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
