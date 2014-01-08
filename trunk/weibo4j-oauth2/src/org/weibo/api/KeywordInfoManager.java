package org.weibo.api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.weibo.common.KeywordInfo;

import com.google.gson.Gson;
public class KeywordInfoManager 
{
	private static List<KeywordInfo> keys = null;
	private static KeywordInfoManager mg = null;
	private int current = 0;
	
	private	 static Logger logger = Logger.getLogger(KeywordInfoManager.class);
	private KeywordInfoManager()
	{
		keys = loadKeyWords();
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
								keys = loadKeyWords();
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
	
	public synchronized KeywordInfo  getNextKeywordInfo()
	{
		
		int size = keys.size();
		current=(current+1)%size;
		return  keys.get(this.current);
	}
	
	static private String keywords_file  = "src/main/resources/keywords.json";
	static private List<KeywordInfo> loadKeyWords()
	{
		Gson gson = new Gson();
		try {
			Reader reader = new BufferedReader((new InputStreamReader(new FileInputStream(keywords_file),"UTF-8")));
			List<KeywordInfo> keywords = (List<KeywordInfo>)gson.fromJson(reader,new com.google.gson.reflect.TypeToken<List<KeywordInfo>>() {}.getType());
			logger.info("load keywords:"+keywords);
			return keywords;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("load keywords error :"+e.toString());
		} 
		return null;
	}
}
