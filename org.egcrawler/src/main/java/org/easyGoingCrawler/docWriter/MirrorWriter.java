package org.easyGoingCrawler.docWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.Localizer;

import com.mysql.jdbc.StringUtils;


/**
 *  MirrorWriter will do the job of storing the documents you have fetched to Hard disk ,
 *  arranged in a directory hierarchy based on the URI paths.
 *  for example, "www.baidu.com/music/gingle_bell.html" will be stored in the file "path/www.baidu.com/music/gingle_bell.html"
 *
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class MirrorWriter extends DocWriter
{
	// where to put this file
	private String baseDirectory="/"; 
	
	public MirrorWriter() 
	{
		// TODO Auto-generated constructor stub
		baseDirectory = Localizer.getMessage("baseDirectory");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		/*try {
			URL url = new URL("http://www.cnblogs.com/");
			
			String str = url.getHost();
			String file = url.getFile();
			String path = url.getPath();
			
			System.out.println(file);
			if(path.endsWith("/"))
			{
				String ss =  path.substring(0, path.length()-1);
				System.out.println(ss);
				String [] sss = ss.split("/");
				String s = sss[sss.length-1];
				System.out.println(s);
				
				String rs = ss.replace(s,"");
				System.out.println(rs);				
			}
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		
		MirrorWriter mw = new MirrorWriter();
		//mw.write(null, "http://blog.csdn.net/fengyarongaa");
		//String url = "C:\crawledHTML\www.iteye.com\blogs\pdfs\8779?channel_home=true";
		/*url = mw.strip(url);
		
		System.out.println(url);*/
	}


	@Override
	public void write(org.easyGoingCrawler.framwork.CrawlURI curl) 
	{
		if(curl.getStatus() != CrawlURI.STATUS_OK)
			return;
		
		String u = curl.getUrl();
		if(StringUtils.isNullOrEmpty(u))
			return ;
		String directory = null;
		String fileName = null;
		String host = null;
		FileOutputStream fo = null;
		
		try {
			
			URL url = new URL(u);	
			
			// get the host name
			host = url.getHost();
			
			// get the file path of the url
			String file = url.getFile();
			file = file.trim();
			
			// if this is a directory already
			if (file.endsWith("/"))
			{
				directory=file;
				fileName = "index.html";
			}
			else // get the file name
			{
				String [] sss = file.split("/");
				fileName = sss[sss.length-1];	
				directory = file.replace(fileName,"");
				if( !fileName.contains("."))
					fileName = fileName +".html";
			}
			fileName = strip(fileName);
			directory = host + directory;
			fileName = directory+fileName;
			
			
			//System.out.println("fileName = " + fileName);
			
			// strip unsupportable char in a path
			// create directory and file for this url
			File f = new File(baseDirectory+ directory);
			
			// if the dirctory does not exist
			if (!f.isDirectory())
			{ 
				f.delete();
				
			}
			if (!f.exists())
			{
				f.mkdirs();			
			}
			File html = new File(baseDirectory+ fileName);

			
			fo = new FileOutputStream(html);
			
			// write html content to this file
			System.out.println(Thread.currentThread().getName()  + " directory = " + directory);
			System.out.println(Thread.currentThread().getName() + " writing file :" + fileName);
			
			fo.write(curl.getContent());
		
			// set the status to true when write the file successfully 
			curl.setStatus(true);
			curl.setPath(fileName);
			System.out.println(Thread.currentThread().getName()+" mirror writer: "+ curl.toString());
			return ;			
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			// set the status to false when failed to write the file  
			curl.setStatus(false);
			System.out.println("directory = " + directory);
			System.out.println("file = " + fileName);
		}
		finally
		{
			if (fo != null)
				try {
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return;	
		
	}
	
	public String strip (String path)
	{
		char [] s = {'*','?' ,':' ,'\'', '<', '>','|'};
		if(path == null || "".equals(path.trim()))
			return null;
		char [] p = path.toCharArray();
		
		for(int i = 0; i < s.length; i++)
		{
			char ss = s[i];
			for( int j = 0; j< p.length; j++)
			{
				if( ss == p[j])
				{
					p[j] = '_';
				}
			}
		}
		
		return new String(p);
		
	}

}
