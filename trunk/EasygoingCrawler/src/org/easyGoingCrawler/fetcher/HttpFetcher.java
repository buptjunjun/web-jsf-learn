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
import org.apache.http.params.CoreConnectionPNames;
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
		   // ��ʼ�����˴����캯������3.1�в�ͬ
	       HttpClient httpclient = new DefaultHttpClient();
	       httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//����ʱ��20s
	       httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);//���ݴ���ʱ��60s


	      // HttpHost targetHost = new HttpHost("blog.sina.com.cn");
	       //HttpGet httpget = new HttpGet("http://www.apache.org/"); 
	       HttpGet httpget = new HttpGet(url);
	       // �鿴Ĭ��requestͷ����Ϣ
	       System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));
	       // ��������������ӻᷢ������������Accept-CharsetΪgbk����utf-8��������Ĭ�Ϸ���gb2312���������google.cn��˵��
	       httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
	       // �ö��ŷָ���ʾ����ͬʱ���ܶ��ֱ���
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	       httpget.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	       // ��֤ͷ����Ϣ������Ч
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
	
	      // �ж�ҳ�淵��״̬�ж��Ƿ����ת��ץȡ������
	       int statusCode = response.getStatusLine().getStatusCode();
	       if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
	            (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
	            (statusCode == HttpStatus.SC_SEE_OTHER) ||
	            (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT))
	       {
	         // �˴��ض�����   �˴���δ��֤
	         String newUri = response.getLastHeader("Location").getValue();
	         System.out.println("redirect url = " + newUri);
	         httpclient = new DefaultHttpClient();
	         httpget = new HttpGet(newUri);
	         response = httpclient.execute(httpget);
	       }
	
	       // Get hold of the response entity
	       HttpEntity entity = response.getEntity();
	      
	       // �鿴���з���ͷ����Ϣ
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
	         // ��Դ����������һ��byte���鵱�У���Ϊ������Ҫ�����õ�������
	          byte[] bytes = EntityUtils.toByteArray(entity);
	         String charSet = "";
	         
	         // ���ͷ��Content-Type�а����˱�����Ϣ����ô���ǿ���ֱ���ڴ˴���ȡ
	          charSet = EntityUtils.getContentCharSet(entity);
	
	         System.out.println("In header: " + charSet);
	         // ���ͷ����û�У���ô������Ҫ �鿴ҳ��Դ�룬���������Ȼ����˵��ȫ��ȷ����Ϊ��Щ�ֲڵ���ҳ������û����ҳ����дͷ��������Ϣ
	         if (charSet == "") {
	        	String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
	        	Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	        	Matcher m=p.matcher(new String(bytes));   // Ĭ�ϱ���ת���ַ�������Ϊ���ǵ�ƥ���������ģ����Դ��п��ܵ����������û��Ӱ��
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
	         // ���ˣ����ǿ��Խ�ԭbyte���鰴����������ר���ַ������������ҵ��˱���Ļ���
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
		String content = new HttpFetcher ().fetch("http://www.baidu.com");
		
		//System.out.println(content);
		
	}
}