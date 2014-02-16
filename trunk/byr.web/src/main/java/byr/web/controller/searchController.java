package byr.web.controller;

import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import byr.web.logic.ResultSearcher;
import byr.web.util.UIlabelStore;

@Controller
public class searchController 
{ 
	private ResultSearcher rs = new ResultSearcher();
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	 public String showHomePage (Map<String,Object> model )
	 {
         model.put("search_title", UIlabelStore.getMessage("search_title"));
         model.put("search_head_title", UIlabelStore.getMessage("search_head_title"));
         model.put("search_head_desc", UIlabelStore.getMessage("search_head_desc"));
         model.put("search_searchbtn", UIlabelStore.getMessage("search_searchbtn"));
         model.put("contact", UIlabelStore.getMessage("contact"));
	     return "search";
    }
}

