package org.movier.service;

import java.util.List;

import org.movier.bean.BResource;
import org.movier.bean.Rating;

public interface  ResourceService 
{
	public BResource getResource(String id);
	public List<BResource> getResources(String id);
	public String updateResource(BResource res);
	public String addResource(BResource res);
	public String delResource(String id);
}
