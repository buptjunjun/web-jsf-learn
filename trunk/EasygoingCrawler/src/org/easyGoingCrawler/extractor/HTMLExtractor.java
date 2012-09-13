package org.easyGoingCrawler.extractor;

import java.util.ArrayList;
import java.util.List;
import  org.easyGoingCrawler.framwork.Extractor;

/**
 *  extract url from  HTML document
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class HTMLExtractor implements Extractor
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> extract(String docContent)
	{
		// TODO Auto-generated method stub
		List<String> urls = new ArrayList<String>();
		urls.add("www.baidu.com");
		return urls ;
	}

}
