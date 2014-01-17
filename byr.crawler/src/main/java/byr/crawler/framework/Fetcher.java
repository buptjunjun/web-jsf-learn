package byr.crawler.framework;

/**
 * Fetcher is a interface which will do the fetch job. HttpFetcher,or DNSfetcher will be its implementation
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */
public abstract class Fetcher
{
	/**
	 * do the fech job
	 * @return the result
	 */
	public void  fetch(CrawlURI curl){};
}
