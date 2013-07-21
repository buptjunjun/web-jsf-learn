package com.movie.dao.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.docWriter.Movie;

import com.movie.dao.service.DBResourceService;

public class DBResourceServiceImpl implements DBResourceService
{
	private DAOMongo egMongoResource = null;
	public DBResourceServiceImpl(DAOMongo egMongoResource)
	{
		this.egMongoResource = egMongoResource;
	}
	
	public List<BResource> get(String movieid,int magicNum)
	{
		HashMap constrains = new HashMap(1);
		constrains.put("movieId", movieid);
		constrains.put("magicNum", magicNum);
		List<BResource> ret = egMongoResource.search(constrains, 30, BResource.class);
		
		return ret;
	}
	
	public List<BResource> get(String movieid)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public  String update(BResource r,Set<String> field2update)
	{
		// TODO Auto-generated method stub
		Map constrains = new HashMap<String,String>();
		constrains.put("_id", r.getId());
		this.egMongoResource.update(r, constrains,field2update);
		return null;
	}

	public String delete(BResource r)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String add(BResource r)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public String update(BResource r)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
