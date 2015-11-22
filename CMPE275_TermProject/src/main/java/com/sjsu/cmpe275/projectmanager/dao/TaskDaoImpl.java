package com.sjsu.cmpe275.projectmanager.dao;

import org.dbunit.util.concurrent.Takable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.Task;
import com.sjsu.cmpe275.projectmanager.model.User;

@Repository
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean createTask(Task task) {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(task);
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occured while creating task");
		}
	}

	@Override
	public Task getTaskById(int taskId) {
		Task task = (Task) sessionFactory.getCurrentSession().get(Task.class, taskId);
		return task;
	}

	@Override
	public boolean updateTask(Task task) {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(task);
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occured while updating task");
		}
	}

}
