package com.epam.trn.web;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.model.User;
import com.epam.trn.service.UsersService;
import com.epam.trn.web.grid.Grid;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@JsonSerialize
	public @ResponseBody Grid<User> getUsers(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
		
		return usersService.getUsersGrid(null, page, rows, sortBy, sortDirrection);
	}
	
	/*@RequestMapping(method=RequestMethod.POST, value="/create")
	public @ResponseBody void createUser(
				@RequestParam(required=true) String email,
				@RequestParam(required=false) String login,
				@RequestParam(required=false) String firstName,
				@RequestParam(required=false) String lastName,
				@RequestParam(required=false) String address,
				@RequestParam(required=false) String phone,
				@RequestParam(required=false) Boolean isActive,
				@RequestParam(value="role", required=false) List<Long> roles) throws NoSuchAlgorithmException {
		
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setLogin(login);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		
		usersService.createUser(newUser);
		usersService.updateRoles(newUser.getId(), newUser.getRoles(), roles);
	}*/
	
	@RequestMapping(method=RequestMethod.POST, value="/create", headers="Accept=application/json")
	public @ResponseBody void createUser(@RequestBody User user) throws NoSuchAlgorithmException {
		
		User newUser = user;
		
		usersService.createUser(newUser);
//		usersService.updateRoles(newUser.getId(), newUser.getRoles(), roles);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/update", headers="Accept=application/json")
	public @ResponseBody void updateUser(
			@RequestParam(required=true)  Long id, 
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String login,
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) Boolean isActive,
			@RequestParam(value="role[]", required=false) List<Long> roles) {
		
		User newUser = new User();
		newUser.setId(id);
		newUser.setEmail(email);
		newUser.setLogin(login);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setAddress(address);
		newUser.setPhone(phone);
		newUser.setIsActive(isActive);
		
		usersService.updateUser(newUser, roles);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/delete")
	public @ResponseBody void deleteUser(@RequestParam String id) {
		usersService.deleteUsers(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/roles")
	public @ResponseBody String getRoles() {
		return usersService.getRoles();
	}
}
