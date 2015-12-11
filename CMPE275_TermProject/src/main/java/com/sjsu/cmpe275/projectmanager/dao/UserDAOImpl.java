package com.sjsu.cmpe275.projectmanager.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.projectmanager.configuration.Queries;
import com.sjsu.cmpe275.projectmanager.exception.EntityNotFound;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.model.Users;

@Repository("UserDAO")

public class UserDAOImpl extends AbstractDAO<Integer, User> implements UserDAO {

	@Autowired
	SessionFactory session;

	public boolean createUser(User user, UserRoles roles, Users users) {

		boolean status = false;
		try {
			session.getCurrentSession().persist(user);
			session.getCurrentSession().persist(users);
			session.getCurrentSession().persist(roles);
			System.out.println("In User DAO");
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occurred in createUser()");
		}

	}

	public User getUser(int userId) throws EntityNotFound {

		return (User) session.getCurrentSession().get(User.class, userId);
	}

	// public List<User> getAllUser() {
	// return null;
	// }

	public boolean updateUser(User updatedUser) throws EntityNotFound {

		boolean status = false;
		try {
			session.getCurrentSession().update(updatedUser);
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occured while updating the user");
		}

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

	@Override
	public String getUserProjectStatus(int userId, int projectId) {
		String result = null;
		try {
			Query query = session.getCurrentSession().createQuery(Queries.GET_USER_PROJECT_STATUS);
			query.setParameter("userId", userId);
			query.setParameter("projectId", projectId);
			result = (String) query.uniqueResult();
			System.out.println("Result of status: " + result);
		} catch (Exception e) {

		}
		return result;
	}

	@Override
	public void updateUserRoles(UserRoles roles) {
		session.getCurrentSession().update(roles);

	}

	@Override
	public String getUserRole(String username) {
		UserRoles userRole = (UserRoles) session.getCurrentSession().get(UserRoles.class, username);
		return userRole.getRole();
	}

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		try {
			Query query = session.getCurrentSession().createQuery("from User where email=:userName")
					.setParameter("userName", userName);
			user = (User) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime exception has occured");
		}
		return user;
	}
}