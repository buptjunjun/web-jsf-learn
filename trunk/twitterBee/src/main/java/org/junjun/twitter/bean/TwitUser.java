package org.junjun.twitter.bean;

import org.apache.commons.lang3.StringUtils;

import twitter4j.User;

public class TwitUser {
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private long id = -1l;
	private String name = null;
	private String tag = null;
	private User user = null;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null || !TwitUser.class.isInstance(obj)) return false;
		TwitUser tu = (TwitUser)obj;
		if(!StringUtils.isEmpty(this.name))
			return this.name.equals(tu.getName());
		else if(!StringUtils.isEmpty(tu.name))
		{
			return  tu.name.equals(this.getName());
		}
		else return true;
		
	}
}
