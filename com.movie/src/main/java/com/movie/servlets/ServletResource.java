package com.movie.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

public class ServletResource extends HttpServlet
{
	private DBMovieService ms = null; 
	private DBResourceService rs = null;
	
	private static HashSet<String> field2updateResource = new HashSet<String>();
	{
		field2updateResource.add("magicNum");
	}
	
	public ServletResource()
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		//movie name
		String moviename = req.getParameter("name");
		String charset = req.getParameter("charset");
		String url = req.getParameter("url");
		String magicNumStr = req.getParameter("magicNum");
		int magicNum = Integer.valueOf(magicNumStr);
		if(StringUtils.isEmpty(charset))
			charset = "utf8";
		
		
		if(StringUtils.isEmpty(url))
		{
			resp.getWriter().write("error");
			return;
		}
		
		String ret = getMovieAndResources(url,magicNum);
		//ret = new String(ret.getBytes(),charset);
		resp.setCharacterEncoding("UTF_8");
		resp.setHeader("Content-type","text/html;charset=UTF-8");
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().write(ret);
	}
	
	
	/**
	 * update --> type,magicNum,id
	 * add --->   type,magicNum,url,movieId
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String type = req.getParameter("type");  // add or update
		String errorResponse = null;
		if(null ==type || type.trim().equals(""))
		{
			errorResponse += "type is none";			
		}
		
		BResource resource = new BResource();
		if(type.equals("update"))
		{
			String magicNum = req.getParameter("magicNum");
			int magic = Integer.parseInt(magicNum);
			String id = req.getParameter("id");		
			
			if(null ==magicNum || magicNum.trim().equals(""))
				errorResponse+=","+"magicNum is none";

			if(null ==id || id.trim().equals(""))
				errorResponse+=","+"id is none";
			
			resource.setId(id);
			resource.setMagicNum(magic);
			
			if(null ==errorResponse)
				this.rs.update(resource, field2updateResource);
			
		}
		else if(type.equals("add"))
		{	
			String resourceURL = req.getParameter("url");
			String movieId = req.getParameter("movieId");
			String magicNum = req.getParameter("magicNum");
			
			if(null ==magicNum || magicNum.trim().equals(""))
				errorResponse+=","+"magicNum is none";
			
			if(null ==resourceURL || resourceURL.trim().equals(""))
				errorResponse+=","+"resourceURL is none";

			if(null ==movieId || movieId.trim().equals(""))
				errorResponse+=","+"movieId is none";
			
			int magic = Integer.parseInt(magicNum);
			resource.setMovieId(movieId);
			resource.setResourceURL(resourceURL);
			resource.setId(Converter.urlEncode(resourceURL));
			resource.setMagicNum(magic);
			
			if(null != errorResponse)
				this.rs.add(resource);
			
		}	
		PrintWriter print =	resp.getWriter();

		if(null==errorResponse)
		{
			print.println(errorResponse);
			return ;
		}
		print.println("ok");
		return;
	}
	
	private String url2id(String url)
	{
		return Converter.urlEncode(url);
	}
	
	public String getMovieAndResources(String url,int magicNum)
	{
		Gson gson = new Gson();
		String id = url2id(url.trim());
		
		List<BResource> lr = this.rs.get(id,magicNum);
		String ret = "[]";
		if(lr == null || lr.size() <= 0)
			return ret;
		
		List<Object> movies = new ArrayList<Object>(1);

		movies.addAll(lr);	
		ret = gson.toJson(movies);		
		return ret; 
	}
	
	
	private String getMovies(String name)
	{	
		List<Movie> movies = this.ms.query(name);
		Gson gson = new Gson();
		return gson.toJson(movies);
		
	}
	
	static public void main(String [] args)
	{
		DAOMongo mongo = new DAOMongo("42.96.143.59",27017 , "moviedb");
		DBMovieService ms = new DBMovieServiceImpl(mongo);
		DBResourceService rs = new DBResourceServiceImpl(mongo);
		
		/*String url = "http://movie.douban.com/subject/3793023";
		String ret = sr.getMovieAndResources(url);
		System.out.println(ret);*/
	}
	
}
