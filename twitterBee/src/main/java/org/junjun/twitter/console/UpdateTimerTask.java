package org.junjun.twitter.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.junjun.controller.logic.PicUtil;
import org.junjun.twitter.ResourceProcessor;
import org.junjun.twitter.StatusFetcher;
import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.Update;
import org.junjun.twitter.bean.ImprovedStatus;
import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitResources;
import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

import twitter4j.TwitterException;

import com.google.gson.Gson;

/*
 * update twits which is fetched 'datespan' days agao
 * @Author Andy Yang
 * @Date 2013-12-15
 */
public class UpdateTimerTask extends TimerTask{

	// how many days ago 
	private int datespan = -1;
	
	private List<Tag2User> lt = null;
	private Update update = new Update();
	
	// top N most increased twits
	private int topN = 10;
	
	private Gson gson= new Gson();
	
	// file path which stores the user information
	public  static final String userInfoFile = "taguser.json";
	
	private static final ResourceProcessor rp = new ResourceProcessor();
	
	public UpdateTimerTask() 
	{
		
	}
	
	@Override
	public void run()
	{
		 StatusFetcher sf = new StatusFetcher();
		// list to store the most improved twits
		List<TwitResources>  is = new ArrayList<TwitResources>();
		
		// init and get the Object of Tag2User which contains the users's information 
		lt = TwitterUtils.init(userInfoFile);		
		
		// get the 'dataspan' days before today
		Date date = PicUtil.getDateBefore(new Date(),datespan );
		// unified the date 
		date.setHours(1);
		date.setMinutes(0);
		date.setSeconds(0);
		
		for(Tag2User t2u : lt)
		{
			Set<TwitUser> lus = t2u.getTwuser();
			
			// per user
			for(TwitUser tu:lus)
			{				
				// get 100 twits before date 
				List<TwitResources> tmp = TwitterUtils.getTwitResource(tu.getName(), date, 100);
				
				if(tmp == null || tmp.size() ==0)
					continue;
				
				//sort to get the top increased twitts
				Collections.sort(tmp, new Comparator<TwitResources>()
				{

					public int compare(TwitResources o1, TwitResources o2) {
						if(o1.getDate().before(o2.getDate()))
							return 1;
						else if(o1.getDate().after(o2.getDate()))
							return -1;
						
						return 0;
						
					}});					
				
				// get 100 twits before  
				List<TwitStatus> lts = null;
				try {
					String tag = tmp.get(0).getTag();
					String name = tmp.get(0).getUserName();
					long maxID = tmp.get(0).getId();
					lts = sf.fetchOlderThan(tag ,name,maxID);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<TwitResources> ltrtmp = rp.process(lts);
				
				// caculate increased score of every twitts
				List<TwitResources>  lis = update.compareOldResources(ltrtmp); 	
				
				//sort to get the top increased twitts
				Collections.sort(lis, new Comparator<TwitResources>()
				{

					public int compare(TwitResources o1, TwitResources o2) {
						if(o1.getScore() == o2.getScore())
							return 0;
						else if(o1.getScore() < o2.getScore())
							return -1;
						else 
							return 1;
						
					}});					
				
				// get the top N  increased twittes
				int size = lis.size()<this.topN ? lis.size() : this.topN;
				
				// add them to the improved twits list
				if(size > 0 )
					is.addAll(lis.subList(0, size));
			}
		}
		
		// convert the is to string
		String selectedStatus = gson.toJson(is);
		
		// get the file name according to the date and prepare to save the result to it
		String filename = TwitterUtils.fileNameFromDate(date);
		
		// save the result to file.
		try {
			FileUtils.writeStringToFile(new File(filename), selectedStatus);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}

}
