package com.sjsu.cmpe275.projectmanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sjsu.cmpe275.projectmanager.configuration.CommonUtilites;
import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.configuration.EmailUtility;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.Task;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.service.ProjectService;
import com.sjsu.cmpe275.projectmanager.service.ReportService;
import com.sjsu.cmpe275.projectmanager.service.TaskService;
import com.sjsu.cmpe275.projectmanager.service.UserService;

@Controller
@ComponentScan({ "com.sjsu.cmpe275.projectmanager.aop", "com.sjsu.cmpe275.projectmanager.service" })
@RequestMapping("/project")
public class ProjectController {
	// test
	@Autowired
	EmailUtility utility;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	TaskService taskService;

	@Autowired
	private ReportService reportService;

	/**
	 * Method for fetching project add form
	 */

	@RequestMapping(value = "/getProfilePage", method = RequestMethod.GET)
	public String getProfilePage(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		// model.put("addProjectForm", new Project());
		return "userProfile";
	}

	@RequestMapping(value = "/addProjectFormView", method = RequestMethod.GET)
	public String addProjectFormView(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		model.put("addProjectForm", new Project());
		return "addProjectForm";
	}

	@RequestMapping(value = "/updateProjectFormView/{pid}", method = RequestMethod.GET)
	public String updateProjectFormView(Map<String, Object> model, @PathVariable int pid, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		model.put("updateProjectForm", projectService.getProjectById(pid));
		model.put("pid", pid);
		return "updateProjectForm";
	}

	@RequestMapping(value = { "/create/{userId}" }, headers = "Accept=*/*", method = RequestMethod.POST, produces = {
			"application/json" })
	public String createProject(@PathVariable int userId, @ModelAttribute("addProjectForm") Project project,
			RedirectAttributes att, HttpServletRequest request, RedirectAttributes attributes) {
		request.getSession().setAttribute("USER", getPrincipal());
		String role = null;
		User user = new User();

		try {

			role = CommonUtilites.getRole();

			user.setUserId(userId);
			project.setOwner(user);
			String status = projectService.getProjectStatus(project.getStartDate(), project.getEndDate());
			if (status.equalsIgnoreCase(Constants.PROJECT_COMPLETED)) {
				attributes.addAttribute("error", "End date has to be a future date.");
				return "redirect:/project/addProjectFormView";
			}
			project.setStatus(status);
			projectService.createProject(userId, project);
		} catch (RuntimeException e) {
			e.printStackTrace();
			att.addAttribute("error", "Project Creation Failed");
			return "redirect:/project/create/" + userId;

		}
		return "redirect:/project/viewProjects/" + user.getUserId() + "/" + role;
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

	@RequestMapping(value = { "/update/{userId}/{pid}" }, method = RequestMethod.POST, produces = "application/json")
	public String updateProject(@PathVariable int userId, @PathVariable int pid,
			@ModelAttribute("updateProjectForm") Project project, RedirectAttributes attributes,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		try {
			Project proj = projectService.getProjectById(pid);

			if (proj == null) {
				attributes.addAttribute("noProject", "Failed");
				return "redirect:/project/getProjectInfo/" + pid;
			}

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
						String status = projectService.getProjectStatus(project.getStartDate(), project.getEndDate());
						if (status.equalsIgnoreCase(Constants.PROJECT_COMPLETED)) {
							attributes.addAttribute("error", "End date has to be a future date.");
							return "redirect:/project/updateProjectFormView/" + pid;
						}
						proj.setStatus(status);
					}
					if (proj.getEndDate() != null) {
						proj.setEndDate(project.getEndDate());
					}

				}

				projectService.updateProject(proj);
				attributes.addAttribute("successfulUpdate", "Successfully updated project");
				return "redirect:/project/getProjectInfo/" + pid;
			}
			attributes.addAttribute("error", "Cannot update Project.Project is Cancelled or Completed");
			return "redirect:/project/getProjectInfo/" + pid;
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("ExceptionError", "CannotUpdate");
			return "redirect:/project/getProjectInfo/" + pid;
		}

	}

	@RequestMapping(value = {
			"/sendInvite/{uid}/{recipientId}/{projectId}/{projectName}/{projectOwner}" }, method = RequestMethod.GET)
	public String sendInvite(@PathVariable int uid, @PathVariable String recipientId, @PathVariable int projectId,
			@PathVariable String projectName, @PathVariable String projectOwner, RedirectAttributes attributes,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		try {
			if (utility.sendEmail(uid, recipientId, projectId, projectName, projectOwner)) {
				if (projectService.saveInvitationStatus(uid, projectId, Constants.INVITATION_PENDING)) {
					attributes.addFlashAttribute("status", "Invitation sent successfully");
					return "redirect:/project/getUsersListForAddProject/{projectOwner}/{projectId}/{projectName}/{projectOwner}";
				}
				attributes.addFlashAttribute("status", "Invitation sending Failed");
				return "redirect:/project/getUsersListForAddProject/{projectOwner}/{projectId}/{projectName}/{projectOwner}";
			} else {
				attributes.addFlashAttribute("status", "Invitation sending Failed");
				return "redirect:/project/getUsersListForAddProject/{projectOwner}/{projectId}/{projectName}/{projectOwner}";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			attributes.addFlashAttribute("status", "Invitation sending Failed");
			return "redirect:/project/getUsersListForAddProject/{projectOwner}/{projectId}/{projectName}/{projectOwner}";
		} catch (Exception e) {
			System.out.println("Error occured while sending email");
			e.printStackTrace();
			attributes.addFlashAttribute("status", "Invitation sending Failed");
			return "redirect:/project/getUsersListForAddProject/{projectOwner}/{projectId}/{projectName}/{projectOwner}";
		}

	}

	@RequestMapping(value = {
			"/invitationStatus/{status}/{uid}/{recipientId}/{projectId}" }, method = RequestMethod.GET)
	public ResponseEntity<String> inviteStatus(@PathVariable String status, @PathVariable String recipientId,
			@PathVariable int uid, @PathVariable int projectId, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
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

	@RequestMapping(value = "/start/{pid}/{userId}", method = RequestMethod.GET)

	public String startProject(@PathVariable int pid, @PathVariable int userId, RedirectAttributes attributes,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		try {
			Project projInfo = projectService.getProjectById(pid);
			if (projInfo.getOwner().getUserId() == userId) {
				if (projectService.getTasksForProject(pid)) {
					projInfo.setStatus(Constants.PROJECT_ONGOING);
					projectService.updateProject(projInfo);
					attributes.addAttribute("startProjSuccess", "Project Started");
					return "redirect:/project/getProjectInfo/" + pid;
				} else {
					attributes.addAttribute("startProjError",
							"Project cannot be started untill all the tasks have been assigned./ Estimated Units missing");
					return "redirect:/project/getProjectInfo/" + pid;
				}
			}
			attributes.addAttribute("accessDenied", "You do not have the permission to start the project.");
			return "redirect:/project/getProjectInfo/" + pid;
		} catch (RuntimeException e) {
			e.printStackTrace();
			attributes.addAttribute("startProjException", "Exception occured while starting the project");
			return "redirect:/project/getProjectInfo/" + pid;
		}
	}

	@RequestMapping(value = { "/complete/{projectId}/{userId}" }, method = RequestMethod.GET)
	public String completeProject(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId,
			RedirectAttributes attributes, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		try {
			Project p = projectService.getProjectById(projectId);
			if (p == null) {
				attributes.addAttribute("noProject", "No Project found");
				return "redirect:/project/getProjectInfo/" + projectId;
			}

			if (p.getStatus().equalsIgnoreCase(Constants.PROJECT_CANCELLED)) {
				attributes.addAttribute("completeProjError", "Project is already cancelled.");
				return "redirect:/project/getProjectInfo/" + projectId;
			}

			// if (p.getOwner().getUserId() == userId) {
			if (projectService.completeProjectById(projectId)) {
				attributes.addAttribute("completeProjSuccess", "Project Completed");
				return "redirect:/project/getProjectInfo/" + projectId;
				// p = projectService.getProjectById(projectId);
			}
			attributes.addAttribute("completeProjError", "Project cannot be completed until the tasks are finished");
			return "redirect:/project/getProjectInfo/" + projectId;

			// }
		} catch (Exception e) {

			attributes.addAttribute("completeProjException", "Error occured while completing the project");
			return "redirect:/project/getProjectInfo/" + projectId;

		}
	}

	@RequestMapping(value = { "/cancel/{projectId}/{userId}" }, method = RequestMethod.GET)
	public String cancelProject(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId,
			RedirectAttributes attributes, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		try {
			Project p = projectService.getProjectById(projectId);
			if (p == null) {
				attributes.addAttribute("noProject", "No Project found");
				return "redirect:/project/getProjectInfo/" + projectId;
			}

			if (p.getStatus().equalsIgnoreCase(Constants.PROJECT_COMPLETED)) {
				attributes.addAttribute("cancelProjError", "Project is already completed.");
				return "redirect:/project/getProjectInfo/" + projectId;
			}

			// if (p.getOwner().getUserId() == userId) {
			if (projectService.cancelProjectById(p)) {
				attributes.addAttribute("cancelProjSuccess", "Project cancelled");
				return "redirect:/project/getProjectInfo/" + projectId;
			}
			// }
			attributes.addAttribute("cancelProjError", "Project cannot be cancelled");
			return "redirect:/project/getProjectInfo/" + projectId;
		} catch (Exception e) {
			attributes.addAttribute("cancelProjException", "Error occured while completing the project");
			return "redirect:/project/getProjectInfo/" + projectId;
		}

	}

	@RequestMapping(value = "/getUsersListForTask/{pid}", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersListForTask(@PathVariable int pid, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
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
	public ModelAndView getUserProjects(@PathVariable int userId, @PathVariable String role,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
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

	@RequestMapping(value = "/getUsersListForAddProject/{username}/{pid}/{pname}/{owner}", method = RequestMethod.GET)
	public ModelAndView getUsersListForAddProject(@PathVariable String username, @PathVariable int pid,
			@PathVariable String pname, @PathVariable String owner, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		List<User> userList = null;
		ModelAndView model = new ModelAndView();
		try {
			userList = projectService.getUsersForAddProject(username, pid);
			// projectService.getInvitationStatusForUser(pid);
			model.setViewName("inviteesList");
			model.addObject("inviteesList", userList);
			model.addObject("pid", pid);
			model.addObject("pname", pname);
			model.addObject("owner", owner);

		} catch (RuntimeException e) {
			e.printStackTrace();
			return model;
		}

		return model;

	}

	@RequestMapping(value = "/getProjectInfo/{pid}", method = RequestMethod.GET)
	public ModelAndView getProjectInfo(@PathVariable int pid, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		ModelAndView model = new ModelAndView();
		Project project = null;
		List<Task> taskList = null;
		try {
			/*
			 * List<User> users = projectService.getUsersList(pid);
			 * model.addObject("users", users);
			 */

			project = projectService.getProjectById(pid);
			model.addObject("project", project);
			taskList = taskService.getTasks(pid);

			Map<Long, String> assigneeMap = new HashMap<Long, String>(taskList.size());

			for (Task task : taskList) {
				if (!(null == task.getAssignee())) {
					User user = userService.getUser(task.getAssignee());
					if (user != null) {
						task.setAssigneeName(user.getFirstName() + " " + user.getLastName());
					}
				}
			}

			model.addObject("assigneeMap", assigneeMap);
			model.addObject("taskList", taskList);
			model.setViewName("projectInfo");
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("project", project);
			model.addObject("taskList", taskList);
			model.setViewName("projectInfo");
		}

		return model;
	}

	@RequestMapping(value = "/report/{pid}", method = RequestMethod.GET)
	public @ResponseBody Project getReport(@PathVariable int pid) {

		return reportService.getReport(pid);
	}

	@RequestMapping(value = "getScoreCard/{pid}", method = RequestMethod.GET)
	public @ResponseBody List<Task> getScoreCard(@PathVariable("pid") int pid, HttpServletRequest request,
			ModelMap model) {
		request.getSession().setAttribute("USER", getPrincipal());
		List<Task> taskList = null;
		String grade = null;
		int assignee;
		try {
			int actualUnits;
			taskList = taskService.getTasks(pid);
			for (int i = 0; i < taskList.size(); i++) {
				if (taskList.get(i).getAssignee() == null) {
					taskList.get(i).setAssigneeName("");
				} else {
					assignee = taskList.get(i).getAssignee();
					User user1 = userService.getUser(assignee);
					taskList.get(i).setAssigneeName(user1.getFirstName() + " " + user1.getLastName());
				}

				int estimatedUnits = taskList.get(i).getEstimated_time().intValue();
				if (taskList.get(i).getActual_time() == null)
					actualUnits = 0;
				else
					actualUnits = taskList.get(i).getActual_time().intValue();
				int difference = estimatedUnits - actualUnits;
				taskList.get(i).setDifference(difference);
				if(taskList.get(i).getTaskState().equals(Constants.TASK_CANCELLED))
					grade = "Task Cancelled";
				else if (difference < 0)
					grade = "B";
				else
					grade = "A";

				taskList.get(i).setGrade(grade);
			}
			// model.addAttribute("TaskList", taskList);
			return taskList;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
}
