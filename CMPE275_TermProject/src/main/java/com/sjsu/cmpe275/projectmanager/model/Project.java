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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect
@Entity
@Table(name = "Project")
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

	//@JsonSerialize(using = FormatDate.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
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
