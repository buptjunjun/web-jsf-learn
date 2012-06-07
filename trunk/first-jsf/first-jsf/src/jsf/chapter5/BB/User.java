package jsf.chapter5.BB;

import java.io.Serializable;

public class User implements Serializable
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