package org.junjun.mybatis.dao;

import org.junjun.mybatis.bean.User;

	public interface UserMapper {  
	    public User findByName(String name);  
	}  

