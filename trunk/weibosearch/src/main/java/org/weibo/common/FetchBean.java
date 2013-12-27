package org.weibo.common;

/**
 * the bean  contains infomation  that the fetcher need to feth a url.
 * @author junjun
 *
 */
public class FetchBean {

	private String keyword = null;
	private String url = null;
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
