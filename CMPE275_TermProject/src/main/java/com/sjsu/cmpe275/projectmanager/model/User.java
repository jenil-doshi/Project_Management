package com.sjsu.cmpe275.projectmanager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * THIS CLASS WILL NOT BE USED BY SPRING SECURITY
 * @author rainashastri
 *
 */
@Entity
@Table(name = "User")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UID")
	private int userId;

	@Column(name = "FirstName")
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name="Email")
	private String email;

	@Column(name="Password")
	private String password;
	
	/*@JoinColumn(name="role",referencedColumnName="role")
	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}

	@JoinColumn(name="role")
	private UserRoles role;
	*/
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/*@Column(name="enabled")
	private int enabled;

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}*/
}
