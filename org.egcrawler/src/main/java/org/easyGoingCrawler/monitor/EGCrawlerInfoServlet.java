package org.easyGoingCrawler.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EGCrawlerInfoServlet extends HttpServlet
{
	private static  Date startTime = null;
	private Date currentTime = null;
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
		request.setCharacterEncoding("GBK");
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
		
		p.println("<form name = 'form' method='post' action='EGCrawlerInfoServlet'>");
		p.println("host:<input name='host' />");
		p.println("action:<input name='action' />");
		p.println(" <input type='submit' value='submit' /><br>");
		p.println("</form>");
		if (host == null)
			host = "unknow";
		
		p.println("<p2> host:"+host+"<p2>");
		p.println("<body><html>");
	}
}
