package junjun.chapter9;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		HttpSession s = req.getSession();
		s.invalidate();
		
	 	resp.setContentType("text/html;charset= gb2312");
	    PrintWriter p = resp.getWriter();
	    p.println("<html>");
	    p.println("<meta http=equiv=\"Pragma\" content=\"no-cache\">");
	    p.println("<head><title>logout again</title></head>");
	    p.println("<body>");
	    p.println("<br>");
	    p.println("<a href="+resp.encodeURL("login9")+ ">relogin</a>");
	    p.println("</body>");
	    p.println("</html>");

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
}
