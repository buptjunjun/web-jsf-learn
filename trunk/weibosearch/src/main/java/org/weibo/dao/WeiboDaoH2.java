package org.weibo.dao;

import java.util.Date;

import org.weibo.common.SearchResultID;
import org.weibo.common.SearchResultWeibo;
import org.weibo.common.Weiboh2;

/**
 * WeiboDao implemented by H2 database.
 * @author junjun
 *
 */

public class WeiboDaoH2 implements WeiboDao
{
	Weiboh2 h2 = null;
	
	public WeiboDaoH2()
	{
    	this.h2 = new Weiboh2();
    	
	}
	
	
	public String save(SearchResultWeibo weibos) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * save weiboids to database
	 */
	public String save(SearchResultID weiboids) {
		
		this.h2.insert(weiboids);
		return null;
	}
	
	
	public SearchResultWeibo search(String keyword, Date from, Date to, int type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * query data base and get the SearchResultID by type(sina or TX), flag and keyword
	 */
	public SearchResultID search(String keyword, int flag, int type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.h2.stopServer();
	}

}
