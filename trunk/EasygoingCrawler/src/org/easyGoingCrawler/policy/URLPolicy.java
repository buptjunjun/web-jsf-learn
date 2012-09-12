package org.easyGoingCrawler.policy;

import org.easyGoingCrawler.framwork.Policy;

/*
* URLPolicy is an implemetation of Policy. It will make decision if one url will be accepted .
* for example , when we choose one url from "url store"(maybe in a database , it all depends), 
* and want to fetch the html of it.  URLPolicy will decide whether to abandon it 
* or accept it . Maybe the url is "http://www.abc.com/music.swf", we have no interest in it ,
* so we can abandon it  
* 
*  @author Andy  weibobee@gmail.com 2012-9-12
*/
public class URLPolicy implements Policy 
{
	/**
	 * decide if the url will be accepted
	 * @param url to prcess
	 * @return true if the url is accepted or false
	 */
	@Override
	public boolean process(String url) {
		// TODO Auto-generated method stub
		return false;
	}

}
