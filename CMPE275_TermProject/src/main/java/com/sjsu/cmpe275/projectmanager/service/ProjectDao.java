package com.sjsu.cmpe275.projectmanager.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sjsu.cmpe275.projectmanager.model.Project;

@Configuration
@Component
@Service
@Repository
public interface ProjectDao {

	public boolean createProject(Project project);

	public Project updateInvitationStatus(String status, int id,
			String recipientId, int projectId);

}
