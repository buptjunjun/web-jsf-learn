package org.easyGoingCrawler.monitor;
import java.io.IOException;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

 /**
  * jetty hello world server
  * use http://localhost:8081/ to visit this server.
  * @author andyWebsense
  *
  */
public class MainServer
{
      
    public static void main(String[] args) throws Exception  
    {  
    	 Server server = new Server(8081);  
         ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
         context.setContextPath("/");
         server.setHandler(context);        
         // http://localhost:8081/hello   
         context.addServlet(new ServletHolder(new EGCrawlerInfoServlet()), "/hello");  
         server.setHandler(context);       
         server.start();  
         server.join();  
    }  
}