package byr.web.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import byr.analyzer.analyzer.HtmlStructuredData;
import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;
import byr.web.dao.MongoDao;
import byr.web.util.ByrUtils;
import byr.web.util.WebConfig;

public class ResultSearcher 
{
	static private ExecutorService executorService = Executors.newFixedThreadPool(WebConfig.taskThreads);
	private static String serverUrl ="http://localhost:8983/solr/collection1";
	private static SolrServer solr = new HttpSolrServer(serverUrl);
	
	public ResultSearcher() 
	{
		// TODO Auto-generated constructor stub
	}

	public  List<ResultItem> search(SearchCriteria sc) throws InterruptedException, ExecutionException, TimeoutException
	{
		SearchTask st = new SearchTask(sc,solr);
		
		List<ResultItem> ret = null;
		
		Future<List<ResultItem>> future =  executorService.submit(st);
		ret = future.get(WebConfig.waitLimit, TimeUnit.MINUTES);
		
		return null;
	}
	
	
}


class SearchTask implements Callable<List<ResultItem>> 
{
	
	static public String TITLE="title";
	static public String URL="url";
	static public String CONTENT="content";
	static public String DATE="date";
	static public String ID="id";
	static public int TIMELIMIT=3; //second
	
	private SearchCriteria sc = null;
	private SolrServer solr =null;
	
	public SearchTask(SearchCriteria sc,SolrServer solr)
	{
		this.sc = sc;
		this.solr = solr;
	}
	
	public List<ResultItem> call() throws Exception {
		
		List<ResultItem> ret = null;
		String  query= "";
		
		if(sc.getSearch_position()!=SearchCriteria.TITLE)  //search title only
		{
			query= TITLE+":"+sc.getKeywords();
		}
		else if(sc.getSearch_position()!=SearchCriteria.CONTENT) // search content only
		{
			query= CONTENT+":"+sc.getKeywords();
		}
		else  // search both title and content
		{
			// title is double important by "^2"
			query= TITLE+":("+sc.getKeywords()+")^2 "+CONTENT+":"+sc.getKeywords();
		}

		
		SolrQuery solrQuery = new SolrQuery(query);
		
		// time limit
		solrQuery.setTimeAllowed(1000*TIMELIMIT);
		
		// highting  max number of snippet
		solrQuery.setHighlight(true).setHighlightSnippets(3);
		
		//field which will be hightlight
		solrQuery.setParam("hl.fl", "title,content");
		
		//paging
		solrQuery.setStart(sc.getPage()*sc.getLimit());
		solrQuery.setRows(sc.getLimit()); 
		
		//search by date range
		if(sc.getDate1()!=null && sc.getDate2()!=null&& sc.getDate1().before(sc.getDate2()))
		{		
			// +1HOUR indecates precision of 1 hour 
			solrQuery.addDateRangeFacet("date", sc.getDate1(), sc.getDate2(), "+1HOUR");
		}
		
		// sort by date
		if(this.sc.getSort() == SearchCriteria.ASC )
		{
			solrQuery.setSortField("date", ORDER.asc);
		}
		else if(this.sc.getSort() == SearchCriteria.DES)
		{
			solrQuery.setSortField("date", ORDER.desc);
		}
		
		QueryResponse resp = solr.query(solrQuery); 
		
		
		// results
		SolrDocumentList hits = resp.getResults();
		// hilighting 
		Map<String, Map<String, List<String>>> highlighting = resp.getHighlighting();
		
		for(SolrDocument doc: hits)
		{
		
			String id = (String )doc.getFieldValue(ID);
			String url = (String) doc.getFieldValue(URL);
			Date date= (Date) doc.getFieldValue(DATE);
			
			// get title with hilighting keywords
			String title = "";
			if (highlighting !=null && highlighting.containsKey(ID) && highlighting.get(ID).containsKey(TITLE))
			{
				List<String> tmp=highlighting.get(ID).get(TITLE);
				if(tmp!=null && tmp.size() > 0)
				{
					for(String str:tmp)
					{
						title += str+" ";
					}
					
				}
				else 
					title = (String) doc.getFieldValue(TITLE);
			}
			
			
			// get content with hilighting keywords
			String content = "";
			if (highlighting !=null && highlighting.containsKey(ID) && highlighting.get(ID).containsKey(CONTENT))
			{
				List<String> tmp=highlighting.get(ID).get(CONTENT);
				if(tmp!=null && tmp.size() > 0)
				{
					for(String str:tmp)
					{
						content += str+" ";
					}
					
				}
				else 
					content = (String) doc.getFieldValue(CONTENT);
			}
			
			ResultItem r = new ResultItem();
			r.setId(id);
			r.setUrl(url);
			r.setTitle(title);
			r.setContent(content);
			r.setDate(date);
			ret.add(r);
			
		}
		return ret;
	}
}