package com.sjsu.cmpe275.projectmanager.dao;

import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;
import com.sjsu.cmpe275.projectmanager.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Queue;



@Repository("UserDAO")
@Transactional

public class UserDAOImpl extends AbstractDAO<Integer, User> implements UserDAO {

	@Autowired
	SessionFactory session;

	
	public User createUser(User user) {

		session.getCurrentSession().persist(user);
		System.out.println("In User DAO");
		return user;
	}
	
	

	
	public User getUser(int userId) throws EntityNotFound {
		
		return (User) session.getCurrentSession().get(User.class, userId);
	}

	// public List<User> getAllUser() {
	// return null;
	// }

	public User updateUser(User updatedUser) throws EntityNotFound {
		
		session.getCurrentSession().update(updatedUser);
		return updatedUser;
	}

	public User deleteUser(int userId) throws EntityNotFound {
		
		User userToDelete = getByKey(userId);
		session.getCurrentSession().delete(userToDelete);

		if (userToDelete != null) {
			return userToDelete;
		} else {
			throw new EntityNotFound("User not found.");
		}
	}
}