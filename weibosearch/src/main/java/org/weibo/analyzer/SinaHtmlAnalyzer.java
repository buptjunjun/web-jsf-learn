package org.weibo.analyzer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResult;
import org.weibo.common.WeiboDetails;

public class SinaHtmlAnalyzer implements Analyzer{

	private static Logger logger = Logger.getLogger(SinaHtmlAnalyzer.class); 
	
	private Pattern pattern = Pattern.compile("[0-9+]");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	/**
	 * analyze the content of html and get the weibo ids concerning the keyword we'v specified.
	 * @return list of weibo id
	 */
	public SearchResult analyze(AnalyzeBean ab) 
	{
		SearchResult ret = new SearchResult(ab.getKeyword());
	
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
				try
				{
					String id = dlnode.attr("mid");
					
					String contentStr = "";
					
					//content element
					Element content =  dlnode.getElementsByClass("content").first();
					
					// content of weibo
					Element content1 = content.getElementsByTag("p").first();
					
					//content of forwarded weibo
					Element content2 = content.getElementsByAttributeValue("node-type", "feed_list_forwardContent").first();
					
					contentStr+=content1.text();
					if(content2!=null)
						contentStr+="//"+content2.text();
					
					Element statistics = content.getElementsByTag("p").last();
					Element dateElement = statistics.getElementsByClass("date").first();
					
					// date 
					String dateStr = dateElement.attr("title");
					Date date = sdf.parse(dateStr);
					
					Elements statistics1 = statistics.child(0).getElementsByTag("a");
					Element retwitElement = statistics1.get(1);
					Element commentElement = statistics1.get(3);
					
					//times of retwit 
					String retwitStr = retwitElement.text();
					Matcher m1 = pattern.matcher(retwitStr);
					int rewitNumber = 0;
					if(m1.find())
					{
						String retwitNumberStr = m1.group();
						rewitNumber = Integer.parseInt(retwitNumberStr);
					}
					
					//times of retwit 
					String commentStr = commentElement.text();
					Matcher m2 = pattern.matcher(commentStr);
					int commentNumber = 0;
					if(m2.find())
					{
						String commentNumberStr = m2.group();
						commentNumber = Integer.parseInt(commentNumberStr);
					}
					
					
					/*user info*/
					Element user = content.getElementsByTag("a").first();
					
					// get userName
					String userName = user.text();
					
					// get userid
					String href = user.attr("href");
					String[] tmp = href.split("/");;
					String userId = tmp[tmp.length-1];
					
					
					WeiboDetails wds = new WeiboDetails();
					wds.setId(id);
					wds.setCommentCount(commentNumber);
					wds.setContent(contentStr);
					wds.setRettwitCount(rewitNumber);
					wds.setCreateAt(date);
					wds.setUserID(userId);
					wds.setUserName(userName);
					
					ret.getDetails().add(wds);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.info("Analyze resulst:"+ab.getKeyword()+" ; "+ab.getHttpstatus()+" ;  "+ret.toString());
				}
			}
			
			logger.info("Analyze resulst:"+ab.getKeyword()+" ; "+ab.getHttpstatus()+" ;  "+ret.toString());
			
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
