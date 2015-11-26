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

	public Project getProjectById(Integer id) {
		return projectDao.getProjectById(id);
	}

	public void completeProjectById(int id) {
		projectDao.completeProjectById(id);
	}

	 public void cancelProjectById(Project project) {
	 projectDao.cancelProjectById(project);
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

	public String setProjectStatus(Date startDate) {
		String status;
		if (startDate.before(new Date())) {
			status = Constants.PROJECT_NEW;
		} else {
			status = Constants.PROJECT_PLANNING;
		}
		return status;
	}

	public List<Project> getProjectsForUser(int userId, String role) {
		return projectDao.getProjects(userId, role);

	}

}
