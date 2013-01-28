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
 * ����ת��
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
			
			//p.println("<html><head><title> Info table </title></head><body>"); �����������
			//// ��success.jsp��������ݲ��뵽PortalServlet
			//p.println("hello test</body></html>"); Ҳ�����
			//������ʱ PortalServlet�������success.jsp�����ֻ��
			ServletContext context = this.getServletContext();
			RequestDispatcher d = context.getRequestDispatcher("/success.jsp");
			d.include(req, resp);
		}
		else
		{
			// ������ת����login2 servlet���Կͻ��˵���Ӧ����login2������
			//ǰ�� p.println("<html><head><title> Info table </title></head><body>"); ��������ᱻ�����
			// p.println("hello test</body></html>"); Ҳ�������
			//������ֻ��login2�������portalServlet��������������
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
