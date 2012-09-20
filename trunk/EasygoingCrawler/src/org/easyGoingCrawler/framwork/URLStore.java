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
	 public boolean put(FetchedFile f);
	 
	 /**get a url from store
	  * @return the url 
	  */
	 public String  get();

	 /**
	  *  update status of a url if crawling is ok
	  * @param url
	  */
	 public void  updateSucceed(FetchedFile f);
	 
	 /**
	  *  update status of a url if crawling failed
	  * @param url
	  */
	 public void  updateFailed(FetchedFile f);
}
