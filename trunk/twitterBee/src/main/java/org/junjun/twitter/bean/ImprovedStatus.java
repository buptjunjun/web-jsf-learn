package org.junjun.twitter.bean;

public class ImprovedStatus implements Comparable
{
	private String id = "";
	private long timespan = 0;  //seconds
	private float improvedRate = 0.0f;
	private int retwitCountOld = 0;
	private int favouriteCountOld = 0;	
	private int retwitCountNow = 0;
	private int favouriteCountNow = 0;
	private TwitStatus status ;

	public TwitStatus getStatus() {
		return status;
	}

	public void setStatus(TwitStatus status) {
		this.status = status;
	}

	public void caculateRate()
	{
		float rate = (this.getFavouriteCountNow()+this.getRetwitCountNow()-this.getFavouriteCountOld()+this.getRetwitCountOld())/this.getTimespan();
		this.setImprovedRate(rate);
	}
	
	public long getTimespan() {
		return timespan;
	}
	public void setTimespan(long timespan) {
		this.timespan = timespan;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getImprovedRate() {
		return improvedRate;
	}
	public void setImprovedRate(float improvedRate) {
		this.improvedRate = improvedRate;
	}
	public int getRetwitCountOld() {
		return retwitCountOld;
	}
	public void setRetwitCountOld(int retwitCountOld) {
		this.retwitCountOld = retwitCountOld;
	}
	public int getFavouriteCountOld() {
		return favouriteCountOld;
	}
	public void setFavouriteCountOld(int favouriteCountOld) {
		this.favouriteCountOld = favouriteCountOld;
	}
	public int getRetwitCountNow() {
		return retwitCountNow;
	}
	public void setRetwitCountNow(int retwitCountNow) {
		this.retwitCountNow = retwitCountNow;
	}
	public int getFavouriteCountNow() {
		return favouriteCountNow;
	}
	public void setFavouriteCountNow(int favouriteCountNow) {
		this.favouriteCountNow = favouriteCountNow;
	}
	public int compareTo(Object o) {
		if(!this.getClass().isInstance(o))
			return -1;
		ImprovedStatus is = (ImprovedStatus)o;
		return (int) ( this.getImprovedRate()*100000 - is.getImprovedRate()*100000 );
	}
	
}
