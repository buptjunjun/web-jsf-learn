package org.easyGoingCrawler.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Url;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * statics of amount
 * @author andyWebsense
 *
 */
public class Statistics
{
	private static  Date startTime = null;
	private Date currentTime = null;
	private EGDAOMongo Mongo = null;
	
	public Statistics(Date start,Date end)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongo.class);
	}
	
	public String getHtml(String host)
	{
		List<String> divs =  this.getLatestUrl(host);

		String stat = getStatistics(host,this.startTime);
		String content = "";
		content +="statics:"+ stat;
		content +="Url:<br>";
		for(String div:divs)
		{
			content+=div;
		}
		
		divs =  this.getLatestBlog(host);
		content +="Blogs:<br>";
		for(String div:divs)
		{
			content+=div;
		}
		content+="</div>";
		return content;
	}
	
	public List<String> getLatestBlog(String hostName)
	{
		List<Blog> blogs = this.Mongo.getLatestBlog(hostName, 5);
		List<String> divs = new ArrayList<String>();
		
		for(Blog blog : blogs)
		{
			divs.add(getBlogDiv(blog));
		}
		
		return divs;
	}
	
	public String getBlogDiv(Blog blog)
	{
		String content = blog.getContent();
		if(content != null)
			content = content.substring(0, (content.length() <=200 ? content.length() : 200 ));
		
		String div = "<div>";
		div += blog.getId()+"<br>";
		div += blog.getHost()+"<br>";
		div += blog.getBlogerURL()+"<br>";
		div += blog.getTitle()+"<br>";
		div += content + "<br>";
		div += blog.getTags()+ "<br>";
		div += blog.getPostDate().toLocaleString()+ "<br>";
		div += blog.getCrawledDate().toLocaleString()+ "<br>";
		div += "</div>";
		return div;
	}
	
	public String getStatistics(String hostName,Date date)
	{  
	    Long fetchedUrl = this.Mongo.getCollectionCountByTimeByHost(hostName,"lastCrawled", date, new Date(), Url.class);
	    Long fetchedBlog = this.Mongo.getCollectionCountByTimeByHost(hostName,"crawledDate", date, new Date(), Blog.class);	
	    Long totalUrl = this.Mongo.getCollectionCountByHost(hostName, Url.class);
		Long totalBlog = this.Mongo.getCollectionCountByHost(hostName,Blog.class);
		

		String div = "<div>";
		div +="  totalUrl:"+totalUrl+"<br>";
		div +="  totalBlog:"+totalBlog+"<br>";
		div +=hostName+"  fetchedUrl:"+fetchedUrl+"<br>";
		div +=hostName+"  fetchedBlog:"+fetchedBlog+"<br>";		
		div+="</div>";
		
		return div;
	}
	
	public String getTotal()
	{	    
		Long totalUrl = this.Mongo.getCollectionCount(Url.class);
		Long totalBlog = this.Mongo.getCollectionCount(Blog.class);

		String div = "<div>";
		div +="  totalUrl:"+totalUrl+"<br>";
		div +="  totalBlog:"+totalBlog+"<br>";
		div+="</div>";
		
		return div;
	}
	
	public String getTotalHost(String host)
	{	    
		Long totalUrl = this.Mongo.getCollectionCountByHost(host, Url.class);
		Long totalBlog = this.Mongo.getCollectionCountByHost(host,Blog.class);

		String div = "<div>";
		div +=host+"  totalUrl:"+totalUrl+"<br>";
		div +=host+"  totalBlog:"+totalBlog+"<br>";
		div+="</div>";
		
		return div;
	}
	
	public String getUrlDiv(Url url)
	{
		String div = "<div>";
		div += url.getId()+"<br>";
		div += url.getHost()+"<br>";
		div += url.getUrl()+"<br>";
		div += url.getType()+"<br>";
		div += url.getLastCrawled().toLocaleString()+"<br>";
		div += "</div>";
		return div;
	}
	
	public List<String> getLatestUrl(String host)
	{
		List<Url> urls = this.Mongo.getLatestUrl(host, 10);
		List<String> divs = new ArrayList<String>();
		
		for(Url url : urls)
		{
			divs.add(getUrlDiv(url));
		}
		
		return divs;
	}
	 
}
