package org.junjun.twitter.bean;

import java.util.Date;

public class TwitResources
{
	private long id ;
	private long statusID = -1;

	private String tag;
	private String userName;
	private String type;
	private String txt;
	private String url = null; //i
	private String path = null;
	private Date date = null;
	private Date processDate = new Date();
	private int score = 0;
	private int magicNum = TwiConstant.Undownloaded;
	
	public int getMagicNum() {
		return magicNum;
	}
	public void setMagicNum(int magicNum) {
		this.magicNum = magicNum;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
}
