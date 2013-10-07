package org.junjun.bean.part1;

import java.util.Date;

public class Comment 
{
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCommentTo() {
		return commentTo;
	}
	public void setCommentTo(String commentTo) {
		this.commentTo = commentTo;
	}
	public String getCommentFrom() {
		return commentFrom;
	}
	public void setCommentFrom(String commentFrom) {
		this.commentFrom = commentFrom;
	}
	private String id = null;
	private String comment = null;
	private Date   date = new Date();	
	private String commentTo = null;   // id of what is commented;
	private String commentFrom = null; // id of who is commenting
}
