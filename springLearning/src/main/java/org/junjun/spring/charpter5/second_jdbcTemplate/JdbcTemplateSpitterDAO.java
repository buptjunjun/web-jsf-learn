package org.junjun.spring.charpter5.second_jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junjun.spring.charpter5.common.Spitter;
import org.junjun.spring.charpter5.common.SpitterDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 
 * jdbcTemplate 和 NamedParameterJdbcTemplate测试。
 create table spitter
(
 id int(11) AUTO_INCREMENT PRIMARY key,
 username varchar(200),
 password varchar(20),
 fullname varchar(50)
);
 * @author junjun
 *
 */
public class JdbcTemplateSpitterDAO implements SpitterDAO
{
	
	private static final String SQL_INSERT_SPITTER =
			"insert into spitter (username, password, fullname) values (:usernameee, :passworddd, :fullnameee)"; 
	
	private static final String SQL_SELECT_SPITTER =
			"select id, username, password, fullname from spitter where id = ?";
	
	private JdbcTemplate jdbcTeplate = null;
	private NamedParameterJdbcTemplate NPjdbcTeplate = null;
	
	/**
	 * 使用NamedParameterJdbcTemplate
	 * 注意我们将usernameee与username对应,以后不管username,password,fullname如何交换顺序
	 * 都没有影响
	 * 注意sql的写法:insert into spitter (username, password, fullname) values (:usernameee, :passworddd, :fullnameee)
	 */
	public void addSpitter(Spitter spitter)
	{
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("usernameee", spitter.getUsername());
		params.put("passworddd", spitter.getPassword());
		params.put("fullnameee", spitter.getFullname());
		
		this.NPjdbcTeplate.update(SQL_INSERT_SPITTER, params);
	}

	public Spitter getSpitterById(long id)
	{
		Spitter ret = null;
		ret = this.jdbcTeplate.queryForObject(SQL_SELECT_SPITTER, 
											new ParameterizedRowMapper<Spitter>(){
												public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException
												{
													Spitter spitter = new Spitter();
													spitter.setId(rs.getLong(1));
													spitter.setUsername(rs.getString(2));
													spitter.setPassword(rs.getString(3));
													spitter.setFullname(rs.getString(4));
													return spitter;
												}
											},
											id);
		return ret;
	}

	public void setJdbcTeplate(JdbcTemplate jdbcTeplate)
	{
		this.jdbcTeplate = jdbcTeplate;
	}

	public void setNPjdbcTeplate(NamedParameterJdbcTemplate nPjdbcTeplate)
	{
		NPjdbcTeplate = nPjdbcTeplate;
	}
	
	

}
