package org.junjun.analyse.dao.mongo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Url;
import org.junjun.analyse.analyzer.bean.BResource;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.mongo.DAOMongo;


public class MongoDAOapi implements DAOapi
{

	private DAOMongo mongo= null;
	static public int MAGICNUM = -1;
	static public int DELETE = -2;
	public MongoDAOapi()
	{
		mongo = new DAOMongo("42.96.143.59", 27017, "moviedb");
	}
	 
	public Html getNextHtml(Html html)
	{
		List<Html> rets =  getNextHtmls(html,1);
		
		if(rets!=null && rets.size() > 0)
			return rets.get(0);
		
		return null;
	}

	public List<Html> getNextHtmls(Html html, int limit)
	{
		if(limit < 1)
			limit = 1;
		
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("host", html.getHost());
		Map<String,Object> constrainGT = new HashMap<String,Object>(1);
		constrainGT.put("crawledDate", html.getCrawledDate());
		List<Html> rets = this.mongo.search(null, constrainGT, constrains, "crawledDate", DAOMongo.ASCENDING, limit, Html.class);		
		return rets;
	}
	
	public String insertHtml(Html html)
	{
		this.mongo.insert(html);
		return null;
	}

	public String updateHtml(Html html, Set<String> updateFields)
	{
		
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("id", html.getId());
		mongo.update(html, null, null, constrains, updateFields);
		return null;
	}

	public String deleteHtml(String id)
	{
		
		Html h = new Html();
		h.setId(id.trim());
		h.setMagicNum(DELETE);
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		this.updateHtml(h, updateFields);
		return null;
	}

	public BResource getNextResource()
	{
		
		return null;
	}

	public String insertResource(BResource resource)
	{
		this.mongo.insert(resource);
		return null;
	}

	public String updateResource(BResource resource,
			Set<String> updateFields)
	{
		
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("id", resource.getId());
		mongo.update(updateFields, null, null, constrains, updateFields);
		
		return null;
	}

	public String deleteResources(String Movieid)
	{
		BResource res = new BResource();
		res.setMovieId(Movieid.trim());
		res.setMagicNum(DELETE);
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		this.updateResource(res, updateFields);
		return null;
	}

	public String deleteResource(String resourceid)
	{
		
		BResource res = new BResource();
		res.setId(resourceid.trim());
		res.setMagicNum(DELETE);
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		this.updateResource(res, updateFields);
		return null;
	}

	public Movie getNextMovie(String host)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String insertMovie(Movie movie)
	{
		// TODO Auto-generated method stub
		this.mongo.insert(movie);
		return null;
	}


	public String deleteMovie(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String updateMovie(Movie movie, Set<String> updateFields)
	{
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("id", movie.getId());
		mongo.update(movie, null, null, constrains, updateFields);
		return null;
	}

	public Html getHtml(String id)
	{
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("id", id.trim());
		List<Html> rets = mongo.search(null, null, constrains, null, -1, 1, Html.class);
		if(rets!=null && rets.size() > 0)
			return rets.get(0);	
		return null;
	}

	public Movie getMovie(String id)
	{
		// TODO Auto-generated method stub
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("id", id.trim());
		List<Movie> rets = mongo.search(null, null, constrains, null, -1, 1, Movie.class);
		if(rets!=null && rets.size() > 0)
			return rets.get(0);	
		return null;
	}

	public List<BResource> getResources(String Movieid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BResource getResource(String resourceid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Movie> getNextMovies(Movie movie , int limit)
	{
		if(limit < 1)
			limit = 1;
		
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		Map<String,Object> constrainGT = new HashMap<String,Object>(1);
		constrainGT.put("crawledDate", movie.getCrawledDate());
		List<Movie> rets = this.mongo.search(null, constrainGT, constrains, "crawledDate", DAOMongo.ASCENDING, limit, Movie.class);		
		return rets;
	}

	public List<Url> getNextUrls(Url url, int limit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Url getUrl(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String insertUrl(Url url)
	{
		// TODO Auto-generated method stub
		this.mongo.insert(url);
		return null;
	}

	public String deleteUrl(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String insertObj(Object obj)
	{
		// TODO Auto-generated method stub
		this.mongo.insert(obj);
		
		return null;
	}
	


}
