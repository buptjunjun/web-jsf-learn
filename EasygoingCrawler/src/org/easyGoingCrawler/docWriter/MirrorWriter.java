package org.easyGoingCrawler.docWriter;

import org.easyGoingCrawler.framwork.DocWriter;


/**
 *  MirrorWriter will do the job of storing the documents you have fetched to Hard disk ,
 *  arranged in a directory hierarchy based on the URI paths.
 *  for example, "www.baidu.com/music/gingle_bell.html" will be stored in the file "path/www.baidu.com/music/gingle_bell.html"
 *
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */

public class MirrorWriter implements DocWriter
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean write(Object doc)
	{
		// TODO Auto-generated method stub
		System.out.println("write "+doc + "to a directory");
		return true;
	}

}
