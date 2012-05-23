package jsf.chapter5.BB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.*;
import javax.faces.model.SelectItem;

public class Charpter5BB 
{
	public class User
	{
		private String firstName;
		private String lastName;
		private int currency;
		private boolean registered;

		public User(String firstName, String lastName, int currency, boolean registered)
		{
			this.setfirstName(firstName);
			this.setLastName(lastName);
			this.setCurrency(currency);
			this.setRegistered(registered);
		}
		

		public boolean isRegistered() {
			return registered;
		}


		public void setRegistered(boolean registered) {
			this.registered = registered;
		}

		
		public String getfirstName() {
			return firstName;
		}

		public void setfirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getCurrency() {
			return currency;
		}

		public void setCurrency(int currency) {
			this.currency = currency;
		}

	}
	
	private List<String> favoriteColors;
	private int times = 3;
	private List<SelectItem> colors;
	private List<String> seletedItems;
	private List<User> userList;
	private UIData dataTable;
	private String view = "firstName";
	private List<SelectItem> showItems;
	
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

	public List<String> getSeletedItems() {
		return seletedItems;
	}

	public void setSeletedItems(List<String> seletedItems) {
		
		System.out.println("in setSelectedItems");
		
		if(seletedItems !=null) 
		{
			for(String str: seletedItems)
			{
				System.out.println(str);
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
				colors.add(new SelectItem(c,c));
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
