package org.weibo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * the bean  contains infomation  that the fetcher need to feth a url.
 * @author junjun
 *
 */
public class FetchBean 
{
	// sina:1,tx:2
	private int type= Constants.SINA;
	private String keyword = null;
	private String url = null;
	private int interval = 30; 			//seconds
	
	
	public FetchBean(String keyword,int type) 
	{
		try {
			this.keyword = new String(keyword.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.type = type;
		this.generateUrlFromKeywordAndType();
	}
	
	public FetchBean(String keyword,int type,int interval) 
	{
		this.keyword = keyword;
		this.type = type;
		this.interval = Math.abs(interval);
		
		this.generateUrlFromKeywordAndType();
	}
	
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	private void generateUrlFromKeywordAndType()
	{
		if(this.type == Constants.SINA)
			try {
				this.url = "http://s.weibo.com/wb/"+URLEncoder.encode(this.keyword,"utf-8")+"&Refer=index";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
