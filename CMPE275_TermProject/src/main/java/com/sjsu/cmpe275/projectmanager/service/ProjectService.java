package com.sjsu.cmpe275.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.model.Project;
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

	public void deleteProjectById(Integer id) {
		projectDao.deleteProject(id);
	}

	public boolean saveInvitationStatus(int uid, int projectId, String status) {
		UserProjectInfo info = new UserProjectInfo();
		info.setUid(uid);
		info.setPid(projectId);
		info.setAcceptanceStatus(status);
		return projectDao.saveInvitationStatus(info);

	}

}
