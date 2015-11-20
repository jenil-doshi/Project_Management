package com.sjsu.cmpe275.projectmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	@Autowired
	EmailUtility utility;

	@Autowired
	ProjectService projectService;

	@RequestMapping(value = { "/create/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<Project> createProject(@PathVariable int userId,
			@ModelAttribute Project project) {
		// Project proj = null;
		ModelAndView mv = new ModelAndView();
		try {
			// proj = new Project();

			// proj.setPid(1);
			// proj.setName("Sample Project");
			// proj.setDescription("Project Description");
			// proj.setStatus(Constants.PLANNING);
			User user = new User();
			user.setUserId(userId);
			project.setOwner(user);
			project.setStatus(Constants.PLANNING);
			mv.setViewName("project");

			if (projectService.createProject(userId, project)) {
				mv.addObject("project", project);
				return new ResponseEntity<Project>(project, HttpStatus.OK);
			}

		} catch (RuntimeException e) {
			mv.addObject("project", null);
			e.printStackTrace();
			return new ResponseEntity<Project>(project, HttpStatus.OK);

		}
		return null;
	}

	// update Project

	@RequestMapping(value = {
			"/sendInvite/{id}/{recipientId}/{projectId}/{projectName}/{projectOwner}" }, method = RequestMethod.POST)
	public String sendInvite(@PathVariable int id, @PathVariable String recipientId, @PathVariable int projectId,
			@PathVariable String projectName, @PathVariable String projectOwner) {

		if (utility.sendEmail(id, recipientId, projectId, projectName, projectOwner)) {
			return "Success";
		} else {
			return "Fail";
		}

	}

	@RequestMapping(value = {
			"/invitationStatus/{status}/{id}/{recipientId}/{projectId}" }, method = RequestMethod.POST)
	public void inviteStatus(@PathVariable String status, @PathVariable String recipientId, @PathVariable int id,
			@PathVariable int projectId) {

		projectService.updateInvitationStatus(status, id, recipientId, projectId);

	}

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
}
