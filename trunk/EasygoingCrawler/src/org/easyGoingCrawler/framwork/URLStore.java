package org.easyGoingCrawler.framwork;

/**
 * URLStore will take the responsibility for get a url or store a url to Database, 
 * I mean maybe you want to store the URL in MYSQL or simply in a text document.
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public interface URLStore 
{
	 /**
	  *  store the url to the DB
	  * @param url the url to store
	  * @return true if succeed, or false
	  */
	 public boolean put(String url);
	 
	 /**
	  * 
	  * @param condition 
	  *   <p> get a url according to  a condition. for example , 
	  *   if condition was "http://www.baidu.com/music/*" ,
	  *   it mean you want to get a url about music on www.baidu.com<p>
	  * @return the url of certain condition
	  */
	 public String  get(String condition);
}
