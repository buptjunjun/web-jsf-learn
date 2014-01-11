package org.weibo.dao;

import java.sql.SQLException;
import java.util.Date;

import org.weibo.common.SearchResult;
import org.weibo.common.SearchResultWeibo;

public interface WeiboDao 
{
	public String save(SearchResultWeibo weibos);
	public String save(SearchResult weibos);
	
	public SearchResult search(String keyword,int flag,int type);
	public SearchResultWeibo search(String keyword,Date from,Date to,int type);
}
