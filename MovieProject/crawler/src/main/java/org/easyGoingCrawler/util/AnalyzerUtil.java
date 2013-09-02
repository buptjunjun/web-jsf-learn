package org.easyGoingCrawler.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.easyGoingCrawler.framwork.CrawlURI;

public class AnalyzerUtil
{
	static public void persistObj(Object object,String name)
	{

		try{
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(name));
			o.writeObject(object);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public static Object readObj(String name)
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));
			Object obj = in.readObject();
			return obj;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
}
