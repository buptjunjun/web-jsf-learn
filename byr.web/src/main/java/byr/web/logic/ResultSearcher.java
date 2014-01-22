package byr.web.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import byr.analyzer.analyzer.HtmlStructuredData;
import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;
import byr.web.dao.MongoDao;
import byr.web.util.ByrUtils;
import byr.web.util.WebConfig;

public class ResultSearcher 
{
	static private ExecutorService executorService = Executors.newFixedThreadPool(WebConfig.taskThreads);
	static  MongoDao mongo = new MongoDao("byr");
	
	public ResultSearcher() 
	{
		// TODO Auto-generated constructor stub
	}

	public  List<ResultItem> search(SearchCriteria sc) throws InterruptedException, ExecutionException, TimeoutException
	{
		SearchTask st = new SearchTask(sc);
		
		List<ResultItem> ret = null;
		
		Future<List<ResultItem>> future =  executorService.submit(st);
		ret = future.get(WebConfig.waitLimit, TimeUnit.MINUTES);
		
		return null;
	}
	
	
}


class SearchTask implements Callable<List<ResultItem>> 
{
	private SearchCriteria sc = null;
	public SearchTask(SearchCriteria sc)
	{
		this.sc = sc;
	}
	
	public List<ResultItem> call() throws Exception {
		
		List<HtmlStructuredData> ret = null;
		ret = ResultSearcher.mongo.search(sc.getKeywords(), sc.getSort(), sc.getLimit(), sc.getDate1(), sc.getDate2());
			
	    List<ResultItem>  lrt = null;
		if(ret != null)
		{
			lrt = new ArrayList<ResultItem>(ret.size());
			for(HtmlStructuredData hsd: ret)
			{
				lrt.add(ByrUtils.convert(hsd));
			}
		}
		return lrt;
	}
}