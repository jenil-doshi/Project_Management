package com.sjsu.cmpe275.projectmanager.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public @ResponseBody
	List<Task> getTasks(@PathVariable("projectId") int projectId) {
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
	public String addTaskFormView(@PathVariable("projectId") int projectId,
			ModelMap model) {
		model.addAttribute("projectId", projectId);
		model.addAttribute("addTaskForm", new Task());
		return "addTaskForm";
	}

	@RequestMapping(value = "/updateTask/{taskId}", method = RequestMethod.GET)
	public String addUpdateFormView(@PathVariable("taskId") int taskId,
			ModelMap model) {
		model.addAttribute("taskId", taskId);
		model.addAttribute("updateTaskForm", taskService.getTaskById(taskId));
		return "updateTaskForm";
	}

	@RequestMapping(value = { "/task/create/{userId}/{projectId}" }, method = RequestMethod.POST, produces = "application/json")
	public String createTask(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId,
			@ModelAttribute("addTaskForm") Task task, ModelMap model) {
		ModelAndView mv = new ModelAndView();
		System.out.println("Estimated Time: " + task.getEstimated_time());
		try {

			Project project = projectService.getProjectById(projectId);
			if (project.getStatus().equals(Constants.PROJECT_NEW)
					|| project.getStatus().equals(Constants.PROJECT_PLANNING)
					|| project.getStatus().equals(Constants.PROJECT_ONGOING)) {

				User user_role = userService.getUser(userId);
				String username = user_role.getEmail();
				String userRole = userService.getUserRole(username);
				if ((project.getOwner().getUserId() == userId && userRole
						.equals(Constants.ROLE_ADMIN))) {
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						if (task.getEstimated_time() == null
								|| task.getAssignee() == null)
							return null;
					}
					setTaskValues(project, projectId, userId, task);
				} else if (userService.getUserProjectStatus(userId, projectId)
						.equals(Constants.INVITATION_ACCEPT)) {
					if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
						if (task.getEstimated_time() == null
								|| task.getAssignee() == null)
							return null;
					}
					setTaskValues(project, projectId, userId, task);
				} else {
					return null;
				}
			} else {
				return null;
			}

			if (taskService.createTask(task)) {
				/*
				 * Task createdTask = taskService.getTaskById(task.getTid());
				 * 
				 * model.addAttribute("task", createdTask);
				 */
				return "redirect:/project/getProjectInfo/" + projectId;
			}
		} catch (RuntimeException e) {
			task = null;
			e.printStackTrace();
		}
		return null;
	}

	private void setTaskValues(Project project, int projectId, int userId,
			Task task) {
		project.setPid(projectId);
		User user = new User();
		user.setUserId(userId);
		task.setProject(project);

		if (task.getAssignee() == null)
			task.setTaskState(Constants.TASK_NEW);
		else
			task.setTaskState(Constants.TASK_ASSIGNED);
	}

	@RequestMapping(value = { "/task/update/{taskId}/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public String updateTask(@PathVariable("taskId") int taskId,
			@PathVariable("userId") int userId, @ModelAttribute("updateTaskForm") Task task,
			ModelMap model) {

		Task existingTask = null;
		Task updatedTask = null;
		try {
			existingTask = taskService.getTaskById(taskId);
			Project project = projectService.getProjectById(existingTask
					.getProject().getPid());
			int ownerId = project.getOwner().getUserId();
			// User user = userService.getUser(userId);
			existingTask.setTid(taskId);
			String taskState = task.getTaskState();
			if (taskState == null) {
				System.out.println("Inside Task state null if");
				if (task.getTaskName() != null) {
					existingTask.setTaskName(task.getTaskName());
				}
				if (task.getDescription() != null) {
					existingTask.setDescription(task.getDescription());
				}
				if (task.getActual_time() != null) {
					existingTask.setActual_time(task.getActual_time());
				}
				if (project.getStatus().equals(Constants.PROJECT_PLANNING)) {
					System.out.println("Est time: " + task.getEstimated_time());
					if (task.getEstimated_time() != null) {
						existingTask.setEstimated_time(task.getEstimated_time());
					}
				}
				if (project.getStatus().equals(Constants.PROJECT_ONGOING)) {
					if (task.getEstimated_time() != null) {
						existingTask.setEstimated_time(task.getEstimated_time());
					}
				}
				taskService.updateTask(existingTask);
			} else if (task.getTaskState().equals(Constants.TASK_FINISHED)) {
				if (project.getStatus().equalsIgnoreCase(
						Constants.PROJECT_ONGOING)
						&& (!(existingTask.getTaskState()
								.equals(Constants.TASK_CANCELLED)) && existingTask
								.getTaskState().equals(Constants.TASK_STARTED))) {
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
				if (project.getStatus().equalsIgnoreCase(
						Constants.PROJECT_ONGOING)
						&& existingTask.getTaskState().equalsIgnoreCase(
								Constants.TASK_ASSIGNED)) {
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
					if (!(project.getStatus()
							.equalsIgnoreCase(Constants.PROJECT_CANCELLED))
							|| (project.getStatus()
									.equalsIgnoreCase(Constants.PROJECT_COMPLETED))) {
						// mandatory
						if (!(existingTask.getTaskState()
								.equalsIgnoreCase(Constants.TASK_FINISHED))) {
							existingTask.setTaskState(Constants.TASK_CANCELLED);
							taskService.updateTask(existingTask);
						}
					}
				}
			}
			/*updatedTask = taskService.getTaskById(taskId);
			System.out.println("task name: " + updatedTask.getTaskName());
			model.addAttribute("updatedTask", updatedTask);
			return "updateTask";*/
			if(taskService.updateTask(existingTask))
				return "redirect:/project/getProjectInfo/" + existingTask.getProject().getPid();
		} catch (RuntimeException e) {
			task = null;
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = { "/task/finish/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public String finishTask(@PathVariable("taskId") int taskId,
			@ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_FINISHED);
		return updateTask(taskId, 0, task, null);
	}

	@RequestMapping(value = { "/task/start/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public String startTask(@PathVariable("taskId") int taskId,
			@ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_STARTED);
		return updateTask(taskId, 0, task, null);
	}

	@RequestMapping(value = { "/task/cancel/{taskId}/{userId}" }, method = RequestMethod.POST, produces = "application/json")
	public String cancelTask(@PathVariable("taskId") int taskId,
			@PathVariable int userId, @ModelAttribute Task task) {
		task.setTaskState(Constants.TASK_CANCELLED);
		return updateTask(taskId, userId, task, null);
	}
}
