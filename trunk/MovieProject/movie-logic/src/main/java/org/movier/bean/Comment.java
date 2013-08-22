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
}
