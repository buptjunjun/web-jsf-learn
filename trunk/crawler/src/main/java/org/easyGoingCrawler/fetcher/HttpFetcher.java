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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
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
import org.easyGoingCrawler.util.FetcherUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.util.WebClientUtils;

/**
 * HttpFecher is an implenentation of  Fetcher , it will use HTTP protocol to fetch a url
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class HttpFetcher extends Fetcher
{
	private Logger logger = Logger.getLogger(HttpFetcher.class);
	// map from host name to ip address
	/**
	 * fetch a html file 
	 * @param url the url of one html file
	 * @return the content of a html file. null for error
	 */
	@Override
	public void fetch(CrawlURI curl) 
	{
		this.getHTML(curl);	
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
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			return;
		}
		
		HttpEntity entity = null;
		HttpClient httpclient = null;
		HttpGet httpget  = null;
	   try
	   {
		  
		   
		  // System.out.println("url = " + url);
		   
		   // ��ʼ�����˴����캯������3.1�в�ͬ
	       httpclient = new DefaultHttpClient();
	    // ����������
	      // HttpHost proxy = new HttpHost("218.201.21.176", 80);
	      // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

	       httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//����ʱ��20s
	       httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);//���ݴ���ʱ��60s
	       httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");

	      // HttpHost targetHost = new HttpHost("blog.sina.com.cn");
	       //HttpGet httpget = new HttpGet("http://www.apache.org/"); 
	       httpget = new HttpGet(url);
	       // �鿴Ĭ��requestͷ����Ϣ
	     //  System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));
	       // ��������������ӻᷢ������������Accept-CharsetΪgbk����utf-8��������Ĭ�Ϸ���gb2312���������google.cn��˵��
	       httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
	       // �ö��ŷָ���ʾ����ͬʱ���ܶ��ֱ���
	       httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	       httpget.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	       
	       // ��֤ͷ����Ϣ������Ч
	      // System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset").getValue());
	
	       // Execute HTTP request
	       System.out.println(Thread.currentThread().getName() + "  executing request " + httpget.getURI());
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
	       
	       statusCode = response.getStatusLine().getStatusCode();
	       
	       // return if the status is not 200
	       if (statusCode != 200)
	    	   return;
	       
	       // Get hold of the response entity
	       entity = response.getEntity();
	      
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
	        byte[] bytes = null;
	        String charSet = "";
	       if (entity != null) 
	       {
	         // ��Դ����������һ��byte���鵱�У���Ϊ������Ҫ�����õ�������
	          bytes = EntityUtils.toByteArray(entity);
	         // ���ͷ��Content-Type�а����˱�����Ϣ����ô���ǿ���ֱ���ڴ˴���ȡ
	          charSet = EntityUtils.getContentCharSet(entity);
	         //System.out.println("In header: " + charSet);
	         // ���ͷ����û�У���ô������Ҫ �鿴ҳ��Դ�룬���������Ȼ����˵��ȫ��ȷ����Ϊ��Щ�ֲڵ���ҳ������û����ҳ����дͷ��������Ϣ
	            System.out.println(charSet +"0");
	         if (charSet == "") {
	        	 String xml = new String(bytes);
	        	 charSet = FetcherUtil.getEncode(xml);
	            System.out.println(charSet +"1");
	         }
	         
	         if (charSet == null || charSet.equals(""))
	        	 charSet = "utf-8";
	         System.out.println("Last get: " + charSet);
	         // ���ˣ����ǿ��Խ�ԭbyte���鰴����������ר���ַ������������ҵ��˱���Ļ���
	         
	         
	         curl.setContent(new String(bytes,charSet));
	         curl.setHttpstatus(statusCode);
	         curl.setEncode(charSet);
	         curl.setLastCrawlDate(new Date());
	         curl.setStatus(CrawlURI.STATUS_OK);
	         System.out.println(Thread.currentThread().getName()+"  http fetcher:  "+ curl.toString());
	         
	         return;
	       }
	       else //error
	       {
	    	   curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
	    	   return;
	       }
	   }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   logger.error("error when fetching " + url + "  "+ e.getMessage());
    	   curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
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
		/*HttpFetcher f = new HttpFetcher ();		
		CrawlURI curl = new CrawlURI("http://www.baidu.com");
		f.fetch(curl);
		
		System.out.println(new String (curl.getContent()));
		//System.out.println(curl.getIncludeURLs());
		//f.fetch("http://www.myexception.cn/");
		//test(ret);
*/	
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("https://api.douban.com/v2/movie/subject/10564052");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		Document doc = Jsoup.parse(curl.getContent());
		System.out.println(doc.text());	
	}

	
	public static void test(String html)
	{
		String charSet = null;
		String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
    	Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
    	Matcher m=p.matcher(html);   // Ĭ�ϱ���ת���ַ�������Ϊ���ǵ�ƥ���������ģ����Դ��п��ܵ����������û��Ӱ��
        boolean result=m.find();
        if (m.groupCount() == 1) {
               charSet = m.group(1);
        } else {
               charSet = "";
        }
        //System.out.println(charSet +"1");
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