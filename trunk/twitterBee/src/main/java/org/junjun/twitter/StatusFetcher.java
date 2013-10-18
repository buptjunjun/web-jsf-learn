package org.junjun.twitter;

import java.util.ArrayList;
import java.util.List;

import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class StatusFetcher extends Thread 
{
	
	 Twitter twitter = new TwitterFactory().getInstance();
	 public List<TwitStatus> fetch(TwitUser tu) throws TwitterException
	 {
		 long id = tu.getId();
		 System.out.println("fetching :"+tu.getId()+"("+tu.getName()+")...");
		 Paging paging = new Paging(1);
		 paging.setCount(100);
		 ResponseList<Status> ret = twitter.getUserTimeline(id,paging );
		 
		 List<TwitStatus> lt = new ArrayList<TwitStatus>();
		 if(ret != null)
		 {
			 for(Status status:ret)
			 {
				 TwitStatus tws = new TwitStatus();
				 tws.setId(status.getId());
				 tws.setDate(status.getCreatedAt());
				 tws.setStatus(status);
				 tws.setTag(tu.getTag());
				 tws.setUserid(tu.getId());
				 tws.setType(status.getMediaEntities()[0].getType());
				 tws.setScore(status.getRetweetCount()+status.getFavoriteCount());
				 lt.add(tws);
			 }
		 }
		 
		 return lt;
	 }
	 
	  public static void main(String[] args) {
		  
		  
	  }

}
