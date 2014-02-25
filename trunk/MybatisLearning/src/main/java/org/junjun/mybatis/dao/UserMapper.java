package org.junjun.mybatis.dao;

import org.junjun.mybatis.bean.User;

	public interface UserMapper {  
	    public User findByName(String name);  
	    public void insertUser(User user);
/*	    public void updateUser(User user);
	    public void deleteUser(String name);*/
	}  

