package com.epam.trn.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public interface UserDao {
	public void insert(User user);

	public User findByLogin(String username);

	public User findById(long id);
	
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection);
		
	public Boolean deleteUsers(ArrayList<Long> ids);
	
	public Boolean updateUser(User user);

	public List<UserRole> getRoles();

	public void addRoles(Long userId, List<UserRole> roles);

	public void removeRoles(Long userId, List<UserRole> roles);
}
