package org.easyGoingCrawler.docWriter;

public class Blog 
{
	String id = null;
	String host = null;
	String url = null;           // url
	byte[] content = null;       // content of the bolg 
	String encode = "utf8";      // encode of the bolg content
	String blogerURL = "";       // the author's url
	int comment = 0;			 // how many comments the blog has been earnt
	int visit = 0;				 // how many times the blog has been visited
	String [] tags = null; 		 // tags of pictures
	int pictures = 0;			 // how many pictues in the blog's content
}
