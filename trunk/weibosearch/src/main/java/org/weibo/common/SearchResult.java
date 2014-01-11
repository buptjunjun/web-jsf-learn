package org.weibo.common;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	// sina:1,tx:2
	private int type= Constants.SINA;
	
	// key word to search in weibo
	private String keyword = null;
	
	// the weibo details which are relevant to the specified keyword
	private List<WeiboDetails> details = null;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SearchResult()
	{
		  details = new ArrayList<WeiboDetails>();
	}
	
	public SearchResult(String keyword)
	{
		 this.keyword = keyword;
		 details = new ArrayList<WeiboDetails>();
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<WeiboDetails> getDetails() {
		return details;
	}
	public void setDetails(List<WeiboDetails> details) {
		this.details = details;
	}
	@Override
	public String toString() 
	{
		String details = "";
		if(this.details != null)
			for(WeiboDetails id : this.details)
			{
				details += (id.getId()+",");
			}
		return this.keyword +" ; "+details;
	}

}
