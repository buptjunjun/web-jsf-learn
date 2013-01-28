package junjun.chapter6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面访问量统计 使用 ServletContext来保持访问次数
 * @author buptjunjun
 *
 */
public class CountServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		ServletContext context = this.getServletConfig().getServletContext();
		
		// 从servletContext中取出访问次数
		Integer count = (Integer) context.getAttribute("count");
		if(count == null)
			count = 1;
		else 
			count++;
		
		String user = request.getParameter("user");
		response.setContentType("text/html");
			
		PrintWriter p = response.getWriter();
		p.println("<html><head><title> "+"page visit statistics"+" </title></head>");
		p.println("<body> this page have been visited"+ count +"times </body></html>");
		p.close();
		
		// 将访问次数放入servletContext中
		context.setAttribute("count", count);
	}	
}
