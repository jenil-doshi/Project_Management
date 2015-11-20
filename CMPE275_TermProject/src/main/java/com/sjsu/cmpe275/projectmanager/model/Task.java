package com.sjsu.cmpe275.projectmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String taskName;
	
	private String title;
	
	private String taskState;
	
	private String estimate_time;
	
	private String actual_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getEstimate_time() {
		return estimate_time;
	}

	public void setEstimate_time(String estimate_time) {
		this.estimate_time = estimate_time;
	}

	public String getActual_time() {
		return actual_time;
	}

	public void setActual_time(String actual_time) {
		this.actual_time = actual_time;
	}
	
	
}
