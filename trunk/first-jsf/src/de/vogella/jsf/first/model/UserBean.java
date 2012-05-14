package de.vogella.jsf.first.model;

public class UserBean 
{
    public String userName;
	public UserBean(){};
	
	public String decide()
	{
		if(userName!=null && userName.equalsIgnoreCase("newFriend"))
			return "newFriend";
		return "oldFriend";
	}
      public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


      
}
