package com.sjsu.cmpe275.projectmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.dao.TaskDao;
import com.sjsu.cmpe275.projectmanager.model.Task;

@Service
@Transactional
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
public class TaskService {

	@Autowired
	TaskDao taskDao;

	public boolean createTask(Task task) throws RuntimeException {
		if (task.getEstimated_time() == null) {
			task.setEstimated_time(0);
		}
		if (task.getActual_time() == null) {
			task.setActual_time(0);
		}
		
		return taskDao.createTask(task);
	}

	public Task getTaskById(Integer id) {
		return taskDao.getTaskById(id);
	}

	public boolean updateTask(Task task) throws RuntimeException {
		return taskDao.updateTask(task);
	}

	////////// Task_Finish....../////////////
	/*
	 * public boolean finishTask(Task task) throws RuntimeException { return
	 * taskDao.updateTask(task); }
	 * 
	 */

	public List<Task> getTasks(int projectId) {
		return taskDao.getTasks(projectId);
	}
}
