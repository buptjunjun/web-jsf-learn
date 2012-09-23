package org.easyGoingCrawler.hibernate;

import java.util.Date;

public class User 
{
	private String uid ;   
	int age ;
	private Date lastCrawlTime;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getLastCrawlTime() {
		return lastCrawlTime;
	}
	public void setLastCrawlTime(Date lastCrawlTime) {
		this.lastCrawlTime = lastCrawlTime;
	}
}
