package junjun.chapter9;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login9")
public class LoginServlet extends HttpServlet
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
		    resp.setContentType("text/html;charset= gb2312");
		    HttpSession s = req.getSession();
		    String user = (String) s.getAttribute("user");
		    PrintWriter p = resp.getWriter();
		    p.println("<html>");
		    p.println("<meta http=equiv=\"Pragma\" content=\"no-cache\">");
		    p.println("<head><title>µÇÂ½Ò³Ãæ</title></head>");
		    p.println("<body>");
		    
		    OutputSessionInfo.printSessionInfo(p, s);
		    p.println("<br>");
		    p.println("<form method = post action ="+ resp.encodeURL("loginchk")+">");
		    p.println("user name");
		    p.println("<input type=text name=user />");
		    p.println(" password");
		    p.println("<input type=password name=password />");
		    p.println("<input type=submit value=submit />");
		    p.println("</form>");
		    p.println("</body>");
		    p.println("</html>");
	}	
}
