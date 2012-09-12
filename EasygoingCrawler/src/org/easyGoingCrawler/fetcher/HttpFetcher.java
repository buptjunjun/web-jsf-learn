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

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
		HttpGet httpget = new HttpGet(url);
		InputStream instream = null;
		BufferedReader br = null;
		try
		{
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				
				System.out.println();
				instream = entity.getContent();
				br = new BufferedReader(new InputStreamReader(instream));
				char buf []={'\0'};
				StringBuffer sb = new StringBuffer();
				while(br.read(buf) > 0)
				{
					sb.append(buf);
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
		String content = new HttpFetcher ().fetch("http://www.google.cn/");
		File f = new File("test.html");
		
		FileOutputStream fo = new FileOutputStream(f);
		PrintStream ps = new PrintStream(fo);
		ps.print(content);
		
	}
}
