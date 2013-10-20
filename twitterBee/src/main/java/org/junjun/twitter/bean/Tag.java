package org.junjun.twitter.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class Tag 
{
	public String id= null;
	private String tag = null;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
	@Override
	public boolean equals(Object obj) {
		
		if(!Tag.class.isInstance(obj))
			return false;		
		
		Tag tu=(Tag)obj;
		if(!StringUtils.isEmpty(this.tag))
			return this.tag.equals(tu.getTag());
		else if(!StringUtils.isEmpty(tu.getTag()))
			return tu.getTag().equals(this.tag);
	
		return this.tag== tu.getTag();
	}
}
