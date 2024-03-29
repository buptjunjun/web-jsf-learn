package org.junjun.mybatis.test;


import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junjun.mybatis.bean.User;
import org.junjun.mybatis.dao.UserMapper;


/**
 * myBatis数据库测试
 * 
 * 
 */
public class MyBatisTest {
	/**
	 * 获得MyBatis SqlSessionFactory  
	 * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句，commit，rollback，close等方法。
	 * @return
	 */
	private static SqlSessionFactory getSessionFactory() {
		SqlSessionFactory sessionFactory = null;
		String resource = "configuration.xml";
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources
					.getResourceAsReader(resource));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		SqlSession sqlSession = getSessionFactory().openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		// test select
		User user = userMapper.findByName("andy");
		System.out.println(user.getName());
		
		// test insert
		User user1 = new User();
		user1.setName("xl");
		user1.setAge(22);		
		userMapper.insertUser(user1);
		//it is a must or no data will be insert into server.
		sqlSession.commit();
		

	}

}