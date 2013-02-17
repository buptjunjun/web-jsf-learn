package org.cb.data;

import java.util.Date;
import java.util.List;

import org.cb.common.ObjectToField;

public class Blog 
{

	@ObjectToField(analyzed = false, fieldName = "id", store = true, type = "String")
	private String id = null;
	
	@ObjectToField(analyzed = false, fieldName = "host", store = false, type = "String")
	private String host = null;
	
	@ObjectToField(analyzed = false, fieldName = "url", store = false, type = "String")
	private String url = null;           // url
	
	@ObjectToField(analyzed = true, fieldName = "title", store = false, type = "String")
	String title = null;
	
	@ObjectToField(analyzed = true, fieldName = "content", store = false, type = "String")
	private String content = null;       // content of the bolg
	
	private  String html = null;
	
	@ObjectToField(analyzed = false, fieldName = "encode", store = false, type = "String")
	private String encode = "utf8";      // encode of the bolg content
	
	@ObjectToField(analyzed = false, fieldName = "blogerURL", store = false, type = "String")
	private String blogerURL = "";       // the author's url
	
	@ObjectToField(analyzed = false, fieldName = "comment", store = false, type = "Integer")
	private int comment = 0;			 // how many comments the blog has been earnt
	
	@ObjectToField(analyzed = false, fieldName = "visit", store = false, type = "Integer")
	private int visit = 0;				 // how many times the blog has been visited
	
	@ObjectToField(analyzed = true, fieldName = "tags", store = false, type = "List")
	private List<String> tags = null; 		 // tags of pictures
	
	@ObjectToField(analyzed = false, fieldName = "pictures", store = false, type = "Integer")
	private int pictures = 0;			 // how many pictues in the blog's content
	
	@ObjectToField(analyzed = false, fieldName = "postDate", store = false, type = "Date")
	private Date postDate = null;    
	
	@ObjectToField(analyzed = false, fieldName = "crawledDate", store = false, type = "Date")
	private Date crawledDate = new Date();
	
	private int magicNum = -1;

	
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
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getEncode()
	{
		return encode;
	}
	public void setEncode(String encode)
	{
		this.encode = encode;
	}
	public String getBlogerURL()
	{
		return blogerURL;
	}
	public void setBlogerURL(String blogerURL)
	{
		this.blogerURL = blogerURL;
	}
	public int getComment()
	{
		return comment;
	}
	public void setComment(int comment)
	{
		this.comment = comment;
	}
	public int getVisit()
	{
		return visit;
	}
	public void setVisit(int visit)
	{
		this.visit = visit;
	}
	public List<String> getTags()
	{
		return tags;
	}
	public void setTags(List<String>  tags)
	{
		this.tags = tags;
	}
	public int getPictures()
	{
		return pictures;
	}
	public void setPictures(int pictures)
	{
		this.pictures = pictures;
	}
	
	
	public Date getPostDate()
	{
		return postDate;
	}
	public void setPostDate(Date postDate)
	{
		this.postDate = postDate;
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
	public String getHtml()
	{
		return html;
	}
	public void setHtml(String html)
	{
		this.html = html;
	}
	

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "id = "+this.id +" host = " +this.host +" blogerUrl = "+ this.blogerURL +" url =" + this.url +" encode = " +this.encode 
			   + "title = "+title+" tags = "+ this.tags +" pictures=" +this.pictures+" visits = " +this.visit +"  comments = "+ comment + " magicNum = " +this.magicNum
			   +" postDate = "+this.postDate +" crawledDate = "+ this.crawledDate +"\n content Size = "+(this.getContent() == null ? 0:this.getContent().length());  
	}

}
