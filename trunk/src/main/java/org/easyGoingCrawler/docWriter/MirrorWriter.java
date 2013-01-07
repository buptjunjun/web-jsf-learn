package org.easyGoingCrawler.docWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.Localizer;
import org.easyGoingCrawler.util.URLAnalyzer;

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
	Logger loger =  Logger.getLogger(MirrorWriter.class);
	
	// where to put this file
	private String baseDirectory="/"; 
	private URLAnalyzer urAnalyzer = null;
	
	
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
	public void write(CrawlURI curl) 
	{
		if(curl.getHttpstatus() != 200 || curl.getStatus() != CrawlURI.STATUS_OK)
			return;
		
		if(urAnalyzer.analyze(curl.getHost(), curl.getUrl()) == urAnalyzer.SAVE)
		{
			String path = getPath(curl);
			if (path == null)
				return;
			String content = curl.getContent();
			saveHtmlNIO(path,content,curl.getEncode());
			loger.info(Thread.currentThread().getName()+"-"+"++MirrorWriter:\n"+curl+"\n ++path = " + path);
		}

	}

	
	public String getPath (CrawlURI curl) 
	{
		if(curl.getStatus() != CrawlURI.STATUS_OK)
			return null;
		String u = curl.getUrl();
		if(StringUtils.isNullOrEmpty(u))
			return null;
		String directory = "";
		String fileName = null;
		String host = null;
		FileWriter fo = null;
		
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
			
			fileName = baseDirectory+curl.getHost()+"/"+directory+fileName;	
			directory = baseDirectory+curl.getHost()+"/"+directory;
			
			File f = new File(directory);
			
			// if the dirctory does not exist
			if (!f.isDirectory())
			{ 
				f.delete();
				
			}
			if (!f.exists())
			{
				f.mkdirs();			
			}
			
			return fileName;
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			loger.error(Thread.currentThread().getName()+"-"+"++MirrorWriter:\n"+curl+"\n ++error = " +e.getMessage());
		}
		return null;		
	}
	
	public void saveHtmlNIO(String filepath,String str, String encode)
	{
		File file = new File(filepath);
		saveHtmlNIO(file,str,encode);
	}
	
	public void saveHtmlNIO(File file,String str, String encode)
	{
		try
		{   int BSIZE = 2048;
			byte[] bytes = str.getBytes(encode);
			ByteBuffer bf = ByteBuffer.allocate(BSIZE);
			FileOutputStream fo = new FileOutputStream(file);
			FileChannel channel = fo.getChannel();
			int length = bytes.length;
			for(int i = 0; i < length; i+=BSIZE)
			{
				bf.clear();
				int remain = length - i;
				if(remain <= BSIZE )
					bf.put(bytes, i, remain);
				else
					bf.put(bytes, i, BSIZE);
				bf.flip();
				channel.write(bf);

			}
			channel.close();
			;
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			loger.error("++MirrorWriter:\n"+file.getPath()+"\n ++error = " +e.getMessage());
		}		
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
	public String getBaseDirectory()
	{
		return baseDirectory;
	}
	public void setBaseDirectory(String baseDirectory)
	{
		this.baseDirectory = baseDirectory;
	}
	public URLAnalyzer getUrAnalyzer()
	{
		return urAnalyzer;
	}
	public void setUrAnalyzer(URLAnalyzer urAnalyzer)
	{
		this.urAnalyzer = urAnalyzer;
	}
}
