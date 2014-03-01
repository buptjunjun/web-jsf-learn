package org.junjun.spring.charpter1.third_template;

import java.util.List;

public interface UserDAO {
	public  void insert(User u);
	public List<User> findByName(String name);
}
