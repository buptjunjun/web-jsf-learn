package org.easyGoingCrawler.docWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.easyGoingCrawler.framwork.DocWriter;

import com.mysql.jdbc.StringUtils;


/**
 *  MirrorWriter will do the job of storing the documents you have fetched to Hard disk ,
 *  arranged in a directory hierarchy based on the URI paths.
 *  for example, "www.baidu.com/music/gingle_bell.html" will be stored in the file "path/www.baidu.com/music/gingle_bell.html"
 *
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class MirrorWriter implements DocWriter
{

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
		mw.write(null, "http://www.baidu.com/");
	}

	@Override
	public boolean write(Object doc,String u)
	{
		if(StringUtils.isNullOrEmpty(u))
			return false;
		String directory = null;
		String fileName = null;
		String host = null;
		FileOutputStream fo = null;
		
		try {
			URL url = new URL(u);		
			host = url.getHost();
			String file = url.getFile();
			
			if(file.endsWith("/"))
			{
				file =  file.substring(0, file.length()-1);			
			}
			String [] sss = file.split("/");
			fileName = sss[sss.length-1];		
			directory = file.replace(fileName,"");	
			
			if(fileName.equals(""))
				fileName = "index.html";
			
			if(directory.startsWith("/"))
				directory = directory.substring(1);
			directory = host +"/"+ directory;
			fileName = directory+"/"+fileName;
			// create directory and file for this url
			File f = new File(directory);
			
			// if the dirctory does not exist
			if (!f.exists())
				f.mkdirs();
			
			File html = new File(fileName);
			if (!html.exists())
				f.createNewFile();
			
			fo = new FileOutputStream(html);
			
			// write html content to this file
			String content = (String)doc;
			fo.write(content.getBytes());
			return false;
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	/*	System.out.println(host);
		System.out.println(directory);
		System.out.println(fileName);
		System.out.println("--------------");*/
		
	
		
		
	/*	System.out.println(directory);
		System.out.println(fileName );
		System.out.println(directory+fileName);*/
		return false;
		
	}

}
