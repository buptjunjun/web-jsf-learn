package org.easyGoingCrawler.bean;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class BDoubanMovieJson
{
	private String id = null; //id 豆瓣上的id号
	private String json = null; // 从api上拿下来的json数据
	
	private Date crawledDate = new Date();  
	private int magicNum = -1;
	
   
	
	public BMovie toBMovie()
	{
		BMovie bm = null;
		Gson gson = new Gson();
		bm = gson.fromJson(this.json, BMovie.class);	
		return bm;
	}
	
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getJson()
	{
		return json;
	}
	public void setJson(String json)
	{
		this.json = json;
	}
	public Date getCrawledDate()
	{
		return crawledDate;
	}
	public void setCrawledDate(Date crawledDate)
	{
		this.crawledDate = crawledDate;
	}
	public int getMagicNum()
	{
		return magicNum;
	}
	public void setMagicNum(int magicNum)
	{
		this.magicNum = magicNum;
	}
	
	public static void main(String [] args)
	{
		BDoubanMovieJson bdm = new BDoubanMovieJson();
	
	}
}
