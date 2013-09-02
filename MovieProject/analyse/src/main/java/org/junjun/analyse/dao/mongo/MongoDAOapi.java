package org.junjun.analyse.dao.mongo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easyGoingCrawler.docWriter.Html;
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
	 
	public Html getNextHtml(String host)
	{
		Map<String,Object> constrains = new HashMap<String,Object>(1);
		constrains.put("host", host);
		constrains.put("magicNum", MAGICNUM);
		List<Html> rets = this.mongo.search(null, null, constrains, "crawledDate", DAOMongo.ASCENDING, 1, Html.class);
		
		if(rets!=null && rets.size() > 0)
			return rets.get(0);
		
		return null;
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

	public Html getNextMovie(String host)
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

	public String updateMovie(Html html, Set<String> updateFields)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String deleteMovie(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String updateMovie(Movie movie, Set<String> updateFields)
	{
		// TODO Auto-generated method stub
		return null;
	}
	


}
