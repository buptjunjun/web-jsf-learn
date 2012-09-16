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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.easyGoingCrawler.extractor.HTMLExtractor;
import org.easyGoingCrawler.framwork.Fetcher;

/**
 * HttpFecher is an implenentation of  Fetcher , it will use HTTP protocol to fetch a url
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class HttpFetcher implements Fetcher{


	/**
	 * fetch a html file 
	 * @param url the url of one html file
	 * @return the content of a html file. null for error
	 */
	@Override
	public String fetch(String url) 
	{	
		if (url == null)
		{
			return null;
		}
		
	
	   try
	   {
		   // 初始化，此处构造函数就与3.1中不同
	       HttpClient httpclient = new DefaultHttpClient();
	
	      // HttpHost targetHost = new HttpHost("blog.sina.com.cn");
	       //HttpGet httpget = new HttpGet("http://www.apache.org/"); 
	       HttpGet httpget = new HttpGet(url);
	
	       // 查看默认request头部信息
	       System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));
	       // 以下这条如果不加会发现无论你设置Accept-Charset为gbk还是utf-8，他都会默认返回gb2312（本例针对google.cn来说）
	       httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
	       // 用逗号分隔显示可以同时接受多种编码
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	       httpget.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	       // 验证头部信息设置生效
	       System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset").getValue());
	
	       // Execute HTTP request
	       System.out.println("executing request " + httpget.getURI());
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
	
	       // Get hold of the response entity
	       HttpEntity entity = response.getEntity();
	      
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
	       if (entity != null) 
	       {
	         // 将源码流保存在一个byte数组当中，因为可能需要两次用到该流，
	          byte[] bytes = EntityUtils.toByteArray(entity);
	         String charSet = "";
	         
	         // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
	          charSet = EntityUtils.getContentCharSet(entity);
	
	         System.out.println("In header: " + charSet);
	         // 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
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
	         }
	         
	         if (charSet == null || charSet.equals(""))
	        	 charSet = "utf-8";
	         System.out.println("Last get: " + charSet);
	         // 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
	         String content =  new String(bytes, charSet);
	         return content;
	       }
	   }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	  
       }
       return null;
	}
	
	
	
	
	public static void main(String [] args) throws IOException
	{
		String content = new HttpFetcher ().fetch("http://www.xiangping.com/");
		
		//System.out.println(content);
		
	}
}
