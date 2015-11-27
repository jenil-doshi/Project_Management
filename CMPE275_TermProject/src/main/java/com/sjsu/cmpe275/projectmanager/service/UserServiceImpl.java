package com.sjsu.cmpe275.projectmanager.service;

import com.sjsu.cmpe275.projectmanager.dao.UserDAO;
import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;
//import com.sjsu.cmpe275.projectmanager.model.UserRoles;//
//import com.sjsu.cmpe275.projectmanager.model.Users;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	public User getUser(int userId) {
		// return userDao.getUser(userId);
		return userDao.getUser(userId);
	}

	public User createUser(User user, UserRoles roles, Users users) {
		return userDao.createUser(user, roles, users);
	}

	// public List<User> getAllPerson() {
	// return null;
	// }

	public User updateUser(User user) throws EntityNotFound {

		User usrRet = userDao.updateUser(user);
		return usrRet;
	}

	public User deleteUser(int id) throws EntityNotFound {
		// User user = userDao.deleteUser(id);
		User user = userDao.deleteUser(id);
		return user;

	}

	@Override
	public String getUserProjectStatus(int userId, int projectId) {
		String status = userDao.getUserProjectStatus(userId, projectId);
		return status;
	}

	@Override
	public void updateUserRole(UserRoles roles) {
		userDao.updateUserRoles(roles);
	}

	@Override
	public String getUserRole(String username) {
		return userDao.getUserRole(username);
	}
	

}
