package com.sjsu.cmpe275.projectmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TID")
	private int tid;

	@Column(name = "TaskName")
	private String taskName;

	@Column(name = "Description")
	private String description;

	@Column(name = "State")
	private String taskState;

	@Column(name = "EstimatedUnits")
	private int estimated_time;

	@Column(name = "ActualTime")
	private String actual_time;

	@OneToOne
	@JoinColumn(name = "Assignee", referencedColumnName = "UID")
	private User assignee;

	@ManyToOne
	@JoinColumn(name = "Project", referencedColumnName = "PID")
	private Project project;

	public int getTid() {
		return tid;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEstimated_time(int estimated_time) {
		this.estimated_time = estimated_time;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public int getEstimated_time() {
		return estimated_time;
	}

	public void setEstimate_time(int estimated_time) {
		this.estimated_time = estimated_time;
	}

	public String getActual_time() {
		return actual_time;
	}

	public void setActual_time(String actual_time) {
		this.actual_time = actual_time;
	}

}
