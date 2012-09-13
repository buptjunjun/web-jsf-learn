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
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

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
		
		HttpClient httpclient = new DefaultHttpClient();
		
		// use get method
		HttpGet httpget = new HttpGet(url);

		// inputStream to read html content 
		InputStream instream = null;
		
		BufferedReader br = null;

		// the context of one request 
		HttpContext localContext = new BasicHttpContext();
		
		try
		{
			HttpResponse response = httpclient.execute(httpget,localContext);
			
			System.out.println(response.getProtocolVersion());
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getStatusLine().getReasonPhrase());
			System.out.println(response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
	
			if (entity != null)
			{
				instream = entity.getContent();
				long length = entity.getContentLength();
				System.out.println ("length " + length);
				
				// get the host name , because the request may be directed
				HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				String actualHostName = target.getHostName();
				
				br = new BufferedReader(new InputStreamReader(instream));
				char[]  buf = new char[1024];
				StringBuffer sb = new StringBuffer();
				
				// read the html content 
				while(br.read(buf) > 0 && length > 0)
				{
					sb.append(buf);
					length -= 1024;
				}
					
				return sb.toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(br != null)
					br.close();		
				
				if(instream !=null)
					instream.close();
				
				httpget.abort();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		
		return null;
	}
	
	
	
	
	public static void main(String [] args) throws FileNotFoundException
	{
		String content = new HttpFetcher ().fetch("http://www.baidu.com/");
		File f = new File("test.html");
		
		System.out.println(content);
		
		List<String> urls = new HTMLExtractor().extract(content);
		
		System.out.println("urls = " +urls);
		
		FileOutputStream fo = new FileOutputStream(f);
		PrintStream ps = new PrintStream(fo);
		ps.print(content);
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://www.myexception.cn");
		try {
			HttpResponse response = httpclient.execute(httpget, localContext);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		HttpUriRequest req = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
		System.out.println("Target host: " + target);
		System.out.println("Final request URI: " + req.getURI());
		System.out.println("Final request method: " + req.getMethod());
	}
}
