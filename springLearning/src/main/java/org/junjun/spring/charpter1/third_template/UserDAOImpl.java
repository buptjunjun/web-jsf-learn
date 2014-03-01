package org.junjun.spring.charpter1.third_template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDAOImpl implements UserDAO
{
	/**
	 * 数据源
	 */
	private DataSource ds = null;
	
	private JdbcTemplate jdbcTemplate = null;
	
	/**
	 * 可以使用 setter 进行注入
	 * @param ds
	 */
	public void setDs(DataSource ds)
	{
		this.ds = ds;
	}
	
	public  void insert(User u)
	{
		String sql = "insert into user value (?,?)";
		this.jdbcTemplate = new JdbcTemplate(this.ds);
		this.jdbcTemplate.update(sql, new Object[] {u.getName(),u.getAge()});
		
	}
	public List<User> findByName(String name)
	{
		String sql = "select * from user where name=? ";
		this.jdbcTemplate = new JdbcTemplate(this.ds);
		
		List<User> users =  this.jdbcTemplate.query(sql,new Object[]{name}, new UserRowMapper());
		return users;
		
	}
}

class UserRowMapper implements RowMapper
{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();
		user.setName(rs.getString("name"));
		user.setAge(rs.getInt("age"));
		return user;
	}
	
}
