package com.buptjunjun.login.model.service;

import com.buptjunjun.login.model.bean.UserBean;
import com.buptjunjun.login.model.exception.UserException;

/**
 * 处理用户登录的接口
 * @author andyWebsense
 *
 */


public interface UserService 
{
	/**
	 * 实现用户登录
	 * @param name
	 * @param password
	 * @return
	 * @throws UserException
	 */
     public UserBean login(String name, String password) throws UserException;
}
