
package com.sjsu.cmpe275.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	/**
	 * Allow to update only firstname and lastname. Email cannot be updated as
	 * it is PK in users table. password change will be hanfdled through a
	 * different api
	 * 
	 * @param uid
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/update/{id}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User updateUser(@PathVariable(value = "id") int userId, @ModelAttribute User user) {
		
		ModelAndView mv = new ModelAndView();
		User user1 = null;
		
		try{
		user1 = userService.getUser(userId);
		if (user1 == null) {
			System.out.println("User not found...");
		}

			if (user.getFirstName() != null){
				user1.setFirstName(user.getFirstName());
			}
			if (user.getLastName() != null){
				user1.setLastName(user.getLastName());
			}
			if (user.getEmail() != null){
				user1.setEmail(user.getEmail());
			}
			if (user.getPassword() != null){
				user1.setPassword(user.getPassword());
			}
			
			mv.setViewName("updateUser");
			if (userService.updateUser(user1)){
				mv.addObject("updateUser", user1);
				return user1;
			}
		} catch (RuntimeException e){
			user = null;
			mv.addObject("updateUser", user1);
			return user1;
		}
		return null;		

		}

	/**
	 * Delete a user
	 *
	 * @param userId
	 * @return
	 * @throws EntityNotFound
	 */
	/**
	 * This method needs to be changed. Change to deleting user from user project info. Also change user role from ROLE_USER to ROLE_ADMIN
	 * @param UID
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