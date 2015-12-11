package com.sjsu.cmpe275.projectmanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.dao.ProjectDao;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;


@Service
@Transactional
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
public class ProjectService {

	@Autowired
	ProjectDao projectDao;

	public boolean createProject(int userId, Project project) throws RuntimeException {
		return projectDao.createProject(project);
	}

	public boolean updateProject(Project project) throws RuntimeException {
		return projectDao.updateProject(project);
	}

	public Project getProjectById(Integer id) {
		return projectDao.getProjectById(id);
	}

	public boolean completeProjectById(int id) {
		return projectDao.completeProjectById(id);
	}

	public boolean cancelProjectById(Project project) {
		return projectDao.cancelProjectById(project);
	}

	public boolean saveInvitationStatus(int uid, int projectId, String status) {
		UserProjectInfo info = new UserProjectInfo();
		info.setUid(uid);
		info.setPid(projectId);
		info.setAcceptanceStatus(status);
		return projectDao.saveInvitationStatus(info);

	}

	public boolean getTasksForProject(int pid) {
		return projectDao.getTasksForProject(pid);

	}

	public List<User> getUsersList(int pid) {

		return projectDao.getUsersList(pid);

	}

	public String getProjectStatus(Date startDate, Date endDate) {
		String status;

		if (endDate.equals(new Date()) || endDate.before(new Date())) {
			return Constants.PROJECT_COMPLETED;
		}
		if (startDate.before(new Date())) {
			status = Constants.PROJECT_PLANNING;
		} else {
			status = Constants.PROJECT_NEW;
		}
		return status;
	}

	public List<Project> getProjectsForUser(int userId, String role) {
		return projectDao.getProjects(userId, role);

	}

	public List<User> getUsersForAddProject(String username, int pid) {
		return projectDao.getUsersForAddProject(username, pid);
	}
	//
	// public List<User> getInvitationStatusForUser(int pid) {
	//
	// return projectDao.getInvitationStatusForUser(pid);
	//
	// }

}
