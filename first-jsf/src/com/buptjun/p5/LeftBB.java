package com.buptjun.p5;

import java.util.ArrayList;
import java.util.List;

public class LeftBB {
	
private List<Boolean>  list = new ArrayList<Boolean>();
public List<Boolean> getList() {
	return list;
}
public void setList(List<Boolean> list) {
	this.list = list;
}
public LeftBB()
{
	for(int i = 0; i < 3; i++)
	{
		list.add(true);
	}
}


}
