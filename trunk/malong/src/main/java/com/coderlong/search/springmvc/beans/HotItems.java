package com.coderlong.search.springmvc.beans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class HotItems 
{
	private static List<HotItem> hotItems= null;
	private String file;
	public HotItems(String file)
	{
		this.file = file;
		hotItems = new ArrayList<HotItem>();
		this.loadFromFile(file);
		new Thread(){
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					load();
					TimeUnit.SECONDS.sleep(60);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
	synchronized public void addItem(HotItem item)
	{
		hotItems.add(item);
	}
	
	public synchronized  void remove(HotItem item)
	{
		hotItems.remove(item);
	}
	
	public synchronized  void remove(int  location)
	{
		if(location > hotItems.size() && location < hotItems.size())
			hotItems.remove(location);
	}

	public  List<HotItem> getHotItems()
	{
		
		return hotItems;
	}

	public void load()
	{
		this.loadFromFile(this.file);
	}
	/**
	 * load hot items from a file.
	 * @param file
	 */
	public  void loadFromFile(String file)
	{
		Properties pro = new Properties();
		InputStream inStr = null;
		try {
		    inStr = HotItems.class.getClassLoader().getResourceAsStream(file);		    
		    pro.load(inStr);
		    Enumeration e = pro.propertyNames();
		    
		    while(e.hasMoreElements())
		    {
		    	String key = (String) e.nextElement();
		    	if( key != null )
		    	{
		    		String value = pro.getProperty(key);
		    		addItem(new HotItem(key,value));
		    	}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		finally
		{
			try
			{
				inStr.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
