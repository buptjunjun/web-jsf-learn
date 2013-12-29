package org.weibo.common;

/**
 * the bean  contains infomation  that the analyzer need to analyze the html.
 * @author junjun
 *
 */
public class AnalyzeBean {

	// sina:1,tx:2
	private int type= Constants.SINA;
	
	private String keyword = null;
	
	// content of the html
	private String content = null;

	//http status from the server after fetch the url.
	private int httpstatus = -1;
	
	// the encode of the html
	private String encode = "utf-8";

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getHttpstatus() {
		return httpstatus;
	}

	public void setHttpstatus(int httpstatus) {
		this.httpstatus = httpstatus;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() 
	{
		return "keywords:"+this.keyword+" ; httpstatus: "+this.httpstatus+" ; "+this.content;
	}
}
