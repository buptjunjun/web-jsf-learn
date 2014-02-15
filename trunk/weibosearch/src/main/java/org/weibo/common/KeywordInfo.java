package org.weibo.common;

public class KeywordInfo {

	private String contentId;
	private String keyword = null;   // keyword
	private Integer interval = null; // interval
	
	
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.contentId+", "+this.keyword+","+this.interval; 
	}
}
