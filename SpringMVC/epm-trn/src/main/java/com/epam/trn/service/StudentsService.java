/**
 * 
 */
package com.epam.trn.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.dao.UserDao;
import com.epam.trn.mailing.Mail;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

/**
 * @author Siarhei Klimuts
 *
 */
//TODO: configure to RESTful (GET, POST, PUT, DELETE)
@Controller
public class StudentsService {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(method=RequestMethod.GET, value="/students", headers="Accept=application/json")
	public @ResponseBody SimpleGrid<User> getStudents(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
				
		return userDao.getUsersPage(null, page, rows, sortBy, sortDirrection);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/create", headers="Accept=application/json")
	public @ResponseBody Boolean createStudent(
			@RequestParam String login,
			//@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String address,
			@RequestParam String phone,
			@RequestParam Boolean isActive,
			@RequestParam String email) throws NoSuchAlgorithmException {
		
		String tempPassword = UUID.randomUUID().toString();
		byte[] digest = MessageDigest.getInstance("MD5").digest(Utf8.encode(tempPassword));
		String hashedPassword = Utf8.decode(Base64.encode(digest));
		
		UserRole role = new UserRole();
		role.setName("ROLE_STUDENT");
		login = email;
		
		User newUser = new User();
		newUser.setLogin(login);
		newUser.setPassword(hashedPassword);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		newUser.setEmail(email);
		newUser.addRole(role);

		userDao.insert(newUser);
		
		Mail.sendRegistrationMessage(email, login, tempPassword);
		
		return true;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/update", headers="Accept=application/json")
	public @ResponseBody Boolean updateStudent(	
				@RequestParam long id, 
				@RequestParam String login, 
				//@RequestParam String password, 
				@RequestParam String firstName,
				@RequestParam String lastName, 
				@RequestParam String address, 
				@RequestParam String phone, 
				@RequestParam Boolean isActive,
				@RequestParam String email) {
		
		boolean result = false;
		User user = userDao.findById(id);
		
		if(user != null) {
			user.setLogin(login);
			//user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setPhone(phone);
			user.setIsActive(isActive);
			user.setEmail(email);
			
			result = userDao.updateUser(user);
		}
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/students/delete", headers="Accept=application/json")
	public @ResponseBody Boolean deleteStudent(@RequestParam String id) {
		String[] parsedIds = id.split(",");
		int count = parsedIds.length;
		ArrayList<Long> ids = new ArrayList<Long>();
		
		for(int i = 0; i < count; i++) {
			ids.add(Long.parseLong(parsedIds[i]));
		}
		
		return userDao.deleteUsers(ids);
	}
}