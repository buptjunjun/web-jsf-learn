package junjun.chapter6;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=gb2312");
		String name = req.getParameter("user");
		String password = req.getParameter("password");
		
		if (name!=null && password!=null 
			&& name.equalsIgnoreCase("zhangsan") && password.equals("1234"))
		{
			//sendRedirect 将用户的请求重定向到 success.jsp中
			//该方法通过修改HTTP协议的HEADER部分(设置状态代码302,命令浏览器发重新发送请求),对浏览器下达重定向指令的，
			//让浏览器对在location中指定的URL提出请求，使浏览器显示重定向网页的内容
			resp.sendRedirect("success.jsp"); 
		}
		else
		{
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "服务器忙，请稍后登陆");
		}
		
		resp.flushBuffer();		
	}
}
