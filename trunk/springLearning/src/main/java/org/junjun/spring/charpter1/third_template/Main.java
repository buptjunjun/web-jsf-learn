package org.junjun.spring.charpter1.third_template;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext app = new ClassPathXmlApplicationContext("charpter1/jdbc_template.xml");
		User u = new User();
		u.setName("andy");
		u.setAge(27);
		
		UserDAO userDao = (UserDAO) app.getBean("userDaoImpl");
		userDao.insert(u);
		List<User> users = userDao.findByName(u.getName());
		for(User user:users)
			System.out.println(user.getName()+","+user.getAge());
	}

}
