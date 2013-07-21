package com.movie.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.helper.StringUtil;

import com.google.gson.Gson;
import com.movie.dao.service.DBMovieService;
import com.movie.dao.service.DBResourceService;
import com.movie.dao.serviceImpl.DBMovieServiceImpl;
import com.movie.dao.serviceImpl.DBResourceServiceImpl;

public class ServletMovie extends HttpServlet
{
	private DBMovieService ms = null; 
	private DBResourceService rs = null;
	private Gson gson = new Gson();
	
	private static HashSet<String> field2updateResource = new HashSet<String>();
	{
		field2updateResource.add("magicNum");
	}
	
	public ServletMovie()
	{
		if(this.rs == null);
		{
			// TODO Auto-generated constructor stub
			DAOMongo mongo = new DAOMongo("42.96.143.59",27017, "moviedb");
			DBMovieService ms = new DBMovieServiceImpl(mongo);
			DBResourceService rs = new DBResourceServiceImpl(mongo);
			this.ms = ms;
			this.rs = rs;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ?type=get&magicNum=11
	 * ?type=update&magicNum=11&id=111
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String type = req.getParameter("type");  // add or update
		String errorResponse = "";
		if(null == type || type.trim().equals(""))
		{
			errorResponse += "type is none";			
		}
		
		Movie movie = new Movie();
		resp.setCharacterEncoding("UTF_8");
		resp.setHeader("Content-type","text/html;charset=UTF-8");
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.setHeader("Access-Control-Allow-Origin", "*");

		String ret = null;
		try
		{
			if(type.equals("update"))
			{
				String magicNum = req.getParameter("magicNum");
				int magic = Integer.parseInt(magicNum);
				String id = req.getParameter("id");		
				
				if(StringUtil.isBlank(magicNum))
					errorResponse+=","+"magicNum is none";
	
				if(StringUtil.isBlank(id))
					errorResponse+=","+"id is none";
				
				movie.setId(id);
				movie.setMagicNum(magic);
				
				if(null !=errorResponse )
					this.ms.update(movie, field2updateResource);
				ret = "over";
				
			}
			else if(type.equals("get"))
			{
				String magicNum = req.getParameter("magicNum");
				int magic = Integer.parseInt(magicNum);
	
				String limitStr = req.getParameter("limit");
				int limit = Integer.parseInt(limitStr);
				
				if(limit > 10 || limit < 0)
					limit = 10;
				
				List<Movie> ls = this.ms.get(magic, limit);
				ret = gson.toJson(ls);
			}
		
		}
		catch(Exception e)
		{
			errorResponse=e.toString();
		}
		
		
		if(!StringUtil.isBlank(errorResponse))
			resp.getWriter().write(errorResponse);
		else
		{
			resp.getWriter().write(ret);
			return;
		}
		
		
	}
	
}
