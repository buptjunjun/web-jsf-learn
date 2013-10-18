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
import twitter4j.User;

public class UserFetcher extends Thread 
{
	
	 Twitter twitter = new TwitterFactory().getInstance();
	 public List<TwitUser> fetch(List<TwitUser> tus) throws TwitterException
	 {
		 String [] usernames = new String [tus.size()];
		 int i = 0;
		 for(TwitUser u: tus)
		 {
			 usernames[i] = u.getName();
			 i++;
		 }
		 System.out.println("fetching user info of :"+usernames+")...");

		 ResponseList<User> users =  twitter.lookupUsers(usernames);
		 if(users != null)
		 {
			 for(User u:users)
			 {
				 for(TwitUser uts : tus)
				 {
					 if(uts.getName().equals(u.getScreenName()))
					 {
						 uts.setId(u.getId());
						 uts.setUser(u);
					 }
				 }
			 }
		 }
		 
		 return tus;
	 }

	 
	 
}
