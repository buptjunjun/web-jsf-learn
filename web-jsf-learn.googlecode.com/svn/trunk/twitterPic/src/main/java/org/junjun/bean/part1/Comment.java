package org.junjun.bean.part1;

import java.util.Date;

public class Comment 
{
	private String id = null;
	private String comment = null;
	private Date   date = new Date();	
	private String commentTo = null;   // id of what is commented;
	private String commentFrom = null; // id of who is commenting
}
