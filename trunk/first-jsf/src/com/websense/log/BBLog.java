package com.websense.log;

import java.util.*;
import com.websense.log.*;
import javax.faces.component.UIData;

public class BBLog
{
	private List<MyItem> modules  = new ArrayList<MyItem>();

	private UIData dataTable;
	
	public BBLog()
	{
		
		String [] modules = "wcg, wsc, na".split(",");
		for(int i = 0; i < modules.length; i++)
		{
			this.modules.add(new MyItem(modules[i]));
		}
	}
	

	public List<MyItem> getModules() {
		return modules;
	}

	public void setModules(List<MyItem> modules) {
		this.modules = modules;
	}


	
	public UIData getDataTable() {
		return dataTable;
	}

	public void setDataTable(UIData dataTable) {
		this.dataTable = dataTable;
	}


	public String submit()
	{
		for(MyItem item : this.modules)
		{
			System.out.println(item.getName() +" , "+item.isSelected());
		}
		
		return null;
	}
}
