package org.cb.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cb.data.Html;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OschinaAnalyzer implements Analyzer
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
			Document doc = Jsoup.parse(html);

			// content
			Element econtent = doc.getElementsByClass("BlogContent").first();
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
