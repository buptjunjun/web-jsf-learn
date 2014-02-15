package byr.web.bean;

import java.util.Date;

public class SearchCriteria 
{
	public static final int DES= 0;
	public static final int ASC = 1;

	private String keywords = null;
	private Date date1 = null;
	private Date date2 = null;
	private int  sort  = DES;
	private int limit = 10;
	private int page = 0;

	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) 
	{
		this.keywords = keywords;
	}
	
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}

}
