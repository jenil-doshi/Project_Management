package com.sjsu.cmpe275.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.model.Users;
//import com.sjsu.cmpe275.projectmanager.model.UserRoles;
//import com.sjsu.cmpe275.projectmanager.model.Users;
import com.sjsu.cmpe275.projectmanager.service.UserService;

@Controller
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
@RequestMapping("/user")

public class UserController {

	@Autowired
	UserService userService;

	/**
	 * Create a User by passing the following parameters
	 * 
	 */

	@RequestMapping(value = { "/create" }, method = RequestMethod.POST)
	public ResponseEntity<User> createPerson(@RequestParam(value = "firstname", required = true) String firstName,
			@RequestParam(value = "lastname", required = true) String lastName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", defaultValue = "") String password) {

		User userObj = new User();

		if (firstName == null || "".equalsIgnoreCase(firstName) || lastName == null || "".equalsIgnoreCase(lastName)
				|| email == null || "".equalsIgnoreCase(email)) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}

		userObj.setFirstName(firstName);
		userObj.setLastName(lastName);
		userObj.setEmail(email);
		userObj.setPassword(password);

		UserRoles role = new UserRoles();
		role.setRole(Constants.ROLE_ADMIN);
		role.setUsername(email);

		Users users = new Users();
		users.setUsername(email);
		users.setPassword(password);
		users.setEnabled(Constants.ENABLED);

		User createdUser = userService.createUser(userObj, role, users);

		return new ResponseEntity<User>(createdUser, HttpStatus.OK);
	}

	/**
	 * Get a user by Id in HTML format
	 * 
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<User> getPersonHTML(@PathVariable(value = "id") int userId, ModelMap model)
			throws EntityNotFound {
		System.out.println("UserId: " + userId);
		User user = userService.getUser(userId);

		/*
		 * if (user == null) { throw new EntityNotFound("User Not Found."); }
		 */
		// model.addAttribute("user", user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Update a user by id
	 */

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<User> updatePerson(@PathVariable(value = "id") int uid,
			@RequestParam(value = "FirstName", required = true) String firstName,
			@RequestParam(value = "LastName", required = true) String lastName) throws Exception {

		User user = userService.getUser(uid);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		if (firstName != null || !"".equalsIgnoreCase(firstName))
			user.setFirstName(firstName);
		if (lastName != null || !"".equalsIgnoreCase(lastName))
			user.setLastName(lastName);

		
		
		User updatedPerson = userService.updateUser(user);
		return new ResponseEntity<User>(updatedPerson, HttpStatus.OK);
	}

	/**
	 * Delete a user
	 *
	 * @param userId
	 * @return
	 * @throws EntityNotFound
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<User> deletePerson(@PathVariable(value = "id") int UID) throws EntityNotFound {

		User userToDelete = userService.getUser(UID);
		if (userToDelete == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

		User deletedUser = userService.deleteUser(UID);
		return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
	}

}