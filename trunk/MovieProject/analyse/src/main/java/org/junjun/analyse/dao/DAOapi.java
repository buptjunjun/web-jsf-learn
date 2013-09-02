package org.junjun.analyse.dao;

import java.util.Map;
import java.util.Set;

import org.easyGoingCrawler.docWriter.Html;
import org.junjun.analyse.analyzer.bean.BResource;
import org.junjun.analyse.analyzer.bean.Movie;

public interface DAOapi
{
	public Html getNextHtml(String host);
	public String insertHtml(Html html);
	public String updateHtml(Html html,Set<String> updateFields);
	public String deleteHtml(String id);
	
	public Html getNextMovie(String host);
	public String insertMovie(Movie movie);
	public String updateMovie(Movie movie,Set<String> updateFields);
	public String deleteMovie(String id);
	
	public BResource getNextResource();
	public String insertResource(BResource resource);
	public String updateResource(BResource resource,Set<String> updateFields);
	public String deleteResources(String Movieid);
	public String deleteResource(String resourceid);
}
