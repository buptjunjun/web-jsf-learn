package org.easyGoingCrawler.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyGoingCrawler.util.Proxy;
import org.easyGoingCrawler.util.ProxyManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainPageServlet extends HttpServlet
{
	private static  Date startTime = null;
	private Date currentTime = null;
	Statistics statistics = null;
	private static  ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");

		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		List<String> hosts = (List<String>) appcontext.getBean("hosts");
		
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html");
		
		// set the begin time;
		if(startTime == null)
			startTime = new Date();
		this.currentTime = new Date();
		
		// by second
		int interval = (int) ((currentTime.getTime() - startTime.getTime())/1000);
		
		String host = request.getParameter("host");
		String action = request.getParameter("action");
		PrintWriter p = response.getWriter();
		p.println("<html><body>");
		p.println("<p2> server start at:"+startTime.toLocaleString()+"<p2><br>");
		p.println("<p2> now is :"+currentTime.toLocaleString()+"<p2><br>");
		p.println("<p2> the interval is : :"+interval/60+" minitues "+interval%60+" seconds<p2><br><br>");
		
		p.println("<a href='/host'>host</a>");
		p.println("<a href='/proxy'>proxy</a>");
		p.println("<a href='/'>mainpage</a>");
		
		p.println("<body><html>");
	}
	
	private String getProxyHtml()
	{
		String divs ="<div>";
		ProxyManager manager = ProxyManager.getInstance();
		List<Proxy> lp = manager.getLp();
		
		int i = 0;
		int count = 0;
		for(Proxy p:lp)
		{
			if(p.getConnectTime() <= manager.getMaxResponseTime())
				count++;
		}		
		divs+=" total proxies number:"+ lp.size()+"<br>";
		divs+=" useful proxies number:"+ count+"<br>";
		divs+="max response time:" +manager.getMaxResponseTime()+"<br>";
		
		for(Proxy p:lp)
		{
			divs+=i+":"+p.toString()+"<br>";
			if(p.getConnectTime() <= manager.getMaxResponseTime())
				count++;
		}
		
		divs+="</div>";
		return divs;
	}
	
}
