package org.movier.bean;

import java.util.Date;

public class Comment 
{
	private String id = null;
	private String userID = null;   // who comment
	private String commentOn = null; //what the comment is on, movie or resource id.
	private Date time = null;
	private String content = null;	
	private Rating rating = null;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+":"+":"+this.userID+":"+this.content+":"+this.time;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCommentOn() {
		return commentOn;
	}
	public void setCommentOn(String commentOn) {
		this.commentOn = commentOn;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
}
