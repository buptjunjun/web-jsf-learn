package junjun.chapter6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet2 extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter p = resp.getWriter();
		
		p.println("<html><head><title> Info table </title></head><body>");
		p.println("<form action=portal method=post>");
		p.println("user name<input type=text name=user ><br>");
		p.println("password <input type=password name=password ><br>");
		p.println(" <input type=submit value=submit ><br>");
		p.println("<input type=reset vallue=reset ><br>");
		p.println("</body></html>");
		p.close();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
}
