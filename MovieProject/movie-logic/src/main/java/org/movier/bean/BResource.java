package org.movier.bean;

import java.util.Date;
import java.util.List;

/**
 * resource of one movie
 * @author junjun
 *
 */
public class BResource
{	
    private String id = null;
    private String movieId = null;
    private String resourceURL;
    private String resourceType;  
    private String resourceDescription = null; //dvd qmv rmvb ...
    private String movieDescription = null; 
	

	private String host = null;
	private Date crawledDate = new Date();
	private int magicNum = -1;
	
	public String getResourceDescription()
	{
		return resourceDescription;
	}
	public void setResourceDescription(String resourceDescription)
	{
		this.resourceDescription = resourceDescription;
	}
	public String getMovieDescription()
	{
		return movieDescription;
	}
	public void setMovieDescription(String movieDescription)
	{
		this.movieDescription = movieDescription;
	}
	public String getHost()
	{
		return host;
	}
	public void setHost(String host)
	{
		this.host = host;
	}
	public String getMovieId()
	{
		return movieId;
	}
	public void setMovieId(String movieId)
	{
		this.movieId = movieId;
	}
	public String getResourceURL()
	{
		return resourceURL;
	}
	public void setResourceURL(String resourceURL)
	{
		this.resourceURL = resourceURL;
	}
	public String getResourceType()
	{
		return resourceType;
	}
	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}
	

	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
		return this.getResourceType()+":"+this.getHost()+":"+this.getResourceURL()+":"+this.getResourceDescription()+":"+(this.getCrawledDate()!=null?this.getCrawledDate().toLocaleString():this.getCrawledDate());
	}
}
