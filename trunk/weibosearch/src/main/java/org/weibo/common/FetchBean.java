package org.weibo.common;

/**
 * the bean  contains infomation  that the fetcher need to feth a url.
 * @author junjun
 *
 */
public class FetchBean 
{
	// sina:1,tx:2
	private int type= Constants.SINA;
	private String keyword = null;
	private String url = null;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	private void generateUrlFromKeyword()
	{
		
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
