
package com.epam.trn.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import com.epam.trn.dao.UserDao;
import com.epam.trn.mailing.Mail;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.Grid;

@Service
public class UsersService {
	@Autowired
	private UserDao userDao;

	public Grid<User> getUsersGrid(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		return userDao.getUsersPage(filters, page, rows, sortBy, sortDirrection);
	}
	
	public void createUser(User user) throws NoSuchAlgorithmException {
		String tempPassword = UUID.randomUUID().toString();
		byte[] digest = MessageDigest.getInstance("MD5").digest(Utf8.encode(tempPassword));
		String hashedPassword = Utf8.decode(Base64.encode(digest));
		
		user.setPassword(hashedPassword);
		if(user.getLogin().isEmpty()) {
			user.setLogin(user.getEmail());
		}

		//TODO: validate email before sending to avoid unnecessary email limits usage
		if(Mail.sendRegistrationMessage(user.getEmail(), user.getLogin(), tempPassword)) {
			userDao.insert(user);
		} else {
			//TODO: response
		}
	}
	
	public void updateUser(User user, List<Long> roles) {
		User existingUser = userDao.findById(user.getId());
	
		if(existingUser != null) {
			if(user.getEmail() != null) {
				existingUser.setEmail(user.getEmail());
			}
			if(user.getLogin() != null) {
				existingUser.setLogin(user.getLogin());
			}
			if(user.getFirstName() != null) {
				existingUser.setFirstName(user.getFirstName());
			}
			if(user.getLastName() != null) {
				existingUser.setLastName(user.getLastName());
			}
			if(user.getAddress() != null) {
				existingUser.setAddress(user.getAddress());
			}
			if(user.getPhone() != null) {
				existingUser.setPhone(user.getPhone());
			}
			if(user.getIsActive() != null) {
				existingUser.setIsActive(user.getIsActive());
			}
			
			updateRoles(user.getId(), existingUser.getRoles(), roles);
			userDao.updateUser(existingUser);
		}
	}
	
	public void updateRoles(Long userId, List<UserRole> existingsRoles, List<Long> newRoles) {
		if(newRoles != null) {
			List<UserRole> newUserRoles = new ArrayList<UserRole>();
			for(Long roleId : newRoles) {
				UserRole newRole = new UserRole();
				newRole.setId(roleId);
				newUserRoles.add(newRole);
			}
			
			List<UserRole> removeRoles = new ArrayList<UserRole>(existingsRoles);
			removeRoles.removeAll(newUserRoles);
			userDao.removeRoles(userId, removeRoles);
			
			newUserRoles.removeAll(existingsRoles);
			userDao.addRoles(userId, newUserRoles);
		}
	}
	
	public void deleteUsers(String idString) {
		String[] parsedIds = idString.split(",");
		int count = parsedIds.length;
		ArrayList<Long> ids = new ArrayList<Long>();
		
		for(int i = 0; i < count; i++) {
			ids.add(Long.parseLong(parsedIds[i]));
		}
		
		userDao.deleteUsers(ids);
	}

	public String getRoles() {
		String result = "<select>";
		for(UserRole role : userDao.getRoles()) {
			result += "<option value='" + role.getId() + "'>" + role.getName() + "</option>";
		}
		result += "</select>";
		
		return result;
	}
}
