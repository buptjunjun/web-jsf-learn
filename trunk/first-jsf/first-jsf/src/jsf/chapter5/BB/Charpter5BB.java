package jsf.chapter5.BB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.*;
import javax.faces.model.SelectItem;

public class Charpter5BB 
{
	

	
	private List<String> favoriteColors;
	private int times = 3;
	private List<SelectItem> colors;
	private List<Object> seletedItems;
	private List<User> userList;
	private UIData dataTable;
	private String view = "firstName";
	private List<SelectItem> showItems;
	private List<SelectItem> userSelectItems;
	private List<String> selectedColorItems;
	
	public List<String> getSelectedColorItems() {
		return selectedColorItems;
	}


	public void setSelectedColorItems(List<String> selectedColorItems) {
		if(selectedColorItems != null)
		{
			for(String str : selectedColorItems)
			{
				System.out.println(str);
			}
		}
		
		this.selectedColorItems = selectedColorItems;
	}


	public List<SelectItem> getUserSelectItems() {
		if(this.userSelectItems == null)
		{
			this.userSelectItems = new ArrayList<SelectItem>();
			this.userSelectItems.add(new SelectItem(new User("a","aa",1,true),"aa"));
			this.userSelectItems.add(new SelectItem(new User("b","bb",1,true),"bb"));
			this.userSelectItems.add(new SelectItem(new User("c","cc",1,true),"cc"));
		}
		return userSelectItems;
	}


	public void setUserSelectItems(List<SelectItem> userSelectItems) {
		this.userSelectItems = userSelectItems;
	}


	public List<SelectItem> getShowItems() {
		if(this.showItems == null)
		{
			this.showItems = new ArrayList<SelectItem>();
			this.showItems.add(new SelectItem("a","aa"));
			this.showItems.add(new SelectItem("b","bb"));
			this.showItems.add(new SelectItem("c","cc"));
		}
		return showItems;
	}


	public void setShowItems(List<SelectItem> showItems) {
		this.showItems = showItems;
	}


	public String getView() {
		return view;
	}


	public void setView(String view) {
		this.view = view;
	}


	public UIData getDataTable() {
		return dataTable;
	}


	public void setDataTable(UIData dataTable) {
		this.dataTable = dataTable;
	}
	
	public List<User> getUserList() {
		if(this.userList == null)
		{
			this.userList = new ArrayList<User>();
			this.userList.add(new User("a","aa",1,true));
			this.userList.add(new User("b","bb",2,true));
			this.userList.add(new User("c","cc",3,false));
			this.userList.add(new User("d","dd",4,true));
		}
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Object> getSeletedItems() {
		return seletedItems;
	}

	public void setSeletedItems(List<Object> seletedItems) {
		
		System.out.println("in setSelectedItems");
		
		if(seletedItems !=null) 
		{
			for(Object str: seletedItems)
			{
				System.out.println(str.toString());
			}
		}
		this.seletedItems = seletedItems;
	}

	private boolean registered = true;
	
	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public void testValueChange(ValueChangeEvent event)
	{
		System.out.println(" value changed");
		System.out.println(event.getComponent().toString());
		
	}
	
	public void deleteUser(ActionEvent event)
	{
		//System.out.println("childcount:"+event.getComponent().getChildCount());
		System.out.println("value:"+event.getComponent().getAttributes().get("value"));
		this.userList.remove(this.getDataTable().getRowIndex());
		
	}
	
	public List<SelectItem> getColors() 
	{
		if(colors == null)
		{
			colors = new ArrayList<SelectItem>();
			for(String c: this.favoriteColors)
				colors.add(new SelectItem(c,c+"--"));
		}
		return colors;
	}

	public void setColors(List<SelectItem> colors) {
		this.colors = colors;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String nextPage()
	{
		return "next";
	}
	
	public String prePage()
	{
		return "pre";
	}
	
	public List<String> getFavoriteColors() {
		return favoriteColors;
	}

	public void setFavoriteColors(List<String> favoriteColors) {
		this.favoriteColors = favoriteColors;
	}

}
