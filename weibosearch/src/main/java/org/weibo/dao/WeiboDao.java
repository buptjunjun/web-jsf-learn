package org.weibo.dao;

import org.weibo.common.SearchResultID;
import org.weibo.common.SearchResultWeibo;

public interface WeiboDao 
{
	public String save(SearchResultWeibo weibos);
	public String save(SearchResultID weibos);

}
