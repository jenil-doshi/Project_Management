package com.sjsu.cmpe275.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.configuration.EmailUtility;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.service.ProjectService;

@Controller
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
@RequestMapping("/project")
public class ProjectController {
	// test
	@Autowired
	EmailUtility utility;

	@Autowired
	ProjectService projectService;

	@RequestMapping(value = { "/create/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Project createProject(@PathVariable int userId, @ModelAttribute Project project) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = new User();
			user.setUserId(userId);
			project.setOwner(user);
			project.setStatus(Constants.PLANNING);
			mv.setViewName("project");

			if (projectService.createProject(userId, project)) {
				mv.addObject("project", project);
				return project;
			}

		} catch (RuntimeException e) {
			project = null;
			mv.addObject("project", project);
			e.printStackTrace();
			return project;

		}
		return null;
	}

	// update Project

	@RequestMapping(value = {
			"/sendInvite/{uid}/{recipientId}/{projectId}/{projectName}/{projectOwner}" }, method = RequestMethod.POST)
	public ResponseEntity<String> sendInvite(@PathVariable int uid, @PathVariable String recipientId,
			@PathVariable int projectId, @PathVariable String projectName, @PathVariable String projectOwner) {

		try {
			if (utility.sendEmail(uid, recipientId, projectId, projectName, projectOwner)) {
				if (projectService.saveInvitationStatus(uid, projectId, Constants.INVITATION_PENDING)) {
					return new ResponseEntity<String>("Success", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Fail", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Fail", HttpStatus.OK);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		} catch (Exception e) {

			System.out.println("Error occured while sending email");
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}

	}

	@RequestMapping(value = {
			"/invitationStatus/{status}/{uid}/{recipientId}/{projectId}" }, method = RequestMethod.GET)
	public ResponseEntity<String> inviteStatus(@PathVariable String status, @PathVariable String recipientId,
			@PathVariable int uid, @PathVariable int projectId) {

		try {
			if (status.equalsIgnoreCase(Constants.ACCEPT)) {
				status = Constants.INVITATION_ACCEPT;
			} else if (status.equalsIgnoreCase(Constants.REJECT)) {
				status = Constants.INVITATION_REJECT;
			}
			if (projectService.saveInvitationStatus(uid, projectId, status)) {
				return new ResponseEntity<String>("Success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/start/{pid}", method = RequestMethod.POST)
	// no need to check if uid is owner or not coz, UI will handle that using
	// spring security roles
	public @ResponseBody ResponseEntity<String> startProject(@PathVariable int pid) {
		try {
			if (projectService.getTasksForProject(pid)) {
				return new ResponseEntity<String>("Can Start Project", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Cannot Start Project", HttpStatus.OK);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}
	}

	// no need to check if uid is owner or not coz, UI will handle that using
	// spring security roles
	@RequestMapping(value = { "/delete/{userId}/{projectId}" }, method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	public @ResponseBody ResponseEntity<Project> deleteProject(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId) {
		Project p = projectService.getProjectById(projectId);
		if (p == null)
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);

		projectService.deleteProjectById(projectId);

		/*
		 * if(p.getOwner().getUserId() == userId){
		 * projectService.deleteProjectById(projectId); } else{
		 * System.out.println("Project Can only be deleted by Owner"); }
		 */

		return new ResponseEntity<Project>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUsersListForTask/{pid}", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersListForTask(@PathVariable int pid) {

		try {
			List<User> userList = projectService.getUsersList(pid);
			if (userList != null) {
				return userList;
			} else {
				return null;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}

	}
}
