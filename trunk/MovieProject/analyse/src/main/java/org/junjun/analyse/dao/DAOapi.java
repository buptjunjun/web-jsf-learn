package org.junjun.analyse.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easyGoingCrawler.docWriter.Html;
import org.junjun.analyse.analyzer.bean.BResource;
import org.junjun.analyse.analyzer.bean.Movie;

public interface DAOapi
{
	public Html getNextHtml(Html html);
	public List<Html> getNextHtmls(Html html,int limit);
	public Html getHtml(String id);
	public String insertHtml(Html html);
	public String updateHtml(Html html,Set<String> updateFields);
	public String deleteHtml(String id);
	
	public Movie getNextMovie(String host);
	public Movie getMovie(String id);
	public String insertMovie(Movie movie);
	public String updateMovie(Movie movie,Set<String> updateFields);
	public String deleteMovie(String id);
	
	public BResource getNextResource();
	public List<BResource> getResources(String Movieid);
	public BResource getResource(String resourceid);
	public String insertResource(BResource resource);
	public String updateResource(BResource resource,Set<String> updateFields);
	public String deleteResources(String Movieid);
	public String deleteResource(String resourceid);
}
