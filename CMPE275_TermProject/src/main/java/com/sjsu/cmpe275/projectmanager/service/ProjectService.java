package com.sjsu.cmpe275.projectmanager.service;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.sjsu.cmpe275.projectmanager.model.Project;

@Service
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
public class ProjectService {

	@Autowired
	ProjectDao projectDao;

	public void updateInvitationStatus(String status, int id,
			String recipientId, int projectId) {

		System.out.println("in serveice");

		projectDao.updateInvitationStatus(status, id, recipientId, projectId);

	}

	public boolean createProject(int userId, Project project)
			throws RuntimeException {

		return projectDao.createProject(project);

	}

}
