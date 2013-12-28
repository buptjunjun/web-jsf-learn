package org.weibo.analyzer;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResultID;

public class SinaHtmlAnalyzer implements Analyzer{

	private static Logger logger = Logger.getLogger(SinaHtmlAnalyzer.class); 
	/**
	 * analyze the content of html and get the weibo ids concerning the keyword we'v specified.
	 * @return list of weibo id
	 */
	public SearchResultID analyze(AnalyzeBean ab) 
	{
		SearchResultID ret = new SearchResultID(ab.getKeyword());
	
		if(ab.getHttpstatus() != 200 || StringUtils.isEmpty(ab.getContent()))
			return ret;
		
		try
		{
			Document doc = Jsoup.parse(ab.getContent());
			
			Elements elements = doc.getElementsByAttributeValue("action-type", "feed_list_item");
			
			Iterator<Element> i = elements.iterator();
			while(i.hasNext())
			{
				Element dlnode = i.next();
				String id = dlnode.attr("mid");
				ret.getIds().add(id);
			}
			
			logger.info(ab.getKeyword()+" ; "+ab.getHttpstatus()+" ; "+ret.toString());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(ab.getKeyword()+" ; "+(ab.getType()==1?"sian":"tx")+ab.getHttpstatus()+" ; "+e.toString());
		}
		
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
