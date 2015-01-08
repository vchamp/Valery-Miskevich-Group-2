/**
 * 
 */
package com.epam.trn.dao.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

import com.epam.trn.model.User;

/**
 * @author Siarhei Klimuts
 *
 */
public class UserRowMapper implements RowMapper<User> {
	public static final String USER_ID = "id";
	public static final String USER_EMAIL = "email";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "firstname";
	public static final String USER_LAST_NAME = "lastname";
	public static final String USER_ADDRESS = "address";
	public static final String USER_PHONE = "phone";
	public static final String USER_ACTIVE = "active";
	public static final String ROLE_NAME = "role_name";
	public static final String ROLE_ID = "role_id";
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		HashMap<String, Object> columns = new HashMap<String, Object>();
		ResultSetMetaData meta = rs.getMetaData();

		for(int i = 1; i < meta.getColumnCount() + 1; i++) {
			String label = meta.getColumnLabel(i);
			columns.put(label, rs.getObject(label));
		}
		
		User user = new User();
		user.setId((Long)columns.get(USER_ID));
		user.setEmail((String)columns.get(USER_EMAIL));
		user.setLogin((String)columns.get(USER_LOGIN));
		user.setPassword((String)columns.get(USER_PASSWORD));
		user.setFirstName((String)columns.get(USER_FIRST_NAME));
		user.setLastName((String)columns.get(USER_LAST_NAME));
		user.setAddress((String)columns.get(USER_ADDRESS));
		user.setPhone((String)columns.get(USER_PHONE));
		user.setIsActive((Boolean)columns.get(USER_ACTIVE));

		return user;
	}
}