package byr.analyzer.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import byr.crawler.framework.Html;

public class ByrHtmlAnalyzer 
{
	private static Logger logger = Logger.getLogger(ByrHtmlAnalyzer.class);
	public HtmlStructuredData analyse(Html html)
	{
		HtmlStructuredData hsd = new HtmlStructuredData();
		
		try
		{
		Document doc = Jsoup.parse(html.getHtml());
		String title = doc.title();
		String content = "";
		int img = 0;
		int video = 0;
		Elements articles = doc.getElementsByClass("article");
		if(articles == null || articles.size() == 0)
			return null;
		for(int i = 0; i < articles.size();i++)
		{
			try
			{
			Element article = articles.get(i);
			Element contentElem = article.getElementsByClass("a-content").first();
			content+=contentElem.text();
			
			Elements imgs = contentElem.getElementsByTag("img");
			Elements videos = contentElem.getElementsByTag("object");
			
			if(imgs!=null && imgs.size()>0)
				img++;
			
			if(videos!=null && videos.size()>0)
				video++;	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("analyze error:  url="+ html.getUrl()+" "+e.getMessage());
			}
		}

		hsd.setId(html.getId());
		hsd.setUrl(html.getUrl());
		hsd.setTitle(title);
		hsd.setImgs(img);
		hsd.setVideos(video);
		hsd.setContent(content);
		hsd.setCreatedDate(html.getCreatedDate());
		hsd.setCollectedDate(new Date());
		
		logger.info("analyzed url="+ html.getUrl()+",title="+title);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("failed to analyzed url="+ html.getUrl()+" "+e.getMessage());
			hsd = null;
		}
		return hsd;
	}

	public static void main(String [] args) throws IOException
	{
		ByrHtmlAnalyzer analyzer = new ByrHtmlAnalyzer();
		Html html = new Html();
		String text = FileUtils.readFileToString(new File("byr.html"));
		html.setHtml(text);
		HtmlStructuredData hsd = analyzer.analyse(html);
		System.out.println();
	}
}
