package org.junjun.analyse.analyzer.bean;

import java.util.Date;
import java.util.List;

public class BComment
{

	private String id=null;

	private String comment4 = null;  //对什么的评价 人的id或者电影的id
	private	String url = null;  //影人条目URL 
	private String content = null;  //影人头像
	private Date date = null;   //相关的电影
	private String who = null;  //谁给的评价
	private String whoPicUrl = null; //评价人的头像
	private int rate=0;         //评价分数
	private int vote = 0;     //多少人支持
	
	
	public int getRate()
	{
		return rate;
	}
	public void setRate(int rate)
	{
		this.rate = rate;
	}
	public int getVote()
	{
		return vote;
	}
	public void setVote(int vote)
	{
		this.vote = vote;
	}
	public String getWhoPicUrl()
	{
		return whoPicUrl;
	}
	public void setWhoPicUrl(String whoPicUrl)
	{
		this.whoPicUrl = whoPicUrl;
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getComment4()
	{
		return comment4;
	}
	public void setComment4(String comment4)
	{
		this.comment4 = comment4;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public String getWho()
	{
		return who;
	}
	public void setWho(String who)
	{
		this.who = who;
	}
	
	@Override
	public String toString()
	{
		return this.getId()+" |"+ this.getComment4()+"|"+this.getContent();
	}
}
