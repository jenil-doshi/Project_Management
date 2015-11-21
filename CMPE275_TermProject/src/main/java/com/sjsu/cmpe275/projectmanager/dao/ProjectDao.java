package com.sjsu.cmpe275.projectmanager.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;

@Configuration
@Component
@Service
@Repository
public interface ProjectDao {

	public boolean createProject(Project project);
	public Project getProjectById(int Id);
	public Boolean deleteProject(Integer projectId);
	public boolean saveInvitationStatus(UserProjectInfo info);

}
