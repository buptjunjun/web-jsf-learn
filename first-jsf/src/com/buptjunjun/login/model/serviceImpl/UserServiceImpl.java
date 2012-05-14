package com.buptjunjun.login.model.serviceImpl;
import com.buptjunjun.login.model.bean.UserBean;
import com.buptjunjun.login.model.exception.UserException;
import com.buptjunjun.login.model.service.*;

/**
 * 具体实现 UserService
 * @author andyWebsense
 * @return 如果登录成功返回 user 否则返回null
 */
public class UserServiceImpl implements UserService 
{
	/**
	 * 用户的登陆方法具体实现
	 */

	public UserBean login(String name, String password) throws UserException {
		// TODO Auto-generated method stub
		UserBean user = null;
		
		if(name.equals("buptjunjun"))
		{
			System.out.println("name  is ok");
			if(password.equals("1234abcd"))
			{
				System.out.println("password is ok");
				user = new UserBean();
				user.setName(name);
				user.setPassword(password);
				return user;
			}
			else
			{   
				//密码错误
				user = null;
			}
		}
		else
		{
			//用户名不存在
			throw new UserException(name);
		}
		
		return user;
	}

}
