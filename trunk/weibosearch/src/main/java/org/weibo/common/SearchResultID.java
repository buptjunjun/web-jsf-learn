package org.weibo.common;

import java.util.ArrayList;
import java.util.List;

public class SearchResultID {

	// sina:1,tx:2
	private int type= Constants.SINA;
	
	// key word to search in weibo
	private String keyword = null;
	
	// the weibo ids which are relevant to the specified keyword
	private List<String> ids = null;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SearchResultID()
	{
		  ids = new ArrayList<String>();
	}
	
	public SearchResultID(String keyword)
	{
		 this.keyword = keyword;
		 ids = new ArrayList<String>();
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	@Override
	public String toString() 
	{
		String ids = "";
		if(this.ids != null)
			for(String id : this.ids)
			{
				ids += (id+",");
			}
		return this.keyword +" ; "+ids;
	}

}
