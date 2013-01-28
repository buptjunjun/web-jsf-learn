package junjun.chapter6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求转发
 * @author andyWebsense
 *
 */
public class PortalServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter p = resp.getWriter();
		
		p.println("<html><head><title> Info table </title></head><body>");
		
		String name = req.getParameter("user");
		String password = req.getParameter("password");
		
		if (name!=null && password!=null 
			&& name.equalsIgnoreCase("zhangsan") && password.equals("1234"))
		{
			
			//p.println("<html><head><title> Info table </title></head><body>"); 的输出将保留
			//// 将success.jsp输出的内容插入到PortalServlet
			//p.println("hello test</body></html>"); 也会输出
			//最后输出时 PortalServlet的输出和success.jsp的输出只和
			ServletContext context = this.getServletContext();
			RequestDispatcher d = context.getRequestDispatcher("/success.jsp");
			d.include(req, resp);
		}
		else
		{
			// 将请求转发到login2 servlet，对客户端的响应都由login2来处理。
			//前面 p.println("<html><head><title> Info table </title></head><body>"); 的输出将会被清掉，
			// p.println("hello test</body></html>"); 也不会输出
			//最后输出只是login2的输出，portalServlet的输出被清除掉。
			ServletContext context = this.getServletContext();
			RequestDispatcher d = context.getRequestDispatcher("/login2");
			d.forward(req, resp);
		}
		
		p.println("hello test</body></html>");
		p.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		this.doGet(req, resp);
	}
}
