package org.junjun.twitter;

import java.util.ArrayList;
import java.util.List;

import org.junjun.twitter.bean.TwiConstant;
import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class StatusFetcher extends Thread 
{
	
	 Twitter twitter = new TwitterFactory().getInstance();
	 public List<TwitStatus> fetch(TwitUser tu) throws TwitterException
	 {
		 long id = tu.getId();
		 System.out.println("fetching :"+tu.getId()+"("+tu.getName()+")...");
		 Paging paging = new Paging(1);
		 paging.setCount(100);
		 
		 ResponseList<Status> ret = twitter.getUserTimeline(tu.getName(),paging);
		 
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
				 tws.setUserid(status.getUser().getId());
				 tws.setType((status.getMediaEntities() !=null && status.getMediaEntities().length  > 1)? status.getMediaEntities()[0].getType():TwiConstant.TypeText);
				 tws.setScore(status.getRetweetCount()+status.getFavoriteCount());
				 lt.add(tws);
			 }
		 }
		 
		 return lt;
		 
	 }
	 
	 
	 public List<TwitStatus> fetchOlderThan(String tag, String name, long maxid) throws TwitterException
	 {
		 System.out.println("fetching :"+name+"...");
		 
		 Paging paging = new Paging(1);
		 paging.setCount(100);
		 paging.setMaxId(maxid);
		 
		 ResponseList<Status> ret = twitter.getUserTimeline(name,paging);
		 
		 List<TwitStatus> lt = new ArrayList<TwitStatus>();
		 if(ret != null)
		 {
			 for(Status status:ret)
			 {
				 TwitStatus tws = new TwitStatus();
				 tws.setId(status.getId());
				 tws.setDate(status.getCreatedAt());
				 tws.setStatus(status);
				 tws.setTag(tag);
				 tws.setUserid(status.getUser().getId());
				 tws.setType((status.getMediaEntities() !=null && status.getMediaEntities().length  > 1)? status.getMediaEntities()[0].getType():TwiConstant.TypeText);
				 tws.setScore(status.getRetweetCount()+status.getFavoriteCount());
				 lt.add(tws);
			 }
		 }
		 
		 return lt;
		 
	 }
	 
	  public long getUserId(String userName)
	  {
		  try {
			User user = twitter.showUser(userName);
			return user.getId();
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return -1l;
	  }
	  public static void main(String[] args) {
		  
		  
	  }

}
