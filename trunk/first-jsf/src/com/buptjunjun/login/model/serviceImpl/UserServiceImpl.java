package com.buptjunjun.login.model.serviceImpl;
import com.buptjunjun.login.model.bean.UserBean;
import com.buptjunjun.login.model.exception.UserException;
import com.buptjunjun.login.model.service.*;

/**
 * ����ʵ�� UserService
 * @author andyWebsense
 * @return �����¼�ɹ����� user ���򷵻�null
 */
public class UserServiceImpl implements UserService 
{
	/**
	 * �û��ĵ�½��������ʵ��
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
				//�������
				user = null;
			}
		}
		else
		{
			//�û���������
			throw new UserException(name);
		}
		
		return user;
	}

}
