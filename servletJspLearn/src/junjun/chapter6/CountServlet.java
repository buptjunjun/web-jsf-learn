package junjun.chapter6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ҳ�������ͳ�� ʹ�� ServletContext�����ַ��ʴ���
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
		
		// ��servletContext��ȡ�����ʴ���
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
		
		// �����ʴ�������servletContext��
		context.setAttribute("count", count);
	}	
}
