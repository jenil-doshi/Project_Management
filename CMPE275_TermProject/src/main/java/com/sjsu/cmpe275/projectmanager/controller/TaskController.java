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
	public @ResponseBody List<Task> getTasks(
			@PathVariable("projectId") int projectId) {
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
	
	@RequestMapping(value = { "/task/create/{userId}/{projectId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task createTask(
			@PathVariable("userId") int userId, 
			@PathVariable("projectId") int projectId, 
			@ModelAttribute Task task) {
		ModelAndView mv = new ModelAndView();
		try {
			
			Project project = projectService.getProjectById(projectId);
			if(project.getStatus().equals(Constants.PROJECT_NEW) ||
					project.getStatus().equals(Constants.PROJECT_PLANNING)){
				if(userService.getUserProjectStatus(userId, projectId).equals(Constants.INVITATION_ACCEPT)){
					project.setPid(projectId);	
					User user = new User();
					user.setUserId(userId);
					task.setProject(project);
					
					if(task.getAssignee() == null)
						task.setTaskState(Constants.TASK_NEW);
					else
						task.setTaskState(Constants.TASK_ASSIGNED);
				}
			}
			else{
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
	
	@RequestMapping(value = { "/task/update/{taskId}" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Task updateTask(
			@PathVariable("taskId") int taskId,
			@ModelAttribute Task task) {
		
		ModelAndView mv = new ModelAndView();
		Task task1 = null;
		
		try {
			task1 = taskService.getTaskById(taskId);
			Project project = projectService.getProjectById(task1.getProject().getPid());
			int userId = project.getOwner().getUserId();
			User user = userService.getUser(userId);
			
			if(task.getTaskName() != null){
				task1.setTaskName(task.getTaskName());
			}
			if(task.getDescription() != null){
				task1.setDescription(task.getDescription());
			}
			if(task.getActual_time() != null){
				task1.setActual_time(task.getActual_time());
			}
			if(project.getStatus().equals(Constants.PROJECT_NEW) ||
					project.getStatus().equals(Constants.PROJECT_PLANNING)){
				if(task.getEstimated_time() != null){
					task1.setEstimate_time(task.getEstimated_time());
				}
			}
			if(project.getStatus().equals(Constants.PROJECT_NEW) ||
					project.getStatus().equals(Constants.PROJECT_PLANNING) ||
					project.getStatus().equals(Constants.PROJECT_ONGOING)){
				if(!(task1.getTaskState().equals(Constants.TASK_CANCELLED))){
					user.setUserId(userId);
					task1.setAssignee(userId);
				}
			}
			
			mv.setViewName("updateTask");
			if (taskService.updateTask(task1)) {
				mv.addObject("updateTask", task1);
				return task1;
			}
		} catch (RuntimeException e) {
			task = null;
			mv.addObject("updateTask", task1);
			e.printStackTrace();
			return task1;
		}
		return null;
	}
}
