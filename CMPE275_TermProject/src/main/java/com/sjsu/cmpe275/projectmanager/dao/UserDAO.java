package com.sjsu.cmpe275.projectmanager.dao;

import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;

//import java.util.List;

public interface UserDAO {

		public User createUser(User user);

	    public User getUser(int personId) throws EntityNotFound;

//	    public List<User> getAllUser();

	    public User updateUser(User user) throws EntityNotFound;

	    public User deleteUser(int userId) throws EntityNotFound;

	   

}
