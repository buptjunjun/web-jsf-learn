package org.junjun.bean.part1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Collect 
{
	
	private String id = null;
	private String collect = null;
	private Date   date = new Date();	
	private String collectTo = null;   // id of what is collected;
	private String collectFrom = null; // id of who is collecting
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCollectTo() {
		return collectTo;
	}
	public void setCollectTo(String collectTo) {
		this.collectTo = collectTo;
	}
	public String getCollectFrom() {
		return collectFrom;
	}
	public void setCollectFrom(String collectFrom) {
		this.collectFrom = collectFrom;
	}
	
	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	public String getFormatDate()
	{
		if(this.date!=null)
		{
			return sdf.format(date);
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		if(this.id!=null)
			return id.hashCode();
		return 888;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj== null || !Collect.class.isInstance(obj))
			return false;
		
		Collect c = (Collect)obj;
		if(c.id !=null )
			return c.getId().equals(this.id);
		else if(this.id !=null)
			return this.getId().equals(c.getId());
		else 
			return false;
	}
}

