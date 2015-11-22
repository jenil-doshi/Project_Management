package com.sjsu.cmpe275.projectmanager.controller;


import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;
import com.sjsu.cmpe275.projectmanager.model.*;
import com.sjsu.cmpe275.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;

    /**
     * Create a User by passing the following parameters
     
     */

    @RequestMapping(value= {"/create"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> createPerson(@RequestParam(value = "firstname", required = true) String FirstName,
                                               @RequestParam(value = "lastname", required = true) String LastName,
                                               @RequestParam(value = "email", required = true) String Email,
                                               @RequestParam(value = "password", defaultValue = "") String Password
                                               ) {


    	User userObj = new User();

        if (FirstName == null || "".equalsIgnoreCase(FirstName)
                || LastName == null || "".equalsIgnoreCase(LastName)
                || Email == null || "".equalsIgnoreCase(Email)) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

        userObj.setFirstName(FirstName);
        userObj.setLastName(LastName);
        userObj.setEmail(Email);
        userObj.setPassword(Password);

    
        User createdUser = userService.createUser(userObj);

        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
    }

    /**
     * Get a user by Id in HTML format
    
     */


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    public ResponseEntity<User> getPersonHTML(@PathVariable(value = "id") int userId, ModelMap model) throws EntityNotFound {
    	System.out.println("UserId: " + userId);
    	User user = userService.getUser(userId);
    	
        /*if (user == null) {
            throw new EntityNotFound("User Not Found.");
        }*/
        //model.addAttribute("user", user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    /**
     * Update a user by id     
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> updatePerson(
            @PathVariable(value = "id") int UID,
            @RequestParam(value = "Email", required = true) String email,
            @RequestParam(value = "FirstName", required = true) String FirstName,
            @RequestParam(value = "LastName", required = true) String LastName,
            @RequestParam(value = "Password", defaultValue = "") String Password
            ) throws Exception {

    	User user = userService.getUser(UID);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        user.setEmail(email);

        if (FirstName != null || !"".equalsIgnoreCase(FirstName))
        	user.setFirstName(FirstName);
        if (LastName != null || !"".equalsIgnoreCase(LastName))
        	user.setLastName(LastName);
        if (Password != null || !"".equalsIgnoreCase(Password))
        	user.setPassword(Password);
        
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