package junjun.chapter9;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginchk")
public class LoginChkServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		   req.setCharacterEncoding("gb2312");
		   String user = req.getParameter("user");
		   String password = req.getParameter("password");
		   
		   if(user == null || password == null || user.equals("") || password.equals(""))
		   {
			   resp.sendRedirect(resp.encodeRedirectURL("login9"));
			   return;
		   }
		   else
		   {
			   HttpSession s = req.getSession();
			   s.setAttribute("user", user);
			   resp.sendRedirect(resp.encodeRedirectURL("greet"));
			   return;
		   }
	}	
}
