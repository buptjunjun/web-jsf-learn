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

	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		//movie name
		String moviename = req.getParameter("name");
		String charset = req.getParameter("charset");
		String url = req.getParameter("url");
		
		if(StringUtils.isEmpty(charset))
			charset = "utf8";
		if(this.rs == null);
		{
			// TODO Auto-generated constructor stub
			DAOMongo mongo = new DAOMongo("42.96.143.59",27017, "moviedb");
			DBMovieService ms = new DBMovieServiceImpl(mongo);
			DBResourceService rs = new DBResourceServiceImpl(mongo);
			this.ms = ms;
			this.rs = rs;
		}
		
		if(StringUtils.isEmpty(url))
		{
			resp.getWriter().write("error");
			return;
		}
		
		String ret = getMovieAndResources(url);
		//ret = new String(ret.getBytes(),charset);
		resp.setCharacterEncoding("UTF_8");//设置Response的编码方式为UTF-8	 
		resp.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用
		resp.setContentType("text/html;charset=UTF-8");//同上句代码作用一样	 
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
		String type = req.getParameter("type");  // add or update
		String errorResponse = "";
		if(StringUtil.isBlank(type))
		{
			errorResponse += "type is none";			
		}
		
		BResource resource = new BResource();
		if(type.equals("update"))
		{
			String magicNum = req.getParameter("magicNum");
			int magic = Integer.parseInt(magicNum);
			String id = req.getParameter("id");		
			
			if(StringUtil.isBlank(magicNum))
				errorResponse+=","+"magicNum is none";

			if(StringUtil.isBlank(id))
				errorResponse+=","+"id is none";
			
			resource.setId(id);
			resource.setMagicNum(magic);
			
			if(!StringUtil.isBlank(errorResponse))
				this.rs.update(resource, field2updateResource);
			
		}
		else if(type.equals("add"))
		{	
			String resourceURL = req.getParameter("url");
			String movieId = req.getParameter("movieId");
			String magicNum = req.getParameter("magicNum");
			
			if(StringUtil.isBlank(magicNum))
				errorResponse+=","+"magicNum is none";
			
			if(StringUtil.isBlank(resourceURL))
				errorResponse+=","+"resourceURL is none";

			if(StringUtil.isBlank(movieId))
				errorResponse+=","+"movieId is none";
			
			int magic = Integer.parseInt(magicNum);
			resource.setMovieId(movieId);
			resource.setResourceURL(resourceURL);
			resource.setId(Converter.urlEncode(resourceURL));
			resource.setMagicNum(magic);
			
			if(!StringUtil.isBlank(errorResponse))
				this.rs.add(resource);
			
		}	
		PrintWriter print =	resp.getWriter();

		if(StringUtil.isBlank(errorResponse))
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
	
	public String getMovieAndResources(String url)
	{
		Gson gson = new Gson();
		String id = url2id(url);
		Movie movie = this.ms.get(id);	
		if(movie == null)
			return null;
		
		List<BResource> lr = this.rs.get(movie.getId());
		if(lr == null || lr.size() <= 0)
			return null;
		
		List<Object> movies = new ArrayList<Object>(1);
		
		movies.add(movie);
		movies.addAll(lr);
		
		return gson.toJson(movies);
		
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
