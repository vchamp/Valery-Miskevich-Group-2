package com.epam.trn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.dao.utils.IdCallback;
import com.epam.trn.dao.utils.UserResultExtractor;
import com.epam.trn.dao.utils.UserRoleRowMapper;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final String USER_UPDATE_FIELDS = "email, login, firstname, lastname, address, phone, active";
	private static final String USER_UPDATE_VALUES = ":email, :login, :firstname, :lastname, :address, :phone, :active";
	private static final String USER_INSERT_FIELDS = USER_UPDATE_FIELDS + ", password"; 
	private static final String USER_INSERT_VALUES = USER_UPDATE_VALUES + ", :password";
	private static final String USER_SELECT_FIELDS = "id, " + USER_INSERT_FIELDS;
	private static final String USER_WITH_ROLES_QUERY = "SELECT u.id, u.email, u.login, u.password, u.firstname, u.lastname, u.address, u.phone, u.active, roles.name AS role_name, roles.id AS role_id FROM users AS u LEFT OUTER JOIN (user_roles JOIN roles ON user_roles.role_id = roles.Id) ON user_roles.user_id = u.id ";
	private static final String ROLE_SELECT_QUERY = "SELECT id, name FROM roles";

	@Override
	public void addRoles(Long userId, List<UserRole> roles) {
		if(roles != null && !roles.isEmpty()) {
			String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)";
			MapSqlParameterSource paramMap = new MapSqlParameterSource();
			paramMap.addValue("userId", userId);
			for(UserRole role : roles) {
				paramMap.addValue("roleId", role.getId());
				template.update(sql, paramMap);
			}
		}
	}

	@Override
	public void removeRoles(Long userId, List<UserRole> roles) {
		if(roles != null && !roles.isEmpty()) {
			String sql = "DELETE FROM user_roles WHERE user_id = :userId AND role_id IN (:rolesIds)";
			ArrayList<Long> rolesIds = new ArrayList<Long>();
			
			for(UserRole role : roles) {
				rolesIds.add(role.getId());
			}
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("userId", userId);
			parameters.addValue("rolesIds", rolesIds);
			
			template.update(sql, parameters);
		}
	}	
	
	@Override
	public void insert(User user) {
		String sql = "INSERT INTO USERS (" + USER_INSERT_FIELDS + ") VALUES (" + USER_INSERT_VALUES + ") RETURNING ID";
//TODO: util		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("login", user.getLogin());
		parameters.addValue("password", user.getPassword());
		parameters.addValue("firstname", user.getFirstName());
		parameters.addValue("lastname", user.getLastName());
		parameters.addValue("address", user.getAddress());
		parameters.addValue("phone", user.getPhone());
		parameters.addValue("active", user.getIsActive());
		parameters.addValue("email", user.getEmail());

		user.setId(template.execute(sql, parameters, new IdCallback()));
	}

	@Override
	public User findByLogin(String login) {
		String sql = USER_WITH_ROLES_QUERY + " WHERE login = :login";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("login", login);
		
		List<User> result = template.query(sql, parameters, new UserResultExtractor());
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public User findById(long id) {
		String sql = USER_WITH_ROLES_QUERY + " WHERE u.id = :id";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);

		List<User> result = template.query(sql, parameters, new UserResultExtractor());
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		//TODO: prepared sortStatement
		String sortStatement = "ORDER BY " + sortBy + ' ' + sortDirrection;
		String filterStatement = filters!=null?"AND roles.name='"+filters+"' ":"";
		String countSql = "SELECT COUNT(*) FROM USERS";
		String sql = 
			"SELECT "
				+ "u.*, "
				+ "roles.name AS role_name, "
				+ "roles.id AS role_id "
			+ "FROM ("
				+ "SELECT " 
					+ USER_SELECT_FIELDS 
				+ " FROM "
					+ "users " 
						+ sortStatement // sorting for pagination
						+ " LIMIT :limit OFFSET :offset"
			+ ") AS u "
			+ "LEFT OUTER JOIN (user_roles "
				+ "JOIN roles "
				+ "ON user_roles.role_id = roles.Id "+filterStatement+") "
			+ "ON user_roles.user_id = u.id "
			+ sortStatement
			; // the same sorting for result list
		
		int count = getJdbcTemplate().queryForInt(countSql);
		int offset = rows * (page - 1);
		int totalPages = (int)Math.ceil((double)count / rows); 

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("limit", rows);
		parameters.addValue("offset", offset);
		SimpleGrid<User> result = new SimpleGrid<User>(template.query(sql, parameters, new UserResultExtractor()));
	
		result.setTotal(totalPages);
		result.setPage(page);
		result.setRecords(count);
		
		return result;
	}

	@Override
	public List<UserRole> getRoles() {
		return template.query(ROLE_SELECT_QUERY, new MapSqlParameterSource(), new UserRoleRowMapper());
	}

	@Override
	public Boolean deleteUsers(ArrayList<Long> ids) {
		//TODO: cascade
		String sqlRoles = "DELETE FROM USER_ROLES WHERE USER_ID IN (:ids)";
		String sqlUsers = "DELETE FROM USERS WHERE ID IN (:ids)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		
		template.update(sqlRoles, parameters);
		return template.update(sqlUsers, parameters) > 0;
	}
	
	@Override
	public Boolean updateUser(User user) {
		String sql = "UPDATE USERS SET (" + USER_UPDATE_FIELDS + ") = (" + USER_UPDATE_VALUES + ") WHERE ID = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("id", user.getId());
		parameters.addValue("login", user.getLogin());
		parameters.addValue("firstname", user.getFirstName());
		parameters.addValue("lastname", user.getLastName());
		parameters.addValue("address", user.getAddress());
		parameters.addValue("phone", user.getPhone());
		parameters.addValue("active", user.getIsActive());
		parameters.addValue("email", user.getEmail());

		return template.update(sql, parameters) > 0;
	}
}
