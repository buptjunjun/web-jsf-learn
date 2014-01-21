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

import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;
import byr.web.util.WebConfig;

public class ResultSearcher 
{
	static private ExecutorService executorService = Executors.newFixedThreadPool(WebConfig.taskThreads);
	
	public ResultSearcher() 
	{
		// TODO Auto-generated constructor stub
	}

	public  List<ResultItem> search(SearchCriteria sc)
	{
		SearchTask st = new SearchTask(sc);
		
		List<ResultItem> ret = null;
		try 
		{
			Future<List<ResultItem>> future =  executorService.submit(st);
			ret = future.get(WebConfig.waitLimit, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		
		return null;
	}
}