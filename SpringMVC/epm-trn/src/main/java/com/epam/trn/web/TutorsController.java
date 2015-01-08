package com.epam.trn.web;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.model.User;
import com.epam.trn.service.UsersService;
import com.epam.trn.web.grid.Grid;

@Controller
@RequestMapping("/tutors")
public class TutorsController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@JsonSerialize
	public @ResponseBody Grid<User> getTutors(
			@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sortBy,
    		@RequestParam(value="sord", required=false) String sortDirrection) {
		
		return usersService.getUsersGrid("ROLE_TUTOR", page, rows, sortBy, sortDirrection);
	}
}
