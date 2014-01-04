package org.weibo.common;

import java.util.List;
import java.util.concurrent.TimeUnit;
public class KeywordInfoManager 
{

	private static List<KeywordInfo> keys = null;
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
					mg = new KeywordInfoManager();
			}
			
		
			// every 10 seconds reload the keywords;
			new Thread()
			{
				public void run() 
				{
					synchronized(KeywordInfoManager.class)
					{
						keys = WeiboUtil.loadKeyWords();
					}
					
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
			
			return mg;
	}
	
	public synchronized KeywordInfo  getNextKeywordInfo()
	{
		
		int size = keys.size();
		current=(current+1)%size;
		return  keys.get(this.current);
	}
}
