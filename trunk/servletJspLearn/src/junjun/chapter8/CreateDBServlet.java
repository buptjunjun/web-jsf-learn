package junjun.chapter8;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 创建数据库
 * 创建表
 * 插入数据
 * @author andyWebsense
 *
 */
public class CreateDBServlet extends HttpServlet
{
	private String url ; 	//到数据库的连接
	private String user;    // 用户名
	private String password;//密码
	
	@Override
	public void init() throws ServletException
	{
		super.init();
		
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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DriverManager.getConnection(url,user,password);
			// 不自动提交
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			// 如果已经存在该数据库 删掉
			stmt.execute("drop database if exists bookstore");
			//创建数据库			
			stmt.execute("create database bookstore");
			stmt.execute("use bookstore");

			//创建数据表
			stmt.executeUpdate(
					" create table bookinfo(" +
					"id INT not null primary key auto_increment , " +
					"author varchar(50)," +
					"title varchar(50) not null," +
					" publis_date Date not null, " +
					"price float(4,2) not null ," +
					"amount smallint," +
					"remark varchar(200) )" +
					" AUTO_INCREMENT = 0 engine=InnoDB");			
			//批量插入一批数据
			stmt.addBatch("insert into bookinfo values(1,'junjun','java','2012-1-1',12.11,10,null)");
			stmt.addBatch("insert into bookinfo values(2,'xiaolan','python','2012-11-1',2.11,10,null)");
			stmt.addBatch("insert into bookinfo values(3,'junjun','ruby','2012-12-1',1.11,10,null)");
			stmt.executeBatch();
			
			/*
			 *使用prepareStatment 优化
			 */
			PreparedStatement pstmt = conn.prepareStatement("insert into bookinfo values(? , ? , ? ,?,12.11,10,null)");
			pstmt.setInt(1, 4);
			pstmt.setString(2, "xiaohai");
			pstmt.setString(3,"google");
			pstmt.setDate(4, new java.sql.Date(new Date().getTime()));		
			pstmt.execute();
			
			pstmt.setInt(1, 5);
			pstmt.setString(2, "haihai");
			pstmt.setString(3,"ws");
			pstmt.setDate(4, new java.sql.Date(new Date().getTime()));		
			pstmt.execute();
			
			//提交给数据库
			conn.commit();
			PrintWriter p = resp.getWriter();
			p.println("success!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(conn!=null)
			{
				try
				{
					// 如果出现错误回滚
					conn.rollback();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				stmt = null;
			}
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
