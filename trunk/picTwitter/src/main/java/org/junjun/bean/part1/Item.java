package org.junjun.bean.part1;

import java.util.Date;

public class Item
{

	// item
	private String id="";
	private String type=""; // pic,animal , cute or ....
	private String cata = ""; //vedio or pic or text;
	
	private String url="";
	private String url1 = null;
	
	
	private String desc="";
	private  Date  date = new Date();
	
	// rating
	private int good = 0;
	private int bad = 0;
	private int collect = 0; // 
	private int comment = 0; // how many comment are there 
	private int total = 0;
	
	public String getCata() {
		return cata;
	}

	public void setCata(String cata) {
		this.cata = cata;
	}
	private void caculateTotal()
	{
		this.total = this.good+this.bad/2+this.comment*2+this.collect*3;
	}
	
	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
		caculateTotal();
	}	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
		caculateTotal();
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
		caculateTotal();
	}
	public int getCollect() {
		return collect;
	}
	public void setCollect(int collect) {
		this.collect = collect;
		caculateTotal();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		if(type!=null)
			return type.hashCode();
		else 
			return 100;
		
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 == null)
			return false;
		
		if(!Item.class.isInstance(arg0))
			return false;
		Item item = (Item)arg0;
		
		if(this.id==null && item.id==null)
			return true;
		
		if(this.id!=null)
			return this.id.equals(item.getId());
		else 
			return  item.getId().equals(this.id);
			
	}
	
	@Override
	public String toString() {
		return this.id +", "+this.getType()+", "+this.getCata()+","+this.getDate()+", "+this.getUrl()+", "+this.getDesc();
	}
}
