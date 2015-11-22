package com.sjsu.cmpe275.projectmanager.dao;

import com.sjsu.cmpe275.projectmanager.model.Task;

public interface TaskDao {

	public boolean createTask(Task task);
	public Task getTaskById(int taskId);
	public boolean updateTask(Task task);
}
