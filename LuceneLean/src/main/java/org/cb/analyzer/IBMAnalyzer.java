package org.cb.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cb.data.Html;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IBMAnalyzer implements Analyzer
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public String analyse(Html h)
	{
		String ret = null;
		try
		{			
			String html = h.getHtml();	
			String url = h.getUrl();
			System.out.println("url ="+url);
			url = url.replace(".html", "");
			url = url.replace("index", "");
			url = url.trim();
			if(!url.endsWith("/"))
			url+="/";
			
			Document doc = Jsoup.parse(html);

			// content
			Element eContent = doc.getElementById("ibm-content-body");
			// amount of pictures
			Elements eimgs = eContent.select("img");
			if(eimgs != null)
				for(Element img:eimgs)
				{
					String src = img.attr("src");
					System.out.println("src ="+src);
					if(src!=null && !src.startsWith("http"))
						src=url+src;
					img.attr("src", src);
					System.out.println(src);
				}
									
			ret = eContent.html();
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return ret;
	}

}
