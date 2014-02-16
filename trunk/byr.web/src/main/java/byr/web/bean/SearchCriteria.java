package byr.web.bean;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.gson.Gson;

public class SearchCriteria 
{
	public static final int DES= 0;
	public static final int ASC = 1;
	public static final int TITLE= 0;
	public static final int CONTENT = 1;
	
	private String keywords = null;
	private Date date1 = null;
	private Date date2 = null;
	private int  sort  = -1;    // DES or ASC
	private int limit = 10;
	private int page = 0;
	private int search_position = -1; //TITLE or CONTENT	
	
	public int getSearch_position() {
		return search_position;
	}
	public void setSearch_position(int search_position) {
		this.search_position = search_position;
	}
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
	@JsonSerialize(using=JsonDateSerializer.class) 
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
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
	
	static public void main(String [] args)
	{
		Gson gson = new Gson();
		SearchCriteria sc = new SearchCriteria();
		sc.setKeywords("±±” ");
		sc.setDate1(new Date());
		sc.setDate2(new Date());
		System.out.println(gson.toJson(sc));
	}
}
