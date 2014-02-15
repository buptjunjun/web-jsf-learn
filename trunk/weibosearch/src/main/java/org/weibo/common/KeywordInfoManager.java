package org.weibo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
public class KeywordInfoManager 
{

	private static List<KeywordInfo> keys = null;
	private static Map<String,KeywordInfo> mapKeys = null;
	private static KeywordInfoManager mg = null;
	private int current = 0;
	private KeywordInfoManager()
	{
		keys = WeiboUtil.loadKeyWords();
	}
	
	public static  KeywordInfoManager getInstance()
	{
	
			synchronized(KeywordInfoManager.class)
			{
				if(mg==null)
				{
					mg = new KeywordInfoManager();
					// every 10 seconds reload the keywords;
					new Thread()
					{
						public void run() 
						{
							synchronized(KeywordInfoManager.class)
							{
								keys = WeiboUtil.loadKeyWords();
								if(keys!=null)
								{
									mapKeys = new HashMap<String,KeywordInfo>();
									for(KeywordInfo key:keys)
									{
										mapKeys.put(key.getKeyword(), key);
									}
								}
							}
							
							try {
								TimeUnit.SECONDS.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();
				}
			}
			
		
			
			
			return mg;
	}
	
	/**
	 * get keyWordInfo according to keyword
	 * @param keyword
	 * @return
	 */
	public KeywordInfo getKeyWordInfo(String keyword)
	{
		if(keys == null || keys.size() == 0 || StringUtils.isEmpty(keyword))
			return null;
		
		return this.mapKeys.get(keyword);
		
	}
	public synchronized KeywordInfo  getNextKeywordInfo()
	{
		
		int size = keys.size();
		current=(current+1)%size;
		return  keys.get(this.current);
	}
}
