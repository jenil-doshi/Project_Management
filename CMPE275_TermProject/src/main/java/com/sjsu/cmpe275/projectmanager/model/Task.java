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
	@Column(name="TID")
	private int tid;
	
	@Column(name="TaskName")
	private String taskName;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="State")
	private String taskState;
	
	@Column(name="EstimatedUnits")
	private String estimate_time;
	
	@Column(name="ActualTime")
	private String actual_time;
	
	@OneToOne
	@JoinColumn(name="User", referencedColumnName = "UID")
	private User assignee;
	
	@ManyToOne
	@JoinColumn(name="Project",referencedColumnName = "PID")
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
