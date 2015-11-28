package com.sjsu.cmpe275.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = {
			"/task/create/{userId}/{projectId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task createTask(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId,
			@ModelAttribute Task task) {
		ModelAndView mv = new ModelAndView();
		try {

			Project project = projectService.getProjectById(projectId);
			if (project.getStatus().equals(Constants.PROJECT_NEW)
					|| project.getStatus().equals(Constants.PROJECT_PLANNING)
					|| project.getStatus().equals(Constants.PROJECT_ONGOING)) {

				User user_role = userService.getUser(userId);
				String username = user_role.getEmail();
				String userRole = userService.getUserRole(username);
				System.out.println("Username: " + username);
				System.out.println("UserRole: " + userRole);
				System.out.println(project.getOwner().getUserId() == userId);
				System.out.println(userRole.equals(Constants.ROLE_ADMIN));
				if ((project.getOwner().getUserId() == userId && userRole.equals(Constants.ROLE_ADMIN))) {
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						if (task.getEstimated_time() == null || task.getAssignee() == null)
							return null;
					}
					setTaskValues(project, projectId, userId, task);
				} else if (userService.getUserProjectStatus(userId, projectId).equals(Constants.INVITATION_ACCEPT)) {
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						if (task.getEstimated_time() == null || task.getAssignee() == null)
							return null;
					}
					setTaskValues(project, projectId, userId, task);
				} else {
					return null;
				}
			} else {
				return null;
			}

			mv.setViewName("createTask");

			if (taskService.createTask(task)) {
				mv.addObject("createTask", task);
				return task;
			}

		} catch (RuntimeException e) {
			task = null;
			mv.addObject("createTask", task);
			e.printStackTrace();
			return task;

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
	public @ResponseBody Task updateTask(@PathVariable("taskId") int taskId, @PathVariable("userId") int userId,
			@ModelAttribute Task task) {

		ModelAndView mv = new ModelAndView();
		Task existingTask = null;

		try {
			existingTask = taskService.getTaskById(taskId);
			Project project = projectService.getProjectById(existingTask.getProject().getPid());
			int ownerId = project.getOwner().getUserId();
			// User user = userService.getUser(userId);
			existingTask.setTid(taskId);
			if (task.getTaskState().equals(Constants.TASK_FINISHED)) {
				if (project.getStatus().equalsIgnoreCase(Constants.PROJECT_ONGOING)
						&& (!(existingTask.getTaskState().equals(Constants.TASK_CANCELLED))
								&& existingTask.getTaskState().equals(Constants.TASK_STARTED))) {
					// mandatory
					existingTask.setTaskName(task.getTaskName());
					// mandatory
					existingTask.setDescription(task.getDescription());
					// mandatory
					existingTask.setActual_time(task.getActual_time());
					// mandatory
					existingTask.setTaskState(Constants.TASK_FINISHED);

					// assignee and estimated task cannot be changed. Disable
					// from UI
					taskService.updateTask(existingTask);
				}
			} else if (task.getTaskState().equals(Constants.TASK_STARTED)) {
				if (project.getStatus().equalsIgnoreCase(Constants.PROJECT_ONGOING)
						&& existingTask.getTaskState().equalsIgnoreCase(Constants.TASK_ASSIGNED)) {
					// mandatory
					existingTask.setTaskName(task.getTaskName());
					// mandatory
					existingTask.setDescription(task.getDescription());

					// cannot be changed if in ongoing. Disable from UI
					// t.setEstimate_time(task.getEstimated_time());

					// can be changed
					// mandatory
					existingTask.setAssignee(task.getAssignee());
					// mandatory
					existingTask.setTaskState(Constants.TASK_STARTED);
					taskService.updateTask(existingTask);
				}

			} else if (task.getTaskState().equals(Constants.TASK_CANCELLED)) {
				if (ownerId == userId) {
					if (!(project.getStatus().equalsIgnoreCase(Constants.PROJECT_CANCELLED))
							|| (project.getStatus().equalsIgnoreCase(Constants.PROJECT_COMPLETED))) {
						// mandatory
						if (!(existingTask.getTaskState().equalsIgnoreCase(Constants.TASK_FINISHED))) {
							existingTask.setTaskState(Constants.TASK_CANCELLED);
							taskService.updateTask(existingTask);
						}
					}
				}

			}
			return existingTask;
		} catch (RuntimeException e) {
			task = null;
			mv.addObject("updateTask", existingTask);
			e.printStackTrace();
			return existingTask;
		}
	}

	@RequestMapping(value = { "/task/finish/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task finishTask(@PathVariable("taskId") int taskId, @ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_FINISHED);
		return updateTask(taskId, 0, task);
	}

	@RequestMapping(value = { "/task/start/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task startTask(@PathVariable("taskId") int taskId, @ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_STARTED);
		return updateTask(taskId, 0, task);
	}

	@RequestMapping(value = {
			"/task/cancel/{taskId}/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task cancelTask(@PathVariable("taskId") int taskId, @PathVariable int userId,
			@ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_CANCELLED);
		return updateTask(taskId, userId, task);
	}
}
