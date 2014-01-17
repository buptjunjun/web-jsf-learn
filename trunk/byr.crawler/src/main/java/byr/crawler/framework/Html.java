package byr.crawler.framework;

import java.util.Date;

public class Html 
{
	private String id = null;
	private String host = null;
	private String url = null;           // url
	private String html = null;
	private String encode = "utf8";      // encode of the bolg content
	private Date crawledDate = new Date();
	private Date createdDate = new Date();
	
	int magicNum = -1;


	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getEncode()
	{
		return encode;
	}
	public void setEncode(String encode)
	{
		this.encode = encode;
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
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "id = "+this.id +" host = " +this.host+" url =" + this.url +" encode = " +this.encode 
			 +" crawledDate = "+ this.crawledDate +"\n html Size = "+(this.html == null ? 0:this.html.length());  
	}

}
