package com.sjsu.cmpe275.projectmanager.dao;

import java.util.List;

import com.sjsu.cmpe275.projectmanager.model.Task;

public interface TaskDao {

	public boolean createTask(Task task);
	public Task getTaskById(int taskId);
	public boolean updateTask(Task task);
	//public boolean finishTask(Task task);
	public List<Task> getTasks(int projectId);
}
