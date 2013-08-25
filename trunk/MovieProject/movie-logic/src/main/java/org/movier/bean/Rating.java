package org.movier.bean;

public class Rating 
{
	private String id = null; // the same with movie or Resource
	private int good = 0;
	private int bad = 0;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+":"+this.good+","+this.bad;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}
	
	
}
