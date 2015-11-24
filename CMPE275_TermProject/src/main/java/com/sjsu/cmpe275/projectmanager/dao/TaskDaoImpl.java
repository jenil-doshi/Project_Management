package com.sjsu.cmpe275.projectmanager.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.configuration.Queries;
import com.sjsu.cmpe275.projectmanager.model.*;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasks(int projectId) {
			List<Task> taskList = null;
			try {
				Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASK_LIST);
				query.setParameter("projectId", projectId);
				taskList = query.list();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("A Runtime Exception has occurred");
			}
			return taskList;
	}
}
