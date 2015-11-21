package com.sjsu.cmpe275.projectmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Project_Info")
public class UserProjectInfo implements Serializable {

	// @Col
	// private int id;
	@Id
	@Column(name = "PID")
	private int pid;
	@Id
	@Column(name = "UID")
	private int uid;
	@Column(name = "AcceptanceStatus")
	private String acceptanceStatus;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getAcceptanceStatus() {
		return acceptanceStatus;
	}

	public void setAcceptanceStatus(String acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}

}
