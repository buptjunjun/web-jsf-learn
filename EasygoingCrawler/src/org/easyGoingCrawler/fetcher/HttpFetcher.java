package org.easyGoingCrawler.fetcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.easyGoingCrawler.extractor.HTMLExtractor;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;

/**
 * HttpFecher is an implenentation of  Fetcher , it will use HTTP protocol to fetch a url
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class HttpFetcher extends Fetcher
{
	private Logger logger = Logger.getLogger(HttpFetcher.class);
	// map from host name to ip address
	private static Hashtable host2ip = new Hashtable(); 
	/**
	 * fetch a html file 
	 * @param url the url of one html file
	 * @return the content of a html file. null for error
	 */
	@Override
	public void fetch(CrawlURI curl) 
	{	
		/*String newurl = reconstructURL(url);
		
		// use newurl (like http://61.135.169.105/abc) to get html
		String html = this.getHTML(newurl);
		
		if(html != null)
			return html;*/
		
		//if   newurl  failed, use original url to get html
		this.getHTML(curl);
		if(curl.isStatus())
		{
			resetHost2ip(curl.getUrl());
		}
	}
	
	
	/**
	 *    reconstruct the url using ip instead of host
	 *	  for example "http://www.baidu.com/abc" will replaced by "http://61.135.169.105/abc"
	 * @param url
	 * @return new url if secceeded ,or return null
	 */
	private String reconstructURL(String url)
	{
		
		if (url == null)
		{
			return null;
		}
		   
	   try
	   {
		   URL u = new URL(url);
		   String host = u.getHost();
		   
		   if (host == null)
			   return null;
		   
		   // check if we already have the ip of this host
		   String hostip =  (String) host2ip.get(host);
		   
		   // if we do not cached the ip of this host
		   if (hostip == null)
		   {
			   hostip = fetchDNS(host); 
			   if (hostip != null)
			   {
				   host2ip.put(host, hostip);
			   }
		   }
		   
		   // if hostip is valide ,reconstruct the url using ip instead of host
		   if( hostip != null )
		   {
			   url = url.replace(host, hostip);
		   }
		   
		   return url;
	   }
	   catch( Exception e)
	   {
		   e.printStackTrace();
	   }
		   
		return null;
	}
	
	
	/**
	 *    reconstruct the url using ip instead of host
	 *	  for example "http://www.baidu.com/abc" will replaced by "http://61.135.169.105/abc"
	 * @param url
	 * @return new url if secceeded ,or return null
	 */
	private void resetHost2ip(String url)
	{
		
		if (url == null)
			return;
		   
	   try
	   {
		   URL u = new URL(url);
		   		  
		   String host = u.getHost();
		   
		   if (host == null)
			   return;
		   this.host2ip.put(host, host);
	   }
	   catch( Exception e)
	   {
		   e.printStackTrace();
	   }
		   
		return;
	}
	
	/**
	 * get html form a url
	 * @param url
	 * @return the html if succeed , or return null
	 */
	private void getHTML(CrawlURI curl)
	{
		String url = curl.getUrl();
		if (url == null)
		{
			return;
		}
		
		HttpEntity entity = null;
		HttpClient httpclient = null;
		HttpGet httpget  = null;
	   try
	   {
		  
		   
		  // System.out.println("url = " + url);
		   
		   // 初始化，此处构造函数就与3.1中不同
	       httpclient = new DefaultHttpClient();
	    // 代理的设置
	       HttpHost proxy = new HttpHost("218.201.21.176", 80);
	       httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

	       httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//连接时间20s
	       httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);//数据传输时间60s


	      // HttpHost targetHost = new HttpHost("blog.sina.com.cn");
	       //HttpGet httpget = new HttpGet("http://www.apache.org/"); 
	       httpget = new HttpGet(url);
	       // 查看默认request头部信息
	     //  System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));
	       // 以下这条如果不加会发现无论你设置Accept-Charset为gbk还是utf-8，他都会默认返回gb2312（本例针对google.cn来说）
	       httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
	       // 用逗号分隔显示可以同时接受多种编码
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	       httpget.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	       // 验证头部信息设置生效
	      // System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset").getValue());
	
	       // Execute HTTP request
	       System.out.println(Thread.currentThread().getName() + "executing request " + httpget.getURI());
	       HttpResponse response = httpclient.execute( httpget);
	       //HttpResponse response = httpclient.execute(httpget);
	
	/*	       System.out.println("----------------------------------------");
		       System.out.println("Location: " + response.getLastHeader("Location"));
		       System.out.println(response.getStatusLine().getStatusCode());
		       System.out.println(response.getLastHeader("Content-Type"));
		       System.out.println(response.getLastHeader("Content-Length"));
		      
		       System.out.println("----------------------------------------");*/
	
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
	    	   return;
	       
	       // Get hold of the response entity
	       entity = response.getEntity();
	      
	       // 查看所有返回头部信息
	    /*   Header headers[] = response.getAllHeaders();
	       int ii = 0;
	       while (ii < headers.length) 
	       {
	         System.out.println(headers[ii].getName() + ": " + headers[ii].getValue());
	         ++ii;
	       }*/
	      
	       // If the response does not enclose an entity, there is no need
	       // to bother about connection release
	        byte[] bytes = null;
	        String charSet = "";
	       if (entity != null) 
	       {
	         // 将源码流保存在一个byte数组当中，因为可能需要两次用到该流，
	          bytes = EntityUtils.toByteArray(entity);
	         // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
	          charSet = EntityUtils.getContentCharSet(entity);
	         //System.out.println("In header: " + charSet);
	         // 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
	            System.out.println(charSet +"0");
	         if (charSet == "") {
	        	String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
	        	Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	        	Matcher m=p.matcher(new String(bytes));   // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
	            boolean result=m.find();
	            if (m.groupCount() == 1) {
	                   charSet = m.group(1);
	            } else {
	                   charSet = "";
	            }
	            System.out.println(charSet +"1");
	         }
	         
	         if (charSet == null || charSet.equals(""))
	        	 charSet = "utf-8";
	         System.out.println("Last get: " + charSet);
	         // 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
	         
	         
	         curl.setContent(bytes);
	         curl.setHttpstatus(statusCode);
	         curl.setEncode(charSet);
	         curl.setLastCrawlDate(new Date());
	         curl.setStatus(true);
	         System.out.println(Thread.currentThread().getName()+" http fetcher:  "+ curl.toString());
	         
	         return;
	       }
	       else //error
	       {
	    	   curl.setStatus(false);
	    	   return;
	       }
	   }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   logger.error("error when fetching " + url + "  "+ e.getMessage());
    	   curl.setStatus(false);
       }
	   finally
	   {
		   try
		   {
			   // release the connection
			   httpclient.getConnectionManager().closeExpiredConnections();
			   httpget.abort();
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	   }
	}
	
	public static void main(String [] args) throws IOException
	{
		HttpFetcher f = new HttpFetcher ();		
		CrawlURI curl = new CrawlURI("http://www.baidu.com");
		f.fetch(curl);
		
		System.out.println(new String (curl.getContent()));
		//System.out.println(curl.getIncludeURLs());
		//f.fetch("http://www.myexception.cn/");
		//test(ret);
	}

	
	public static void test(String html)
	{
		String charSet = null;
		String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
    	Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
    	Matcher m=p.matcher(html);   // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
        boolean result=m.find();
        if (m.groupCount() == 1) {
               charSet = m.group(1);
        } else {
               charSet = "";
        }
        System.out.println(charSet +"1");
	}
	
	
	/**
	 * fetch the ip address of a website.
	 * For example we query "ditu.baidu.com" form DNS server. will return "http://123.125.114.86/" 
	 * 
	 * @param url the url of one html file
	 * @return the IP address of a Website 
	 */
	public String fetchDNS(String website) 
	{
		if (website == null ) return null;
		
		try
		{
		  InetAddress address = InetAddress.getByName(website);
		  return address.getHostAddress();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
}
