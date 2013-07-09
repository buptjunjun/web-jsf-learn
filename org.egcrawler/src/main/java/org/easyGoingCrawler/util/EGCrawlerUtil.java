package org.easyGoingCrawler.util;

import org.jsoup.helper.StringUtil;

public class EGCrawlerUtil
{
	/**
	 * if c is a chinese word
	 * @param c
	 * @return
	 */
	static public boolean isChinese(char c)
	{
		
		return false;
	}


	
	/**
	 * http://movie.douban.com/subject/5954626 to 5954626
	 * @param url
	 * @return
	 */
	static public String getMovieIDFromUrl(String url)
	{
		String ret = null;
		if(StringUtil.isBlank(url))
			return ret;
		
		ret = url.replace("http://movie.douban.com/subject/", "");
		
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
