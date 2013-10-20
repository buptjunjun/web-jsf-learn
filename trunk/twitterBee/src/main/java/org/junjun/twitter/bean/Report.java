package org.junjun.twitter.bean;

import java.util.Date;

public class Report 
{
	private String description  = null;
	private boolean sucess = true;
	private Date date = new Date();
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
