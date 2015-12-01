package com.sjsu.cmpe275.projectmanager.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	//@Column(name = "StartDate")
	//private Date dbStartDate;
	//@Column(name = "EndDate")
	//private Date dbEndDate;

	//@Transient
	@Column(name = "StartDate")
	private Date startDate;

	//@Transient
	@Column(name = "EndDate")
	private Date endDate;

	// @JsonSerialize(using = FormatDate.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	public Date getStartDate() {
//		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
//		startDate = date.format(getDbStartDate());
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
//		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
//		try {
//			setDbStartDate(date.parse(this.startDate));
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
	}

//	public Date getDbStartDate() {
//		return dbStartDate;
//	}
//
//	public void setDbStartDate(Date dbStartDate) {
//		this.dbStartDate = dbStartDate;
//	}
//
//	public Date getDbEndDate() {
//		return dbEndDate;
//	}
//
//	public void setDbEndDate(Date dbEndDate) {
//		this.dbEndDate = dbEndDate;
//	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	public Date getEndDate() {
//		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
//		endDate = date.format(getDbEndDate());
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
//		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
//		try {
//			setDbEndDate(date.parse(this.endDate));
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
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
