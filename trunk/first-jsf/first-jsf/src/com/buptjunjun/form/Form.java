package com.buptjunjun.form;

public class Form {


	private String userName = null;	
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String submit()
	{
		System.out.println(userName);
		return userName;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
