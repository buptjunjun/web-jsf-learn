package org.easyGoingCrawler.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.easyGoingCrawler.util.Localizer;

/**
 * manage a bunch of Proxy
 * singleto model
 * @author andyWebsense
 *
 */
public class ProxyManager
{
	 private List<Proxy> lp  = new ArrayList<Proxy>();
	 
	 //how many times when we visit a url using the the current proxy 
	 private long fetchTimes = 0;
	 // the position of current proxy
	 private int currentProxy = 0;
	 //the limit one proxy are used each round
	 private int visitLimit = 80;
	 static private ProxyManager proxyManager = null;
	 
	 private ProxyManager()
	 {
		 String proxyFile = Localizer.getMessage("proxyFile");
		 this.lp = getProxyFromFile(proxyFile);
		 this.visitLimit = Integer.parseInt(Localizer.getMessage("visitLimit"));
	 }
	 
	 static  public ProxyManager getInstance()
	 {
		synchronized(proxyManager)
		{
			if(proxyManager == null)
				proxyManager = new ProxyManager();
		}
		return proxyManager;
	 }
	
	public Proxy getOneAvailableProxy()
	{
		synchronized (lp)
		{
			if(currentProxy > lp.size() -1)
				return null;
			
			Proxy p = lp.get(currentProxy);
			this.fetchTimes++;
			
			if(this.fetchTimes>this.visitLimit)
			{
				this.fetchTimes = 0;
				currentProxy=(currentProxy+1)%lp.size();
			}
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
			visitLimit = 80;
		this.visitLimit = visitLimit;
	}

	private List<Proxy>  getProxyFromFile(String file)
	{
		List<Proxy> lp = new ArrayList<Proxy>();
		
		try
		{
			FileReader f = new FileReader(file);
			BufferedReader bf = new BufferedReader(f);
			String line = null;
			while( (line = bf.readLine()) != null)
			{	
				String [] split = line.split(":");
				if(split.length !=2)  continue;
				String ip = split[0].trim();
				String port = split[1].trim();
				int portInt = Integer.parseInt(port);
				Proxy p = new Proxy(null,ip,portInt,null);
				lp.add(p);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return lp;
	}
}
