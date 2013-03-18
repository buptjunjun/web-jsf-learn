package org.cb.indexMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.util.Localizer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class SiteMapCreater
{
	Date date = new Date();
	static private String host = "http://coderlong.com/page/";
	public String dir = "";
	String freq = "weekly";
	String prio = "0.8";
	// how many xmls
	public static int indexNum = 2;
	//how many url per page
	public static int sizeOfpage= 2;
	//content length of bolg 
	public static int lengthLimit = 1;
	
	public static int beginId = 0;
	public String fileName = "site_map_content_";
	public Element createUrl(String id)
	{
		Element url = new Element("url");
		Element loc = new Element("loc");
		Element lastmod = new Element("lastmod");
		Element changefreq = new Element("changefreq");
		Element priority = new Element("priority");
		loc.setText(host+id);
		lastmod.setText((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDay());
		changefreq.setText(freq);
		priority.setText(prio);
		
		url.addContent(loc).addContent(lastmod).addContent(changefreq).addContent(priority);
		return url;
		
	}
	
	public Element createUrlset(List<Element> le)
	{
		Element root = new Element("urlset");
		for(Element e :le)
		{
			root.addContent(e);
		}
		return root;	
	}
	
	public Element createUrlsetByID(List<String> le)
	{
		Element root = new Element("urlset");
		//root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
		for(String e :le)
		{
			root.addContent(createUrl(e));
		}
		return root;	
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		Element e = new SiteMapCreater().createUrl("aaaaaaaa");
//		XMLOutputter outputter = new XMLOutputter();
//		String xml = outputter.outputString(e);
//		System.out.println(xml);
		
		try
		{
			new SiteMapCreater().createSitmap();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void createSitmap() throws IOException
	{
		String mongohost = Localizer.getMessage("mongohost");
		String mongodb = Localizer.getMessage("mongodb");
		String host = Localizer.getMessage("host");
		String unindexedflag = Localizer.getMessage("unindexedflag");
		String inserunindexedflag = Localizer.getMessage("inserunindexedflag");

		int unindexedflag_int = Integer.parseInt(unindexedflag); 
		int inserunindexedflag_int = Integer.parseInt(inserunindexedflag); 
		DAOMongo mongo =  new DAOMongo(mongohost,27017,mongodb);
		
		
	
		long begin = System.currentTimeMillis();
		
		
		int i = 0;
		int start = beginId;
		while(i<indexNum)
		{
			List<Blog> lblog = mongo.searchBlog(host, inserunindexedflag_int, 2);		
			List<String> ids = new ArrayList<String>();
			while(lblog!=null && lblog.size()!=0 )
			{   
				
				for(Blog b : lblog) 
				{				
					b.setMagicNum(unindexedflag_int);		
					//mongo.updateBlog(b);
					String id = b.getId();
					int length = (b.getContent() == null ? 0:b.getContent().length());
					if(length > lengthLimit)
						ids.add(id);
					System.out.println("add:"+b.getUrl());				
					
				}
				
				if(ids.size() > sizeOfpage)
				{
					i++;
					break;
				}
				
				lblog = mongo.searchBlog(host, inserunindexedflag_int, 2);
			}
			
			Element e = createUrlsetByID(ids);
			XMLOutputter outputter = new XMLOutputter();
			FileOutputStream file = new  FileOutputStream(new File(fileName+(start++)+".xml"));
		    outputter.output(e, file);
			//System.out.println(xml);
			
		}
		long end = System.currentTimeMillis();	
			
	}
}
