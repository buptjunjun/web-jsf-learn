package org.easyGoingCrawler.analyzer;

import java.util.HashMap;
import java.util.List;



import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Html;
import org.junit.Test;

public class fillNullTitle
{

	@Test
	public void fillNullTitle()
	{
		DAOMongo mongo = new DAOMongo("blogdb");
    	HashMap map = new HashMap<String,String>();
    	map.put("host", "ibm.cn");
    	map.put("title", null);
    	Analyzer analyzer = new IBMBlogAnalyzer();
    	List<Blog> ret = mongo.searchBlog(map, 100, Blog.class);   	
    	while(ret != null && ret.size()>0)
    	{
    		for(Blog blog:ret)
    		{
	    		HashMap hm = new HashMap();
	    		hm.put("_id", blog.getId());
	    		 List<Html> h = mongo.searchBlog(hm,1,Html.class);
	    		if(h == null || h.size() !=1) 
	    		{
	    			System.out.println("html == empty");
	    			//continue;
	    		}
	    	    org.easyGoingCrawler.docWriter.Blog tmp = (org.easyGoingCrawler.docWriter.Blog) analyzer.analyze(blog.getHost(), blog.getEncode(),h.get(0).getHtml() );
	    		blog.setTitle(tmp.getTitle());
	    		
	    		
	    		
	    		mongo.updateBlog(blog);
	    		//List<Blog> ret1 = mongo.searchBlog(hm, 1, Blog.class);
    		}
    		//System.out.println();
    		
    		ret = mongo.searchBlog(map, 100, Blog.class);
    	}
    	
	}

}
