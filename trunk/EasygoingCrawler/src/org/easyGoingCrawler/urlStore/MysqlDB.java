package org.easyGoingCrawler.urlStore;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * 将url信息的数据 存入数据库 urldb，当前只支持mysql

 * @author Andy  weibobee@gmail.com   2011-9-1
 */

class MysqlDB 
{
	private Connection connect = null;
	private Statement stmt = null;
	
	public MysqlDB(String dbName) 
	{
		this.reginster();
		this.conDB(dbName);
		try 
		{
			stmt = this.connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 /**
	  * 加载mysql驱动
	  */
	 private void reginster()
	 {
		 try {
			 Class.forName("com.mysql.jdbc.Driver"); //加载MYSQL JDBC驱动程序 
			// System.out.println("Success loading Mysql Driver!");
			 }
			 catch (Exception e) {
			 System.out.print("Error loading Mysql Driver!");
			 e.printStackTrace();
			 }
	 }
	 
	 /**
	  *  连接数据库
	  * @return 连接对象
	  */
	 private Connection conDB(String daName)
	 {
		 Connection connect = null;
		 try {
			  connect = DriverManager.getConnection(
			 "jdbc:mysql://localhost/"+daName+"?useUnicode=true&characterEncoding=utf-8", "root", "");
			//连接URL为 jdbc:mysql//服务器地址/数据库名
			//后面的2个参数分别是登陆用户名和密码
			 System.out.println("Success connect Mysql server!");
		 } 
		 catch (Exception e) 
		 {
			 System.out.print("get data error!");
			 e.printStackTrace();
		 }
		 this.connect = connect;
		 return connect;
	 }
	 
	 
	 
	 /**
	  * insert a obj to a table
	  * @param bib
	  * @param table
	  */
	 private void insert(Object bib, String table)
	 {
		 String insertSql =  getInsertSql(bib,table);
		 try {
			stmt.clearBatch();
			stmt.execute(insertSql);
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(insertSql);
			}
			finally
			{
/*				try
				{
				if(stmt!=null)
					stmt.close();
				stmt = null;
				}
				catch(Exception e)
				{e.printStackTrace();}*/
			}
	 }
	 
	 
	 /**
	  * 
	  * @param查询table里的信息 条件是situation
	  * @param table 要插入的表
	  * @return 结果ResultSet对象
	  */
	 private ResultSet Query(String table,String situation)
	 {   
		 //取得对象每一个成员变量的名字 
		 //查询以条件为situation的结果
		 String sql = " select * from "+ table +"  "+situation +";";
		// System.out.println(sql);

		 ResultSet r = null;	
		try {
			stmt.clearBatch();
			r = stmt.executeQuery(sql);
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		/*	try {
					if(stmt!=null)
					{
						stmt.close();
						stmt = null;
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		}

		 return r;
	 }
	 
	 

	 
	 
	 /**
	  * insert a url;
	  * @param bib
	  * @param table
	  */
	 public boolean insertURL(URLInfo bib,String table)
	 {
		 String situation =  " where url= '"+ bib.getUrl()+"'";
		 ResultSet r = Query(table, situation);
		 try 
		 {
			 if(r.next() == false)
			    insert(bib,table);
			 return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(r!=null)
				{ 
				  r.close();
				  r = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	 
		
		return false;
	 }
	 
	 /**
	  * insert a url;
	  * @param bib
	  * @param table
	  */
	 public boolean deleteURL(URLInfo obj,String table)
	 {
		 String situation =  " where url= '"+ obj.getUrl()+"'";
		 ResultSet r = Query(table, situation);
		 try 
		 {
			 if(r.next() == true)
			 {
				 String sql =  "delete  from " + table + " where url=  '" + obj.getUrl() +"' ;  ";
				 stmt.execute(sql);
				 return true;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(r!=null)
				{ 
				  r.close();
				  r = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	 
		
		return false;
	 }
	 
	 /**
	  * update a url
	  * @param bib
	  * @param table
	  */
	 public boolean updateURLInfo(URLInfo obj,String table)
	 {
		
		 String situation =  " where url= '"+ obj.getUrl()+"'";
		 ResultSet r = Query(table, situation);
		 try 
		 {
			 if(r.next() == false)
			    insert(obj,table);
			 else
			 {
				 String sql =  "update  " + table +  " set url = '"+obj.getUrl()+"' , status= '"+ obj.getStatus()+"' , collectTime ='"+obj.getCollectTime()+ "', lastCrawlTime='"+ obj.getLastCrawlTime()+"'   where url = '"+obj.getUrl()+"'";		 		 
				 stmt.executeUpdate(sql); 
			 }
			 return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(r!=null)
				{ 
				  r.close();
				  r = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	 
		
		return false;
	 }
	 
	
	 /**
	  * return a cadinate url
	  * @param situation
	  * @param table
	  * @return
	  */
	 public List<URLInfo> getURL(String situation,String table)
	 {
		 List<URLInfo> returls = new ArrayList<URLInfo>();
		 ResultSet r = Query(table, situation);
		 try 
		 {
			 while(r.next() == true)
			 {
				 String url = r.getString(1);
				 int status = r.getInt(2);
				 String encode = r.getString(3);
				 String collectTime = r.getString(4);
				 String lastCrawlTime = r.getString(5);
				 
				 URLInfo urlInfo = new URLInfo(url,status,encode,collectTime,lastCrawlTime);
				 returls.add(urlInfo);
			 }
			 
			 return returls;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{
			try {
				if(r!=null)
				{ 
				  r.close();
				  r = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	 
		
	 }
	
	 /**
	  * 
	  * @param bib 要插入的对象
	  * @param table 要插入的表
	  * @return 返回一条插入语句
	  */
	 private String getInsertSql(Object bib, String table)
	 {   
		 String sql = "insert into "+ table+ " values ( ";

  		 Field[] field = bib.getClass().getFields();
		 for(int i = 0 ; i < field.length; i++)
		 {
			 try {
				// System.out.println(i + " " +field[i].get(bib));
			   if(i<field.length-1)
				   sql+= "\""+field[i].get(bib)+"\",";
			   else
					 sql+= "  \""+ field[i].get(bib) + "\");";
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		 }
		 
		 return sql;
	 }
	 
	
	 
	 
	 /**
	  * 取得现在总共有多少信息
	  * @return
	  */
	 public int getTotal(String table)
	 {

		 String sql = "select count(*) from "+ table;
		 int ret = -1;
		 ResultSet r = null;	
		 try 
		 {
			stmt.clearBatch();
			r = stmt.executeQuery(sql);
			if(r.next())
				ret = r.getInt(1);
			return ret;
		  }
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
	 }
	 
}