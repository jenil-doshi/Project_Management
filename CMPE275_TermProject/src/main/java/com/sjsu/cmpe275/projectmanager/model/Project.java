package com.sjsu.cmpe275.projectmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "project_name")
	private String name;
	@Column(name = "project_description")
	private String description;
	@Column
	private String status;

	@OneToOne
	@JoinColumn(name = "userId")
	private User owner;

	/*
	 * private List<User> userList; private List<Task> taskList;
	 */
	/*
	 * public List<Task> getTaskList() { return taskList; }
	 * 
	 * public void setTaskList(List<Task> taskList) { this.taskList = taskList;
	 * }
	 */

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * public List<User> getUserList() { return userList; }
	 * 
	 * public void setUserList(List<User> userList) { this.userList = userList;
	 * }
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
