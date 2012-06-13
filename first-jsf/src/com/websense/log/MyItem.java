package com.websense.log;

import java.io.Serializable;

public class MyItem implements Serializable
{ 
	private String name ;
	private boolean selected = false;
	
	public MyItem(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}