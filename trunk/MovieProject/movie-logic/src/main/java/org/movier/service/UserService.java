package org.movier.service;

import org.movier.bean.User;

public interface UserService 
{
	public String addUser(User u);
	public String delUser(String name);
	public String updateUser(User u);
	public User getUser(String name);
}
