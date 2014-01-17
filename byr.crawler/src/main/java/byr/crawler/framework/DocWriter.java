package byr.crawler.framework;


/**
 *  DocWriter will do the job of storing the documents you have fetched to some place, it depends on the implementation
 *  of DocWriter.
 * 
 *  For example you  can store the docuemnts to Mysql or just to the hard disk. 
 * 
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */
public abstract class  DocWriter 
{
	/**
	 * 
	 * @param doc the document you want to store
	 * @return true if success or false
	 */
	public void write(CrawlURI curl) {};
}
