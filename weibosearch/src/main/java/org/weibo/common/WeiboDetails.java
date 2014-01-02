package org.weibo.common;

import java.util.Date;

/**
 * details of a weibo
 * @author andyWebsense
 *
 */
public class WeiboDetails 
{
	// details of the weibo 
	private String id = null;
	private Date   createAt = null;     // date when create this weibo
	private String content = null;      // content of this weibo
	private int commentCount = 0;       // the amount of comments of this weibo
	private int rettwitCount = 0;        // the times of the rettwit
	
	// details of the user who create this weibo
	private String userID = null;         // id of user  who creat this weibo
	private String userName = null;       // name of user create this weibo
	private String userDescription = null; // description of user  who creat this weibo
	private String userLocation = null;   // loacation of user  who creat this weibo

	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserDescription() {
		return userDescription;
	}


	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}


	public String getUserLocation() {
		return userLocation;
	}


	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getCommentCount() {
		return commentCount;
	}


	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}


	public int getRettwitCount() {
		return rettwitCount;
	}


	public void setRettwitCount(int rettwitCount) {
		this.rettwitCount = rettwitCount;
	}


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
