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
	
	@Column(name="Description")
	private String description;
	
	@Column(name="State")
	private String taskState;
	
	@Column(name="EstimatedUnits")
	private Integer estimated_time;
	
	@Column(name="ActualTime")
	private Integer actual_time;
	
	/*@OneToOne
	@JoinColumn(name="Assignee", referencedColumnName = "UID")*/
	@Column(name="Assignee")
	private Integer assignee;
	
	@ManyToOne
	@JoinColumn(name="Project",referencedColumnName = "PID")
	private Project project;


	public int getTid() {
		return tid;
	}

	public Integer getAssignee() {
		return assignee;
	}

	public void setAssignee(Integer assignee) {
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

	public void setDescription(String title) {
		this.description = title;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public Integer getEstimated_time() {
		return estimated_time;
	}

	public void setEstimate_time(Integer estimated_time) {
		this.estimated_time = estimated_time;
	}

	public Integer getActual_time() {
		return actual_time;
	}

	public void setActual_time(Integer actual_time) {
		this.actual_time = actual_time;
	}
}