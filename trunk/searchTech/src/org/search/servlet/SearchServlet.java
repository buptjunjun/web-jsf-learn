package org.search.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.search.beans.ResultBean;
public class SearchServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try
		{
			req.setCharacterEncoding("GBK");
			HttpSession session = req.getSession();
			
			//int a = 1/0;
			String queryStr = req.getParameter("searchwords");
			session.setAttribute("queryStr", queryStr);
			
			if(queryStr == null || queryStr.trim().equals(""))
			{
	//			RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
	//			dispacher.forward(req, resp);
				resp.sendRedirect("index.jsp");
				return;
			}
			queryStr = new String(queryStr.getBytes(),"GBK");
			ResultBean rb = new ResultBean(queryStr);
		
			if(rb == null)
			{
	//			RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
	//			dispacher.forward(req, resp);
				resp.sendRedirect("pages/search.jsp");
				return;
			}
			
			session.setMaxInactiveInterval(60*5);
			session.setAttribute("result", rb);
			
			//RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
			//dispacher.forward(req, resp);
			resp.sendRedirect("pages/search.jsp");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.sendError(400);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
}
