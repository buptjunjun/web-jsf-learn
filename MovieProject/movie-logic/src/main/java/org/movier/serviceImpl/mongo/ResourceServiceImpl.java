package org.movier.serviceImpl.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.movier.bean.BResource;
import org.movier.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceServiceImpl implements ResourceService{
	
	public static final int LIMIT = 20; 
	@Autowired
	private DAOMongo mongo;
	
	public BResource getResource(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * get the resources of a movie according to the movieID;
	 */
	public List<BResource> getResources(String id) {
		// TODO Auto-generated method stub
		Map constrains = new HashMap();
		constrains.put("movieId", id);
		List<BResource> ret = mongo.search(null, null, constrains, null, -1, LIMIT, BResource.class);
		return ret;
		//return this.mock();
	}

	public String updateResource(BResource res) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addResource(BResource res) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delResource(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<BResource> mock()
	{
		List<BResource> ret = new ArrayList<BResource>();
		BResource res = new BResource();
		res.setId("id333");
		res.setResourceType("qvod");
		res.setResourceURL("http://www.dd13.tv/rh/28046/index.html");
		res.setResourceDescription("松全集百度影音在线观看,武松电视剧,主演:游大庆 潘长江 汤镇业 李永林 杨洋,剧情介绍:武松回乡探兄，路经景阳冈，拳毙猛虎，阳谷县街头遇兄，武大已娶潘金莲为妻，金莲见武松人");
		ret.add(res);
		
		res = new BResource();
		res.setId("id336");
		res.setResourceType("qvod");
		res.setResourceURL("http://www.dd13.tv/rh/28046/index.html");
		res.setResourceDescription("松全集百度影音在线观看,武松电视剧,主演:游大庆 潘长江 汤镇业 李永林 杨洋,剧情介绍:武松回乡探兄，路经景阳冈，拳毙猛虎，阳谷县街头遇兄，武大已娶潘金莲为妻，金莲见武松人");
		ret.add(res);
		
		res = new BResource();
		res.setId("id336");
		res.setResourceType("baidu");
		res.setResourceURL("http://www.dd13.tv/rh/28046/index.html");
		res.setResourceDescription("松全集百度影音在线观看,武松电视剧,主演:游大庆 潘长江 汤镇业 李永林 杨洋,剧情介绍:武松回乡探兄，路经景阳冈，拳毙猛虎，阳谷县街头遇兄，武大已娶潘金莲为妻，金莲见武松人");
		ret.add(res);
		
		res = new BResource();
		res.setId("id336");
		res.setResourceType("baidu");
		res.setResourceURL("http://www.dd13.tv/rh/28046/index.html");
		res.setResourceDescription("松全集百度影音在线观看,武松电视剧,主演:游大庆 潘长江 汤镇业 李永林 杨洋,剧情介绍:武松回乡探兄，路经景阳冈，拳毙猛虎，阳谷县街头遇兄，武大已娶潘金莲为妻，金莲见武松人");
		
		ret.add(res);
		return ret;
	}
	
}
