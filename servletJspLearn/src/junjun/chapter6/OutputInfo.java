package junjun.chapter6;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打印 请求信息
 * @author andyWebsense
 *
 */
public class OutputInfo extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setContentType("text/html;charset=gb2312");
			
		PrintWriter p = response.getWriter();
		p.println("<html><head><title> Info table </title></head>");
		p.println("<body>");
		p.println("<table border = 1 align = center>");
		p.println("<caption>headers the server received </caption>");
		p.println("<tr><th>header name</th><th>header value<th></th></tr>");
		
		// 打印 请求报头信息
	    Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements())
		{
			String name = headers.nextElement();
			String value = request.getHeader(name);
			p.println("<tr><td>"+name+"</td><td>"+value+"</td></tr>");
		}
		// 获取客户端ip
		p.println("<tr><td>"+"remove ip"+"</td><td>"+request.getRemoteAddr()+"</td></tr>");
		// 获取客户端端口
		p.println("<tr><td>"+"remote port"+"</td><td>"+request.getRemotePort()+"</td></tr>");
		// 获取服务器ip
		p.println("<tr><td>"+"local ip"+"</td><td>"+request.getLocalAddr()+"</td></tr>");
		// 获取服务器端口
		p.println("<tr><td>"+"local port"+"</td><td>"+request.getLocalPort()+"</td></tr>");
		
		p.println("</table></body></html>");
		p.close();	
	}
}
