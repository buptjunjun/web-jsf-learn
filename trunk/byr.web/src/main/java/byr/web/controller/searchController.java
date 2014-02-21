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
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	 public String showHomePage (Map<String,Object> model )
	 {
         model.put("search_title", UIlabelStore.getMessage("search_title"));
         model.put("search_head_title", UIlabelStore.getMessage("search_head_title"));
         model.put("search_head_desc", UIlabelStore.getMessage("search_head_desc"));
         model.put("search_searchbtn", UIlabelStore.getMessage("search_searchbtn"));
         model.put("search_advanced", UIlabelStore.getMessage("search_advanced"));
         model.put("contact", UIlabelStore.getMessage("contact"));

         model.put("search_date", UIlabelStore.getMessage("search_date"));
         model.put("search_sort", UIlabelStore.getMessage("search_sort"));
         model.put("search_field", UIlabelStore.getMessage("search_field"));
         model.put("search_date_from", UIlabelStore.getMessage("search_date_from"));
         model.put("search_date_to", UIlabelStore.getMessage("search_date_to"));
         
         model.put("search_sort_relevance", UIlabelStore.getMessage("search_sort_relevance"));
         model.put("search_sort_asc", UIlabelStore.getMessage("search_sort_asc"));
         model.put("search_sort_desc", UIlabelStore.getMessage("search_sort_desc"));
         model.put("search_field_all", UIlabelStore.getMessage("search_field_all"));
         model.put("search_field_title", UIlabelStore.getMessage("search_field_title"));
         model.put("search_field_content", UIlabelStore.getMessage("search_field_content"));
         model.put("search_result_empty", UIlabelStore.getMessage("search_result_empty"));
         
	     return "search";
    }
}

