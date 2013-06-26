package org.easyGoingCrawler.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.util.Localizer;

/**
 * manage a bunch of Proxy
 * singleto model
 * @author andyWebsense
 *
 */
public class ProxyManager extends TimerTask
{
	 private List<Proxy> lp  = new ArrayList<Proxy>();
	 
	 public List<Proxy> getLp()
	{
		return lp;
	}

	//how many times when we visit a url using the the current proxy 
	 private long fetchTimes = 0;
	 // the position of current proxy
	 private int currentProxy = 0;
	 //the limit one proxy are used each round
	 private int visitLimit = 60;
	 static private ProxyManager proxyManager = null;
	 private Timer timer = null;
	 private int proxyScheduleInterval = Integer.parseInt(Localizer.getMessage("proxyScheduleInterval"));
	 // the max response time from the test site.
	 private int maxResponseTime = 10000;
	

	private ProxyManager()
	 {
		 String proxyFile = Localizer.getMessage("proxyFile");
		 this.lp = getProxyFromFile(proxyFile);
		 this.visitLimit = Integer.parseInt(Localizer.getMessage("visitLimit"));
		 timer =  new Timer();
		 timer.schedule(this, 0, proxyScheduleInterval*1000);
		 maxResponseTime = Integer.parseInt(Localizer.getMessage("maxResponseTime"));
	 }
	 
	 /**
	  * singleton model
	  * @return
	  */
	 static  public ProxyManager getInstance()
	 {
		synchronized(ProxyManager.class)
		{
			if(proxyManager == null)
			{
				proxyManager = new ProxyManager();				
			}
		}
		return proxyManager;
	 }
	
	public Proxy getOneAvailableProxy()
	{
		synchronized (lp)
		{
			if(currentProxy > lp.size() -1)
				return null;
			int i = 0;
			currentProxy=(currentProxy+1)%lp.size();
			while( (lp.get(currentProxy).getConnectTime()<0 ||lp.get(currentProxy).getConnectTime() > this.maxResponseTime)  && i++ < lp.size())
			{
				currentProxy=(currentProxy+1)%lp.size();
			}
			
			
			Proxy p = lp.get(currentProxy);
			return p;
		}
	}
	
	
	public int getVisitLimit()
	{
		return visitLimit;
	}

	public void setVisitLimit(int visitLimit)
	{
		if(visitLimit <=0)
			visitLimit = 60;
		this.visitLimit = visitLimit;
	}

	private List<Proxy>  getProxyFromFile(String file)
	{
		List<Proxy> lp = new ArrayList<Proxy>();
		BufferedReader bf= null;
		try
		{
			FileReader f = new FileReader(file);
			bf = new BufferedReader(f);
			String line = null;
			while( (line = bf.readLine()) != null)
			{	
				String [] split = line.split(":");
				if(split.length !=2)  continue;
				String ip = split[0].trim();
				String port = split[1].trim();
				int portInt = Integer.parseInt(port);
				Proxy p = new Proxy(null,ip,portInt,"http://movie.douban.com/");
				lp.add(p);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(bf!=null)
			{
				try
				{
					bf.close();
					bf = null;
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lp;
	}
	
	/**/
	private void testUsable()
	{
		ExecutorService executorService = Executors.newFixedThreadPool(this.lp.size());
		 HashMap<Proxy,Future<Integer>> resultFuture =  new HashMap<Proxy,Future<Integer>>();
		boolean flag = false;
		for(Proxy p: this.lp)
		{
			Future<Integer> f = executorService.submit(p);
			resultFuture.put(p, f);
		}
		

		try
		{
			boolean success =  executorService.awaitTermination(30, TimeUnit.SECONDS);
			if(!success)
				executorService.shutdown();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			executorService.shutdown();
		}
		
		for(Entry<Proxy, Future<Integer>> entry: resultFuture.entrySet())
		{
			Future<Integer> f = entry.getValue();
			int time = Proxy.ERROR;
			try
			{
				time = f.get();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Proxy p = entry.getKey();
			p.setConnectTime(time);
		}	
		executorService.shutdown();
	
		/*Collections.sort(lp);*/
		for(Proxy p:lp)
		{
			System.out.println(p);
		}
	}
	
	public static void main(String []str)
	{
		ProxyManager pm = ProxyManager.getInstance();
		pm.testUsable();

	}
	 
	 @Override
	public void run()
	{
		 synchronized(this.lp)
		{	
			 this.testUsable();
		}
	
	}
	 
	 public int getMaxResponseTime()
		{
			return maxResponseTime;
		}

		public void setMaxResponseTime(int maxResponseTime)
		{
			this.maxResponseTime = maxResponseTime;
		}

}

