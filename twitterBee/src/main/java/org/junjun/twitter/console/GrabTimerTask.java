package org.junjun.twitter.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junjun.controller.logic.PicUtil;
import org.junjun.mongo.DAOMongo;
import org.junjun.twitter.ResourceProcessor;
import org.junjun.twitter.StatusFetcher;
import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitResources;
import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

/**
 * grab resources from twitter
 * @author Andy Yang
 * @date 2013-12-15
 *
 */
public class GrabTimerTask extends TimerTask {

	// interval of task excecuted
	private int interval = 60*60; // seconds
	// list
	private List<Tag2User> lt = null;
	
	//List of Twiter users who will be fetched
	private List<TwitUser> tus = null;
	
	//DB
	final private DAOMongo  daomongo = new DAOMongo("twitter");
	
	private static final Logger logger = Logger.getLogger(TimerTask.class);
	private static final ResourceProcessor rp = new ResourceProcessor();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() 
	{
		 StatusFetcher sf = new StatusFetcher();
		// TODO Auto-generated method stub
		 List<TwitStatus> lstatus = new ArrayList<TwitStatus> ();
		 lt = TwitterUtils.init("taguser.json");
		
		logger.info("start Fetching at "+new Date().toLocaleString());	
		for(Tag2User t2u : lt)
		{
			String tag = t2u.getTag();
			Set<TwitUser> lus = t2u.getTwuser();
			// per user
			for(TwitUser twu:lus)										
			{
				try
				{			
					// fetch user's latest twits
					List<TwitStatus> lts = sf.fetch(twu);					
					lstatus.addAll(lts);
					
					logger.info("Fetched  "+ (lts == null ? 0:lts.size())+" TwitStatus from "+twu.getName()+"("+twu.getTag()+") "+new Date().toLocaleString());				
					TimeUnit.SECONDS.sleep(2);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e);
				}	
			}
		}
	
		// save all the fetched twits to database.
	
		if(lstatus!=null)
		{
			for(TwitStatus ts:lstatus)
			{
				try
				{						
					daomongo.insert(ts);
					List<TwitResources> lres = rp.process(ts);
					for(TwitResources tres:lres)
						daomongo.insert(tres);
					
					logger.info(ts);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		
	
	
		logger.info("end Fetching at "+new Date().toLocaleString());
	
		
	}
	
	
}
