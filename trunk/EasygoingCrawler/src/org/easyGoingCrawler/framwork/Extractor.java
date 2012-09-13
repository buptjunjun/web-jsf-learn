package org.easyGoingCrawler.framwork;

import java.util.List;

/**
 * Extractor will extract url from current document
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
interface  Extractor
{
	/**
	 * 
	 * @param docContent for example a html
	 * @return list of the url in this document
	 */
	 List<String> extract(String docContent);
}
