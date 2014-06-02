package byr.web.bean;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class ResultItem 
{
	private String id = null;
	private String url = null;
	private String title = null;
	private String content = null;
	private String section = null;
	private Date date = null;
	
	
	
	public String getSection()
	{
		return section;
	}
	public void setSection(String section)
	{
		this.section = section;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class) 
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
