package org.weibo.analyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResult;
import org.weibo.common.WeiboDetails;
import org.weibo.common.WeiboUtil;

public class TXHtmlAnalyzer implements Analyzer{

	private static Logger logger = Logger.getLogger(TXHtmlAnalyzer.class); 
	
	private Pattern pattern = Pattern.compile("[0-9+]");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月DD日 hh:mm");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月DD日 hh:mm");
	private SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月DD日");
	
	/**
	 * analyze the content of html and get the weibo ids concerning the keyword we'v specified.
	 * @return list of weibo id
	 */
	public SearchResult analyze(AnalyzeBean ab) 
	{
		SearchResult ret = new SearchResult(ab.getKeyword());
		ret.setType(ab.getType());
		if(ab.getHttpstatus() != 200 || StringUtils.isEmpty(ab.getContent()))
			return ret;
		
		try
		{
			Document doc = Jsoup.parse(ab.getContent());
			
			Element talkNew = doc.getElementById("talkList");
			Elements li = talkNew.getElementsByTag("li");
			
			Iterator<Element> i = li.iterator();
			while(i.hasNext())
			{		
				Element dlnode = i.next();
				try
				{
					String id = dlnode.attr("id");
					
					String contentStr = "";
					
					//content element
					Element content =  dlnode.getElementsByClass("msgBox").first();
					
					// content of weibo
					Elements weiboTXTElement = content.getElementsByClass("msgCnt");
					for(int j =0;j < weiboTXTElement.size();j++)
					{
						Element tmp = weiboTXTElement.get(j);
						if(j==0)
							contentStr+=tmp.text();
						else
							contentStr+=("//"+tmp.text());
					}
					
					Element statistics = content.getElementsByClass("pubInfo").last();
					Element dateElement = statistics.getElementsByClass("time").first();
					
					// date 
					String dateStr = dateElement.text();
					Date date = phraseDate(dateStr);
					
					Element retwitElement = statistics.getElementsByClass("relay").first();				
					Element commentElement = statistics.getElementsByClass("comt").first();
					
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
					Element user = dlnode.getElementsByClass("userPic").first();				
					Element userElement = user.getElementsByTag("a").first();
					
					// get userid
					String userId = userElement.attr("account");
										
					//user name
					String userName = userElement.attr("title");
					userName = userName.replace("(@"+userId+")","");
					
					
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
	
	
	private Date phraseDate(String str)
	{
		Date date =  new Date();
		if(str == null)
			return date;
		
		if(str.contains("分钟前"))
		{
			Matcher m = this.pattern.matcher(str);
			int minutes = 0;
			if(m.find())
			{
				String numStr = m.group();
				minutes = Integer.parseInt(numStr);
			}
			
			long time = date.getTime()-minutes*60*1000;
			date.setTime(time);
			
			return date;
		}
		
		if(str.contains("今天"))
		{
			str = str.replace("今天",sdf3.format(date));
		}
		else if(str.contains("昨天"))
		{
			str = str.replace("昨天",sdf3.format(WeiboUtil.getDateBefore(date, 1)));
		}
		else if(!str.contains("年") && str.contains("月"))
		{
			str = str=sdf1.format(date)+str;
		}
		
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			d = new Date();
		}
		return d;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TXHtmlAnalyzer analyzer = new TXHtmlAnalyzer();
		
		analyzer.phraseDate("5分钟前");
		analyzer.phraseDate("今天 08:59");
		analyzer.phraseDate("昨天 08:59");
		analyzer.phraseDate("1月10日 21:24");
	}

}
