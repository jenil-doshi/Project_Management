package com.sjsu.cmpe275.projectmanager.service;

import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.model.Users;
//import com.sjsu.cmpe275.projectmanager.model.UserRoles;
//import com.sjsu.cmpe275.projectmanager.model.Users;
import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface UserService {

	public boolean createUser(User user, UserRoles roles, Users users);

	public User getUser(int userId) throws EntityNotFound;

	// public List<User> getAllUser();

	public boolean updateUser(User user) throws EntityNotFound;

	public User deleteUser(int userId) throws EntityNotFound;

	public String getUserProjectStatus(int userId, int projectId);

	public void updateUserRole(UserRoles roles);
	
	public String getUserRole(String username);

	public User getUserByUserName(String userName);
}
