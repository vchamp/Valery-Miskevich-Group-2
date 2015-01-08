/**
 * 
 */
package com.epam.trn.dao.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.trn.model.UserRole;

/**
 * @author Siarhei Klimuts
 *
 */
public class UserRoleRowMapper implements RowMapper<UserRole> {
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRole userRole = new UserRole();
		userRole.setId(rs.getLong("id"));
		userRole.setName(rs.getString("name"));
		return userRole;
	}
}
