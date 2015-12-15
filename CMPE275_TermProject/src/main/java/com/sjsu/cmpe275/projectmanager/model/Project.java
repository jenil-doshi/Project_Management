package com.sjsu.cmpe275.projectmanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect
@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PID")
	private int pid;

	@Column(name = "ProjectName")
	private String name;

	@Column(name = "ProjectDescription")
	private String description;

	@Column(name = "Status")
	private String status;

	@OneToOne
	@JoinColumn(name = "Owner", referencedColumnName = "UID")
	private User owner;

	@Column(name = "StartDate")
	private Date startDate;

	@Column(name = "EndDate")
	private Date endDate;

	@Transient
	private int taskUnitsTobeFinished;
	@Transient
	private int taskUnitsFinished;
	@Transient
	private int taskUnitsAtPlanningPhase;
	@Transient
	private int taskUnitsCancelled;
	@Transient
	private double percentTaskUnitsFinished;
	@Transient
	private double percentTaskUnitsTobeFinished;
	@Transient
	private double percentTaskUnitsCancelled;
	
	
	
	public double getPercentTaskUnitsFinished() {
		return percentTaskUnitsFinished;
	}

	public void setPercentTaskUnitsFinished(double percentTaskUnitsFinished) {
		
		this.percentTaskUnitsFinished = percentTaskUnitsFinished;
	}

	public double getPercentTaskUnitsTobeFinished() {
		return percentTaskUnitsTobeFinished;
	}

	public void setPercentTaskUnitsTobeFinished(double percentTaskUnitsTobeFinished) {
		this.percentTaskUnitsTobeFinished = percentTaskUnitsTobeFinished;
	}

	public double getPercentTaskUnitsCancelled() {
		return percentTaskUnitsCancelled;
	}

	public void setPercentTaskUnitsCancelled(double percentTaskUnitsCancelled) {
		this.percentTaskUnitsCancelled = percentTaskUnitsCancelled;
	}



	public int getTaskUnitsTobeFinished() {
		return taskUnitsTobeFinished;
	}

	public void setTaskUnitsTobeFinished(int taskUnitsTobeFinished) {
		this.taskUnitsTobeFinished = taskUnitsTobeFinished;
	}

	public int getTaskUnitsFinished() {
		return taskUnitsFinished;
	}

	public void setTaskUnitsFinished(int taskUnitsFinished) {
		this.taskUnitsFinished = taskUnitsFinished;
	}

	public int getTaskUnitsAtPlanningPhase() {
		return taskUnitsAtPlanningPhase;
	}

	public void setTaskUnitsAtPlanningPhase(int taskUnitsAtPlanningPhase) {
		this.taskUnitsAtPlanningPhase = taskUnitsAtPlanningPhase;
	}

	public int getTaskUnitsCancelled() {
		return taskUnitsCancelled;
	}

	public void setTaskUnitsCancelled(int taskUnitsCancelled) {
		this.taskUnitsCancelled = taskUnitsCancelled;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;

	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
