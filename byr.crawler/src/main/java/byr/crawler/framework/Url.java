package byr.crawler.framework;

import java.util.Date;


public class Url
{
	public static final int  CRAWLED = 1;
	public static final int  UNCRAWLED = 0;
	
	public static final int  URL_SAVE = 0;	
	public static final int  URL_REPEAT = 1;
	public static final int  URL_HOLD = 2;
	public static final int  URL_DELETE = 3;
	

	private String id = null;
	private String host = null;
	private String url = null;
	private Date lastCrawled = new Date();
	private int flag = 0;   // indicate the url is crawled or not
	private int type = 0;   // URL_SAVE URL_REPEAT URL_HOLD
	 
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public Date getLastCrawled()
	{
		return lastCrawled;
	}
	public void setLastCrawled(Date lastCrawled)
	{
		this.lastCrawled = lastCrawled;
	}
	
	public int getFlag()
	{
		return flag;
	}
	public void setFlag(int flag)
	{
		this.flag = flag;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getHost()
	{
		return host;
	}
	public void setHost(String host)
	{
		this.host = host;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public CrawlURI toCrawlURI()
	{
		CrawlURI uri = new CrawlURI();
		uri.setLastCrawlDate(this.lastCrawled);
		uri.setCollectDate(lastCrawled);
		uri.setUrl(this);
		uri.setHost(host);
		uri.setStatus(CrawlURI.STATUS_OK);
		
		return uri;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.id +"   " +"  " + this.host +" "  + this.url +"  flag = "+this.flag +"  type="+this.type + "  lastCrawled = " +this.lastCrawled;
	}
}
