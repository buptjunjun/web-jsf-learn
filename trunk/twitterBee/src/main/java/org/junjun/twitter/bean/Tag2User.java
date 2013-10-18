package org.junjun.twitter.bean;

import java.util.HashSet;
import java.util.Set;


public class Tag2User 
{
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Set<TwitUser> getTwuser() {
		return twuser;
	}

	public void setTwuser(Set<TwitUser> twuser) {
		this.twuser = twuser;
	}

	private String tag = null;
	private Set<TwitUser> twuser = new HashSet<TwitUser>();
	
	public void addUser(TwitUser user)
	{
		this.twuser.add(user);
	}
	
	
}
