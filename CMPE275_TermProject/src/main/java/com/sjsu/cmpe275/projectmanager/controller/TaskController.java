package com.sjsu.cmpe275.projectmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sjsu.cmpe275.projectmanager.configuration.*;
import com.sjsu.cmpe275.projectmanager.model.*;
import com.sjsu.cmpe275.projectmanager.service.*;

@Controller
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
@RequestMapping("/project")
public class TaskController {
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService projectService;
	@Autowired
	UserService userService;

	@RequestMapping(value = { "/tasks/{projectId}" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Task> getTasks(@PathVariable("projectId") int projectId) {
		try {
			List<Task> taskList = taskService.getTasks(projectId);
			if (taskList != null) {
				return taskList;
			} else {
				return null;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/addTask/{projectId}", method = RequestMethod.GET)
	public String addTaskFormView(@PathVariable("projectId") int projectId, ModelMap model,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		List<User> users = projectService.getUsersList(projectId);
		model.addAttribute("users", users);
		model.addAttribute("projectId", projectId);
		model.addAttribute("addTaskForm", new Task());
		return "addTaskForm";
	}

	@RequestMapping(value = "/updateTask/{taskId}", method = RequestMethod.GET)
	public String addUpdateFormView(@PathVariable("taskId") int taskId, ModelMap model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		Task task = taskService.getTaskById(taskId);
		int projectId = task.getProject().getPid();
		List<User> users = projectService.getUsersList(projectId);
		model.addAttribute("users", users);
		model.addAttribute("taskId", taskId);
		model.addAttribute("updateTaskForm", taskService.getTaskById(taskId));
		return "updateTaskForm";
	}

	@RequestMapping(value = {
			"/task/create/{userId}/{projectId}" }, method = RequestMethod.POST, produces = "application/json")
	public String createTask(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId,
			@ModelAttribute("addTaskForm") Task task, ModelMap model, RedirectAttributes attributes,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		System.out.println("Estimated Time: " + task.getEstimated_time());
		try {
			Project project = projectService.getProjectById(projectId);
			if (project.getStatus().equals(Constants.PROJECT_NEW)
					|| project.getStatus().equals(Constants.PROJECT_PLANNING)
					|| project.getStatus().equals(Constants.PROJECT_ONGOING)) {
				User user_role = userService.getUser(userId);
				String username = user_role.getEmail();
				String userRole = userService.getUserRole(username);
				if ((project.getOwner().getUserId() == userId && userRole.equals(Constants.ROLE_ADMIN))) {
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						if (task.getEstimated_time() == null || task.getAssignee() == null) {
							attributes.addAttribute("error", "Estimated Time  and/or assignee are mandatory");
							return "redirect:/project/addTask/" + projectId;
						}
					}
					setTaskValues(project, projectId, userId, task);
				} else if (userService.getUserProjectStatus(userId, projectId).equals(Constants.INVITATION_ACCEPT)) {
					System.out.println(userService.getUserProjectStatus(userId, projectId));
					System.out.println("Inside If");
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						System.out.println("Inside project ongoing if");
						if (task.getEstimated_time() == null || task.getAssignee() == null) {
							attributes.addAttribute("error", "Estimated Time  and/or assignee are mandatory");
							return "redirect:/project/addTask/" + projectId;
						} else {
							setTaskValues(project, projectId, userId, task);
						}
					}
					else{
						setTaskValues(project, projectId, userId, task);
					}
				} else {
					attributes.addAttribute("error",
							"Error Adding Task. Either User is not the owner of project or user is not added to the project");
					return "redirect:/project/addTask/" + projectId;
				}
			} else {
				attributes.addAttribute("error",
						"Task cannot be added. Project should be in New, Planning or Ongoing state");
				return "redirect:/project/addTask/" + projectId;
			}
			if (taskService.createTask(task)) {
				return "redirect:/project/getProjectInfo/" + projectId;
			}
		} catch (RuntimeException e) {
			task = null;
			e.printStackTrace();
		}
		return null;
	}

	private void setTaskValues(Project project, int projectId, int userId, Task task) {
		project.setPid(projectId);
		User user = new User();
		user.setUserId(userId);
		task.setProject(project);
		if (task.getAssignee() == null)
			task.setTaskState(Constants.TASK_NEW);
		else
			task.setTaskState(Constants.TASK_ASSIGNED);
	}

	@RequestMapping(value = {
			"/task/update/{taskId}/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public String updateTask(@PathVariable("taskId") int taskId, @PathVariable("userId") int userId,
			@ModelAttribute("updateTaskForm") Task task, ModelMap model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		Task existingTask = null;
		try {
			existingTask = taskService.getTaskById(taskId);
			Project project = projectService.getProjectById(existingTask.getProject().getPid());
			existingTask.setTid(taskId);

			if (task.getTaskName() != null) {
				existingTask.setTaskName(task.getTaskName());
			}
			if (task.getDescription() != null) {
				existingTask.setDescription(task.getDescription());
			}
			if (task.getActual_time() != null) {
				existingTask.setActual_time(task.getActual_time());
			}
			// if (project.getStatus().equals(Constants.PROJECT_NEW) ||
			// project.getStatus().equals(Constants.PROJECT_PLANNING) ||
			// project.getStatus().equals(Constants.PROJECT_ONGOING)) {
			// if (task.getAssignee() != null) {
			// existingTask.setAssignee(task.getAssignee());
			// existingTask.setTaskState(Constants.TASK_ASSIGNED);
			// }
			// else
			// existingTask.setTaskState(Constants.TASK_NEW);
			// }

			if (project.getStatus().equals(Constants.PROJECT_NEW)
					|| project.getStatus().equals(Constants.PROJECT_PLANNING)
					|| project.getStatus().equals(Constants.PROJECT_ONGOING)) {
				if (task.getAssignee() != null) {
					existingTask.setAssignee(task.getAssignee());
					if (project.getStatus().equals(Constants.PROJECT_NEW)) {
						existingTask.setTaskState(Constants.TASK_ASSIGNED);
					}
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						//existingTask.setTaskState(Constants.TASK_STARTED);
					}
					if (project.getStatus().equals(Constants.PROJECT_PLANNING)) {
						existingTask.setTaskState(Constants.TASK_ASSIGNED);
					}

				} 
//				else{
//					existingTask.setTaskState(Constants.TASK_NEW);}
			}
			if (project.getStatus().equals(Constants.PROJECT_NEW)
					|| project.getStatus().equals(Constants.PROJECT_PLANNING)) {
				if (task.getEstimated_time() != null) {
					existingTask.setEstimated_time(task.getEstimated_time());
				}
			}
			if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
				if (task.getEstimated_time() != null) {
					if (task.getEstimated_time().intValue() != existingTask.getEstimated_time().intValue()) {
						model.addAttribute("error", "Estimated Time cannot be changed in project ongoing state");
						return "redirect:/project/updateTask/" + taskId;
					}
				}
			}
			if (taskService.updateTask(existingTask))
				return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		} catch (RuntimeException e) {
			task = null;
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = { "/task/finish/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public String finishTask(@PathVariable("taskId") int taskId, ModelMap model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());

		Task existingTask = taskService.getTaskById(taskId);
		// existingTask.setTaskState(Constants.TASK_STARTED);
		int projectId = existingTask.getProject().getPid();
		Project project = projectService.getProjectById(projectId);

		if (project.getStatus().equalsIgnoreCase(Constants.PROJECT_ONGOING)
				&& (!(existingTask.getTaskState().equals(Constants.TASK_CANCELLED))
						&& existingTask.getTaskState().equals(Constants.TASK_STARTED))) {
			if (existingTask.getActual_time() == null) {
				model.addAttribute("finishTaskError", "Please update Task to provide actual task units ");
				return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
			}
			existingTask.setTaskState(Constants.TASK_FINISHED);
		} else {
			model.addAttribute("error", "Task not started yet ");
			return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		}
		if (taskService.updateTask(existingTask))
			return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		model.addAttribute("error", "Task cannot be finished");
		return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
	}

	@RequestMapping(value = { "/task/start/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public String startTask(@PathVariable("taskId") int taskId, ModelMap model, HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());
		Task existingTask = taskService.getTaskById(taskId);
		// existingTask.setTaskState(Constants.TASK_STARTED);
		int projectId = existingTask.getProject().getPid();
		Project project = projectService.getProjectById(projectId);
		if (project.getStatus().equalsIgnoreCase(Constants.PROJECT_ONGOING)
				&& existingTask.getTaskState().equalsIgnoreCase(Constants.TASK_ASSIGNED)) {
			existingTask.setTaskState(Constants.TASK_STARTED);
		} else {
			model.addAttribute("error",
					"Task is not assigned/ Estimated Units missing/Project is not in ongoing state");
			return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		}
		if (taskService.updateTask(existingTask))
			return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		model.addAttribute("error", "Task cannot be started");
		return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
	}

	@RequestMapping(value = {
			"/task/cancel/{taskId}/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public String cancelTask(@PathVariable("taskId") int taskId, @PathVariable int userId, ModelMap model,
			HttpServletRequest request) {
		request.getSession().setAttribute("USER", getPrincipal());

		Task existingTask = taskService.getTaskById(taskId);
		// existingTask.setTaskState(Constants.TASK_STARTED);
		int projectId = existingTask.getProject().getPid();
		Project project = projectService.getProjectById(projectId);
		int ownerId = project.getOwner().getUserId();
		if (ownerId == userId) {
			if (!(project.getStatus().equalsIgnoreCase(Constants.PROJECT_CANCELLED))
					|| (project.getStatus().equalsIgnoreCase(Constants.PROJECT_COMPLETED))) {
				// mandatory
				if (!(existingTask.getTaskState().equalsIgnoreCase(Constants.TASK_FINISHED))) {
					existingTask.setTaskState(Constants.TASK_CANCELLED);
				}
			}
		}
		if (taskService.updateTask(existingTask))
			return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		model.addAttribute("error", "Task cannot be cancelled");
		return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
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
}