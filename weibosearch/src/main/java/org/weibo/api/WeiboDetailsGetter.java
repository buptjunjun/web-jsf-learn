package org.weibo.api;

import java.util.concurrent.Callable;

import org.weibo.common.WeiboDetails;

public interface WeiboDetailsGetter 
{
	public WeiboDetails process(int type,String id);
}
