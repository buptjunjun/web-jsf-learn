package org.easyGoingCrawler.urlStore;

import org.easyGoingCrawler.framwork.URLStore;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * URLStoreMysql will store the URL in MYSQL.
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class URLStoreMysql implements URLStore
{
	protected MysqlDB mysqldb = null;
	
	private static  final Hashtable urls = new Hashtable();
	private static  int   MaxCachedURLS = 200;
	private  String tableName = null;
	
	public URLStoreMysql() 
	{
		mysqldb = new MysqlDB("urldb");		
	}
	
	public URLStoreMysql(String dbName,String tableName) 
	{
		mysqldb = new MysqlDB(dbName);
		this.tableName = tableName;
	}

	@Override
	public boolean put(String url)
	{
		// TODO Auto-generated method stub
		URLInfo urlinfo = new URLInfo(url,0,new Date().toLocaleString(),new Date().toLocaleString());
		boolean ret = this.mysqldb.insertURL(urlinfo, tableName);
		
		// if urls size is bigger than a threshold. put some urls to db
		if( urls!=null && urls.size() > MaxCachedURLS)
		{
			while (urls.size() > MaxCachedURLS - MaxCachedURLS/5)
			{
				Iterator i = urls.entrySet().iterator();
				if(i.hasNext())
				{
					URLInfo u = (URLInfo) i.next();
					u.setLastCrawlTime(new Date().toLocaleString());
					this.mysqldb.insertURL(u,tableName);
					urls.remove(u.getUrl());
				}
			}
		}
		return ret;
	}

	@Override
	public String get()
	{
		String condition = " where status=0 order by lastCrawlTime   asc limit 1";
		// TODO Auto-generated method stub		
		URLInfo urlinfo = this.mysqldb.getURL(condition, tableName);

		if (urlinfo != null)
		{			
			urls.put(urlinfo.getUrl(), urlinfo);
			this.mysqldb.deleteURL(urlinfo, tableName);
			return urlinfo.getUrl();
		}
		
		return null;
	}
	
	@Override
	public void updateSucceed(String url)
	{
		// TODO Auto-generated method stub
		if(urls.containsKey(url))
		{
			URLInfo urlinfo = (URLInfo) urls.get(url);			
			urlinfo.setLastCrawlTime(new Date().toLocaleString());
			urlinfo.setStatus(1);
			urls.remove(url);
			this.mysqldb.insertURL(urlinfo, tableName);
		}
		
		
	}

	@Override
	public void updateFailed(String url)
	{
		// TODO Auto-generated method stub
		if(urls.containsKey(url))
		{
			URLInfo urlinfo = (URLInfo) urls.get(url);			
			urlinfo.setLastCrawlTime(new Date().toLocaleString());
			urlinfo.setStatus(-1);
			urls.remove(url);
			this.mysqldb.insertURL(urlinfo, tableName);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		URLStoreMysql uslstore = new URLStoreMysql();
		String url = uslstore.get();
		System.out.println(url);
		uslstore.put("http://www.myexception.cn");
		System.out.println(url);
		
	}
}


/**
 * the URL class 
 * 
 * @author Andy  weibobee@gmail.com 2012-9-15
 *
 */
class URLInfo
{	
	public String url = null;   
	
	/**
	 * status = -1: url is crawled but failed
	 * status = 0: url is not crawled
	 * status = 1: url is crawled 
	 */
	public int status = 0; 
    
    /**
     * the time one url was collected
     */
	public String collectTime = new Date().toLocaleString();
    
    /**
     * the time one url last crawled
     */
	public String lastCrawlTime = new Date().toLocaleString();
    
    public URLInfo() 
    {
		// TODO Auto-generated constructor stub
	}
    public URLInfo(String url,int status, String collectTime,String lastCrawlTime) 
    {
		// TODO Auto-generated constructor stub
    	this.url = url;
    	this.status = status;
    	this.collectTime = collectTime;
    	this.lastCrawlTime = lastCrawlTime;
	}
    
    
    public String getUrl()
    {
	return url;
     }
	public void setUrl(String url) 
	{
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getCollectTime() 
	{
		return collectTime;
	}
	public void setCollecttime(String collectTime) 
	{
		this.collectTime = collectTime;
	}
	public String getLastCrawlTime() 
	{
		return lastCrawlTime;
	}
	public void setLastCrawlTime(String lastCrawlTime) 
	{
		this.lastCrawlTime = lastCrawlTime;
	}
}

/**
 * ��url��Ϣ������ �������ݿ� urldb����ǰֻ֧��mysql

 * @author Andy  weibobee@gmail.com   2011-9-1
 */

class MysqlDB 
{
	private Connection connect = null;
	 Statement stmt = null;
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
	  * ����mysql����
	  */
	 private void reginster()
	 {
		 try {
			 Class.forName("com.mysql.jdbc.Driver"); //����MYSQL JDBC�������� 
			// System.out.println("Success loading Mysql Driver!");
			 }
			 catch (Exception e) {
			 System.out.print("Error loading Mysql Driver!");
			 e.printStackTrace();
			 }
	 }
	 
	 /**
	  *  �������ݿ�
	  * @return ���Ӷ���
	  */
	 private Connection conDB(String daName)
	 {
		 Connection connect = null;
		 try {
			  connect = DriverManager.getConnection(
			 "jdbc:mysql://localhost/"+daName+"?useUnicode=true&characterEncoding=utf-8", "root", "");
			//����URLΪ jdbc:mysql//��������ַ/���ݿ���
			//�����2�������ֱ��ǵ�½�û���������
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
	  * @param��ѯtable�����Ϣ ������situation
	  * @param table Ҫ����ı�
	  * @return ���ResultSet����
	  */
	 private ResultSet Query(String table,String situation)
	 {   
		 //ȡ�ö���ÿһ����Ա���������� 
		 //��ѯ������Ϊsituation�Ľ��
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
	 public URLInfo getURL(String situation,String table)
	 {
		 ResultSet r = Query(table, situation);
		 try 
		 {
			 if(r.next() == true)
			 {
				 String url = r.getString(1);
				 int status = r.getInt(2);
				 String collectTime = r.getString(3);
				 String lastCrawlTime = r.getString(4);
				 
				 URLInfo urlInfo = new URLInfo(url,status,collectTime,lastCrawlTime);
				 return urlInfo;
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
		
		return null;
	 }
	
	 /**
	  * 
	  * @param bib Ҫ����Ķ���
	  * @param table Ҫ����ı�
	  * @return ����һ���������
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
	  * ȡ�������ܹ��ж�����Ϣ
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

