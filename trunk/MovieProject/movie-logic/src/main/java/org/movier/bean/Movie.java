package org.movier.bean;

import java.util.Date;
import java.util.List;

public class Movie
{
	String id = null;
	String url = null;           // url
	String name = null;          //
	String enName = null;        
	List<String> anotherName = null;   
	String description = null;	
	String location = null;      
	String director = null;    
	List<String> actors = null;  
	List<String> type = null; 	
	Date date = null;            
	int timespan = 0;	         

	float score = 0f ;           
	int voteCount = 0;
	String kind = "m";             //movie or serial 

	Date crawledDate = new Date();
	int magicNum = -1;

	public String getEnName()
	{
		return enName;
	}
	public void setEnName(String enName)
	{
		this.enName = enName;
	}
	public String getKind()
	{
		return kind;
	}
	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public int getVoteCount()
	{
		return voteCount;
	}
	public void setVoteCount(int voteCount)
	{
		this.voteCount = voteCount;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<String> getAnotherName()
	{
		return anotherName;
	}
	public void setAnotherName(List<String> anotherName)
	{
		this.anotherName = anotherName;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getDirector()
	{
		return director;
	}
	public void setDirector(String director)
	{
		this.director = director;
	}
	public List<String> getActors()
	{
		return actors;
	}
	public void setActors(List<String> actors)
	{
		this.actors = actors;
	}
	public List<String> getType()
	{
		return type;
	}
	public void setType(List<String> type)
	{
		this.type = type;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public int getTimespan()
	{
		return timespan;
	}
	public void setTimespan(int time)
	{
		this.timespan = time;
	}
	public float getScore()
	{
		return score;
	}
	public void setScore(float score)
	{
		this.score = score;
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
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
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "id = "+this.id +" url =" + this.url +" name = " +this.name+" kind = "+this.kind;	
			    
	}
	

}
