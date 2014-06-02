package byr.web.logic;

import java.text.SimpleDateFormat;
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
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.RangeFacet.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import byr.analyzer.analyzer.HtmlStructuredData;
import byr.web.bean.Result;
import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;
import byr.web.dao.MongoDao;
import byr.web.util.ByrUtils;
import byr.web.util.ConfStore;
import byr.web.util.UIlabelStore;

public class ResultSearcher 
{
	static private ExecutorService executorService = null;
	private static String serverUrl = null;
	private static SolrServer solr = null;
	private static int wait_limit = 2;  //second
	private static int task_limt = 20;
	static public int content_limit = 500;
	public ResultSearcher() 
	{
		
		
		
		synchronized(ResultSearcher.class)
		{
			
			if(serverUrl == null)
				serverUrl = ConfStore.getMessage("solr_server_url");
			
			
			if(executorService == null)
			{
				String task_limt_str = ConfStore.getMessage("task_limit");
				
				try
				{
					task_limt = Integer.parseInt(task_limt_str);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					task_limt = 20;
				}
				executorService = Executors.newFixedThreadPool(task_limt);
			}
			
			if(solr == null)
				solr = new HttpSolrServer(serverUrl);
			
			String wait_limt_str = ConfStore.getMessage("wait_limt");
			String content_limit_str = ConfStore.getMessage("content_limt");
			try
			{
				wait_limit = Integer.parseInt(wait_limt_str);
				content_limit = Integer.parseInt(content_limit_str);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				wait_limit = 3;
				content_limit=500;
			}
		}
			
	}

	public  Result search(SearchCriteria sc) throws InterruptedException, ExecutionException, TimeoutException
	{
		SearchTask st = new SearchTask(sc,solr);
		
		Result ret = null;
		
		Future<Result> future =  executorService.submit(st);
		ret = future.get(wait_limit, TimeUnit.MINUTES);
		
		return ret;
	}
	
	
}


class SearchTask implements Callable<Result> 
{
	
	static public String SECTION="section";
	static public String TITLE="title";
	static public String URL="url";
	static public String CONTENT="content";
	static public String DATE="date";
	static public String ID="id";
	static public int TIMELIMIT=3; //second

	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	private SearchCriteria sc = null;
	private SolrServer solr =null;
	
	public SearchTask(SearchCriteria sc,SolrServer solr)
	{
		this.sc = sc;
		this.solr = solr;
	}
	
	public Result call() throws Exception {
		
		Result result = new Result();
		List<ResultItem> ret = null;
		String  query= "";
		SolrQuery solrQuery = new SolrQuery();
		if(sc.getSearch_position() == SearchCriteria.TITLE)  //search title only
		{
			query= TITLE+":"+sc.getKeywords();
		}
		else if(sc.getSearch_position() == SearchCriteria.CONTENT) // search content only
		{
			query= CONTENT+":"+sc.getKeywords();
		}
		else  // search both title and content
		{
			// title is double important by "^2"
			query= TITLE+":("+sc.getKeywords()+")^2 OR "+CONTENT+":"+sc.getKeywords();
		}

		solrQuery.setQuery(query);

		//search by date range
		if(sc.getDate1()!=null && sc.getDate2()!=null&& sc.getDate1().before(sc.getDate2()))
		{		
			String datefilter = "date:["+sdf.format(sc.getDate1())+ "  TO  " + sdf.format(sc.getDate2())+"]";
			solrQuery.addFilterQuery(datefilter);
			// +1HOUR indecates precision of 1 hour 
			//solrQuery.addDateRangeFacet("date", sc.getDate1(), sc.getDate2(), "+1YEAR");
		}
		
		
	
		
		// time limit
		solrQuery.setTimeAllowed(1000*TIMELIMIT);
		
		// highting  max number of snippet
		solrQuery.setHighlight(true).setHighlightSnippets(3);
		
		//field which will be hightlight
		solrQuery.setParam("hl.fl", "title,content");
		
		//paging
		solrQuery.setStart(sc.getPage()*sc.getLimit());
		solrQuery.setRows(sc.getLimit()); 
		
		
		// sort by date
		if(this.sc.getSort() == SearchCriteria.ASC )
		{
			solrQuery.setSortField("date", ORDER.asc);
		}
		else if(this.sc.getSort() == SearchCriteria.DES)
		{
			solrQuery.setSortField("date", ORDER.desc);
		}
		
		solrQuery.setFacet(true);
		solrQuery.addFacetField(SECTION);
		
		QueryResponse resp = solr.query(solrQuery); 
	
		
		// results
		SolrDocumentList hits = resp.getResults();
		long count = hits.getNumFound();
		
		
		// hilighting 
		Map<String, Map<String, List<String>>> highlighting = resp.getHighlighting();
		
		for(SolrDocument doc: hits)
		{
		
			String id = (String )doc.getFieldValue(ID);
			String url = (String) doc.getFieldValue(URL);
			Date date= (Date) doc.getFieldValue(DATE);
			String section = (String) doc.getFieldValue(SECTION);
			
			// get title with hilighting keywords
			String title = "";
			if (highlighting !=null && highlighting.containsKey(id) && highlighting.get(id).containsKey(TITLE))
			{
				List<String> tmp=highlighting.get(id).get(TITLE);
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
			else 
				title = (String) doc.getFieldValue(TITLE);
			
			// get content with hilighting keywords
			String content = "";
			if (highlighting !=null && highlighting.containsKey(id) && highlighting.get(id).containsKey(CONTENT))
			{
				List<String> tmp=highlighting.get(id).get(CONTENT);
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
			else 
				content = (String) doc.getFieldValue(CONTENT);
			
			// in case the length of content is so large.
			if(content!=null)
			{
				int limit = content.length() > ResultSearcher.content_limit?ResultSearcher.content_limit: content.length() ;
				content = content.substring(0,limit);
			}
			
			ResultItem r = new ResultItem();
			r.setId(id);
			r.setUrl(url);
			r.setTitle(title);
			r.setContent(content);
			r.setDate(date);
			r.setSection(section);
			if(ret == null)
				ret = new ArrayList<ResultItem>();
			ret.add(r);
			
		}
		result.setSuccess(true);
		result.setData(ret);
		result.setCount(count);
		if(ret==null||ret.size() == 0)
			result.setErrorMessge(UIlabelStore.getMessage("search_result_empty"));
		return result;
	}
}