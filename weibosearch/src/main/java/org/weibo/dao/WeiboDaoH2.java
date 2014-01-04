package org.weibo.dao;

import java.util.Date;

import org.weibo.common.SearchResultID;
import org.weibo.common.SearchResultWeibo;
import org.weibo.common.WeiboMysql;

/**
 * WeiboDao implemented by H2 database.
 * @author junjun
 *
 */

public class WeiboDaoH2 implements WeiboDao
{
	WeiboMysql h2 = null;
	
	public WeiboDaoH2()
	{
    	this.h2 = new WeiboMysql();
    	
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
	
	

}
