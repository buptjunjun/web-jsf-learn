package byr.crawler.framework;

import java.util.List;

/**
 * Extractor will extract url from current document
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public abstract class  Extractor
{
	/**
	 * @param url
	 * @param docContent for example a html
	 * @return list of the url in this document
	 */
	 public void extract(CrawlURI curl){};
}
