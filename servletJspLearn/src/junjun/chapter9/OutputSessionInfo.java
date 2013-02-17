package junjun.chapter9;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

/**
 * 打印session的信息
 * @author andyWebsense
 *
 */
public class OutputSessionInfo  
{
	public static void printSessionInfo(PrintWriter p,HttpSession s)
	{
		if(s==null) return;
		
		p.println("<table border=1>");
		p.println("<th>");
		if(s.isNew())
		{
			p.println("new session ");
		}
		else
		{
			p.println("old session");
		}
		p.println("</th>");
		
		// session id
		p.println("<tr>");
		p.println("<td>"+"Session id" +"</td>");
		p.println("<td>"+s.getId() +"</td>");	
		p.println("</tr>");
		
		// create time
		p.println("<tr>");
		p.println("<td>"+"create time" +"</td>");
		p.println("<td>"+s.getCreationTime() +"</td>");	
		p.println("</tr>");
		
		// last acess time
		p.println("<tr>");
		p.println("<td>"+"last acess time" +"</td>");
		p.println("<td>"+s.getLastAccessedTime() +"</td>");	
		p.println("</tr>");
		
		// max inactive interval
		p.println("<tr>");
		p.println("<td>"+"max inactive interval" +"</td>");
		p.println("<td>"+s.getMaxInactiveInterval() +"</td>");	
		p.println("</tr>");
		
		p.println("</table>");
	}
}
