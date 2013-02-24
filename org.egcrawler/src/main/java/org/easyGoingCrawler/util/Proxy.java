package org.easyGoingCrawler.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.easyGoingCrawler.framwork.Fetcher;

public class Proxy implements Callable<Integer> ,Comparable
{
	public static final int TIMEOUT = -1;
	public static final int ERROR = -2;
	
	private Fetcher f = null;
	private String ip = null;
	private int port = 8080;
	private String testUrl = null;
	private int connectTime = Integer.MIN_VALUE;
	

	public Proxy( Fetcher f , String ip , int port ,String testUrl )
	{
		this.f = f;
		this.ip = ip;
		this.port =port;
		this.testUrl = testUrl;
	}
	
	/**
	 * return how many time to connecto to the testUrl
	 * @return
	 */
	public int checkConnection()
	{
		
		return -1;
	}
	

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getTestUrl()
	{
		return testUrl;
	}

	public void setTestUrl(String testUrl)
	{
		this.testUrl = testUrl;
	}

	@Override
	public String toString()
	{
	  return this.ip +":"+this.port+ ","+this.connectTime;
	}

	public Integer call() throws Exception
	{
		this.connectTime = Integer.MIN_VALUE;
		// TODO Auto-generated method stub
		HttpEntity entity = null;
		HttpClient httpclient = null;
		HttpGet httpget  = null;

		try
		{  
		   // 初始化，此处构造函数就与3.1中不同
	       httpclient = new DefaultHttpClient();
	       // 代理的设置
	       HttpHost proxy = new HttpHost(this.ip, this.port);
	       httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

	       httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20*1000);//连接时间20s
	       httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30*1000);//数据传输时间30s
	       httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");

	       httpget = new HttpGet(this.getTestUrl());
	       httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
	       // 用逗号分隔显示可以同时接受多种编码
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	       httpget.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	       
	       // Execute HTTP request
	       System.out.println(Thread.currentThread().getName() + "  executing request " + httpget.getURI());
	       HttpResponse response;
	       long begin = System.currentTimeMillis();
		   response = httpclient.execute( httpget);
		
	       // 判断页面返回状态判断是否进行转向抓取新链接
	       int statusCode = response.getStatusLine().getStatusCode();
	      
	       if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
	            (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
	            (statusCode == HttpStatus.SC_SEE_OTHER) ||
	            (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT))
	       {
	         // 此处重定向处理   此处还未验证
	         String newUri = response.getLastHeader("Location").getValue();
	         System.out.println("redirect url = " + newUri);
	         httpclient = new DefaultHttpClient();
	         httpget = new HttpGet(newUri);
	         response = httpclient.execute(httpget);
	       }
	       
	       statusCode = response.getStatusLine().getStatusCode();
	       
	       // return if the status is not 200
	       if (statusCode != 200)
	    	   return 0-statusCode;
	       
	       long end = System.currentTimeMillis();			  
	       return (int) (end - begin);
	       
		} catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 return ERROR;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 return TIMEOUT;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 return TIMEOUT;
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		if(!Proxy.class.isInstance(obj))
			return false;
		Proxy p = (Proxy)obj;
		
		if(this.ip.equals(p.getIp()) && this.port == p.port)
			return true;
		
		return false;
	}

	public void setConnectTime(int connectTime)
	{
		this.connectTime = connectTime;
	}

	public int getConnectTime()
	{
		return connectTime;
	}

	public int compareTo(Object arg0)
	{
		Proxy p = (Proxy)arg0;
		if(p.getConnectTime() == this.connectTime)
			return 0;
		else if(p.getConnectTime() > this.connectTime)
			return -1;
		else return 1;
			
	}
}