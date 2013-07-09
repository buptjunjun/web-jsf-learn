package org.easyGoingCrawler.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.jsoup.helper.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.annotations.SerializedName;

public class BMovie
{
	

	@SerializedName("id")  
	private String id = null;
	
	private String url = null;               //url

	@SerializedName("title")  
	private String name = null;              //电影中文名称
	
	@SerializedName("original_title")
	private String en_name=null;

	@SerializedName("aka")
	private List<String> anotherName = null;   //又名
	
	@SerializedName("subtype")
	private String kind = "movie";             //电影or连续剧
		
	@SerializedName("images")
	private Map<String,String> images = null;   //图片
	
	@SerializedName("durations")
	private List<Integer> durations = null;   //片长
	
	@SerializedName("language")
	private List<String> language = null;   //语言
	
	@SerializedName("summary")
	private String description = null;	 	 //简介
	
	@SerializedName("countries")
	private List<String> location = null;	 //电影制片国家

	@SerializedName("directors")
	private List<BPerson> director = null;      	 //导演
	
	@SerializedName("casts")
	private List<BPerson> actors = null;  	 //演员
	
	@SerializedName("genres")
	private List<String> type = null; 	 	 //类型
	
	@SerializedName("year")
	private int date = 1900;            	 //上映时间
	
	@SerializedName("rating")
	private BRating rating=null;          	 //评分人数
	
	@SerializedName("ratings_count")
	private int ratings_count=-1;          	 //评分人数
	
	@SerializedName("wish_count")
	private int wish_count = 0;              //想看的人数 
	
	private Date crawledDate = new Date();  
	private int magicNum = -1;

	
	public String getEn_name()
	{
		return en_name;
	}
	public void setEn_name(String en_name)
	{
		this.en_name = en_name;
	}
	public List<Integer> getDurations()
	{
		return durations;
	}
	public void setDurations(List<Integer> durations)
	{
		this.durations = durations;
	}
	public BRating getRating()
	{
		return rating;
	}
	public void setRating(BRating rating)
	{
		this.rating = rating;
	}
	public int getWish_count()
	{
		return wish_count;
	}
	public void setWish_count(int wish_count)
	{
		this.wish_count = wish_count;
	}

	public String getKind()
	{
		return kind;
	}
	public void setKind(String kind)
	{
		this.kind = kind;
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
	public Map<String, String> getImages()
	{
		return images;
	}
	public void setImages(Map<String, String> images)
	{
		this.images = images;
	}
	public List<String> getLanguage()
	{
		return language;
	}
	public void setLanguage(List<String> language)
	{
		this.language = language;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public List<String> getLocation()
	{
		return location;
	}
	public void setLocation(List<String> location)
	{
		this.location = location;
	}
	public List<BPerson> getDirector()
	{
		return director;
	}
	public void setDirector(List<BPerson> director)
	{
		this.director = director;
	}
	public List<BPerson> getActors()
	{
		return actors;
	}
	public void setActors(List<BPerson> actors)
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
	public int getDate()
	{
		return date;
	}
	public void setDate(int date)
	{
		this.date = date;
	}
	
	public int getRatings_count()
	{
		return ratings_count;
	}
	public void setRatings_count(int ratings_count)
	{
		this.ratings_count = ratings_count;
	}
	public String getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "id = "+this.id +" url =" + this.url +" name = " +this.name+" enname = " + this.en_name;	
			    
	}
	
	public static void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("https://api.douban.com/v2/movie/subject/6875412");
		curl.setStatus(CrawlURI.STATUS_OK);
		fetcher.fetch(curl);
		BDoubanMovieJson bdm = new BDoubanMovieJson();
		System.out.println(curl.getContent());
		bdm.setJson(curl.getContent());
		BMovie bm = bdm.toBMovie();
		System.out.println();
		System.out.println(StringUtil.isBlank(null));
		System.out.println(StringUtil.isBlank("   "));

	}

}
