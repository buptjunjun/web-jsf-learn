package org.junjun.twitter.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class Tag2User 
{
	public String id= null;
	
	private String tag = null;
	private Set<TwitUser> twuser = new HashSet<TwitUser>();
	
	public void addUser(TwitUser user)
	{
		this.twuser.add(user);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@Override
	public boolean equals(Object obj) {
		
		if(!Tag2User.class.isInstance(obj))
			return false;		
		
		Tag2User tu=(Tag2User)obj;
		if(!StringUtils.isEmpty(this.tag))
			return this.tag.equals(tu.getTag());
		else if(!StringUtils.isEmpty(tu.getTag()))
			return tu.getTag().equals(this.tag);
	
		return this.tag== tu.getTag();
	}
}
