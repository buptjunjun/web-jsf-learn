package org.easyGoingCrawler.framwork;

/**
 * Fetcher is a interface which will do the fetch job. HttpFetcher,or DNSfetcher will be its implementation
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */
public interface Fetcher
{
	/**
	 * do the fech job
	 * @return the result
	 */
	String fetch(String url);
}