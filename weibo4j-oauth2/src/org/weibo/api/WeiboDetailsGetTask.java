package org.weibo.api;

import java.util.concurrent.Callable;

import org.weibo.common.WeiboDetails;

public interface WeiboDetailsGetTask extends Runnable
{
	public WeiboDetails process(String keyword,int type,String id);
}
