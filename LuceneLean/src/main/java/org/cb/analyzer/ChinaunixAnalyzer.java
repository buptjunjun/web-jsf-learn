package org.cb.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cb.data.Html;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChinaunixAnalyzer implements Analyzer
{

	public String prefix = "http://blog.chinaunix.net";
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
			Document doc = Jsoup.parse(html);

			// content
			Element econtent = doc.getElementsByClass("Blog_wz1").first();
			
			if(econtent == null)
			  econtent = doc.getElementById("detail");
			
			// amount of pictures
			Elements eimgs = econtent.select("img");
			if(eimgs != null)
				for(Element img:eimgs)
				{
					String src = img.attr("src");
					if(src!=null && !src.startsWith("http"))
						src=prefix+src;
					img.attr("src", src);
					System.out.println(src);
				}
						
			ret = econtent.html();
			
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
