package com.sjsu.cmpe275.projectmanager.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.configuration.EmailUtility;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.service.ProjectService;
import com.sjsu.cmpe275.projectmanager.service.UserService;

@Controller
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
@RequestMapping("/project")
public class ProjectController {
	// test
	@Autowired
	EmailUtility utility;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	/**
	 * Method for fetching project add form
	 */

	@RequestMapping(value = "/addProjectFormView", method = RequestMethod.GET)
	public String addProjectFormView(Map<String, Object> model) {
		model.put("addProjectForm", new Project());
		return "addProjectForm";
	}

	@RequestMapping(value = { "/create/{userId}" }, headers = "Accept=*/*", method = RequestMethod.POST, produces = {
			"application/json" })
	public String createProject(@PathVariable int userId, @ModelAttribute("addProjectForm") Project project) {
		String role=null;
		User user = new User();
		
		try {
			
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
					  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(GrantedAuthority authority:authorities){
				if(authority.getAuthority().equalsIgnoreCase(Constants.ROLE_ADMIN)){
					role = "role_admin";
					break;
				}else if(authority.getAuthority().equalsIgnoreCase(Constants.ROLE_USER)){
					role = "role_user";
					break;
				}
			}
			
			user.setUserId(userId);
			project.setOwner(user);
			// to change from new to planning... handle the control from ui by
			// invoking update state service every once a day.
			project.setStatus(projectService.setProjectStatus(project.getStartDate()));
			projectService.createProject(userId, project);
		} catch (RuntimeException e) {
			project = null;
			e.printStackTrace();
			//return project;

		}
		return "redirect:/project/viewProjects/" + user.getUserId()+"/"+role;
	}

	public User getPrincipal() {
		String userName = null;
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		user = userService.getUserByUserName(userName);

		return user;
	}

	// update Project
	@RequestMapping(value = { "/update/{userId}/{pid}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<Project> updateProject(@PathVariable int userId, @PathVariable int pid,
			@ModelAttribute Project project) {
		ModelAndView mv = new ModelAndView();

		Project proj = projectService.getProjectById(pid);

		if (proj == null)
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);

		if (!(proj.getStatus().equalsIgnoreCase(Constants.PROJECT_CANCELLED)
				|| proj.getStatus().equalsIgnoreCase(Constants.PROJECT_COMPLETED))) {

			// status and owner mandatory from ui
			if (proj.getOwner().getUserId() == userId) {
				if (proj.getName() != null) {
					proj.setName(project.getName());
				}

				if (proj.getDescription() != null) {
					proj.setDescription(project.getDescription());
				}

			}

			if (proj.getStatus().equals(Constants.PROJECT_NEW)) {
				if (proj.getStartDate() != null) {
					proj.setStartDate(project.getStartDate());
				}
				if (proj.getEndDate() != null) {
					proj.setStartDate(project.getEndDate());
				}

			}

			projectService.updateProject(proj);
		}
		return new ResponseEntity<Project>(proj, HttpStatus.OK);

	}

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
				if (status.equalsIgnoreCase(Constants.INVITATION_ACCEPT)) {
					UserRoles roles = new UserRoles();
					roles.setUsername(recipientId);
					roles.setRole(Constants.ROLE_USER);
					userService.updateUserRole(roles);
				}
				return new ResponseEntity<String>("Success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/start/{pid}/{userId}", method = RequestMethod.POST)

	public @ResponseBody ResponseEntity<String> startProject(@PathVariable int pid, @PathVariable int userId) {
		try {
			Project projInfo = projectService.getProjectById(pid);
			if (projInfo.getOwner().getUserId() == userId) {
				if (projectService.getTasksForProject(pid)) {
					return new ResponseEntity<String>("Can Start Project", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Cannot Start Project", HttpStatus.OK);
				}
			}
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/complete/{userId}/{projectId}" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Project> completeProject(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId) {
		Project p = projectService.getProjectById(projectId);
		if (p == null)
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);

		if (p.getOwner().getUserId() == userId) {
			if (projectService.completeProjectById(projectId)) {
				p = projectService.getProjectById(projectId);
			}
		} else {
			System.out.println("Project Can only be deleted by Owner");
		}

		return new ResponseEntity<Project>(p, HttpStatus.OK);
	}

	@RequestMapping(value = { "/cancel/{userId}/{projectId}" }, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Project> cancelProject(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId) {
		Project p = projectService.getProjectById(projectId);
		if (p == null)
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);

		if (p.getOwner().getUserId() == userId) {
			projectService.cancelProjectById(p);
		} else {
			System.out.println("Project Can only be deleted by Owner");
		}

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

	@RequestMapping(value = "/viewProjects/{userId}/{role}", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView getUserProjects(@PathVariable int userId, @PathVariable String role) {
		List<Project> projectList = null;
		ModelAndView modelAndView = new ModelAndView();
		try {
			projectList = projectService.getProjectsForUser(userId, role);

			modelAndView.setViewName("viewProjects");
			modelAndView.addObject("projectList", projectList);

		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("viewProjects");
			modelAndView.addObject("projectList", null);

		}
		return modelAndView;

	}

}
