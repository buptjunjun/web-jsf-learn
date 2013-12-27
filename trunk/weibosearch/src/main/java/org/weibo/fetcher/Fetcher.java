package org.weibo.fetcher;

import org.weibo.common.AnalyzeBean;
import org.weibo.common.FetchBean;

public interface Fetcher 
{
	public AnalyzeBean fetch(FetchBean target);
}
