package com.buptjunjun.login.model.service;

import com.buptjunjun.login.model.bean.UserBean;
import com.buptjunjun.login.model.exception.UserException;

/**
 * �����û���¼�Ľӿ�
 * @author andyWebsense
 *
 */


public interface UserService 
{
	/**
	 * ʵ���û���¼
	 * @param name
	 * @param password
	 * @return
	 * @throws UserException
	 */
     public UserBean login(String name, String password) throws UserException;
}
