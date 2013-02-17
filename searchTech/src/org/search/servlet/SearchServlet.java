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
		req.setCharacterEncoding("GBK");
		String queryStr = req.getParameter("searchwords");
		
		if(queryStr == null)
		{
			RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
			dispacher.forward(req, resp);
			return;
		}
		queryStr = new String(queryStr.getBytes(),"GBK");
		ResultBean rb = new ResultBean(queryStr);
	
		if(rb == null)
		{
			RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
			dispacher.forward(req, resp);
			return;
		}
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(2*1000);
		session.setAttribute("result", rb);
		RequestDispatcher dispacher = req.getRequestDispatcher("/pages/search.jsp");
		dispacher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
}
