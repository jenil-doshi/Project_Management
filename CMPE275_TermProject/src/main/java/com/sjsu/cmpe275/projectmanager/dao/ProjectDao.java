package com.sjsu.cmpe275.projectmanager.dao;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;

@Configuration
@Component
@Service
@Repository
public interface ProjectDao {

	public boolean createProject(Project project);
	public boolean updateProject(Project project);
	public Project getProjectById(int Id);
	public boolean completeProjectById(int projectId);
	public boolean cancelProjectById(Project project);
	public boolean saveInvitationStatus(UserProjectInfo info);
	public boolean getTasksForProject(int pid);
	public List<User> getUsersList(int pid);
	public List<Project> getProjects(int userId, String role);
	public List<User> getUsersForAddProject(String username,int pid);
	

}
