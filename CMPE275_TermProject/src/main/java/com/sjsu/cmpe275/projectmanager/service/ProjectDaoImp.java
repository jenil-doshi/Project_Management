package com.sjsu.cmpe275.projectmanager.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.model.Project;

@Configuration
@Repository("projectDao")
@Component
public class ProjectDaoImp implements ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public boolean createProject(Project project) {

		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(project);
			status = true;
			return status;
		} catch (Exception e) {

			throw new RuntimeException("A Runtime Exception Has occured");
		}

	}

	@Override
	public Project updateInvitationStatus(String status, int id,
			String recipientId, int projectId) {
		System.out.println("In DAO");
		return null;
	}
	
	@Override
	public Project getProjectById(int Id) {
		Project project = (Project) sessionFactory.getCurrentSession().get(Project.class, Id);
		return project;
	}

	@Transactional
	@Override
	public Boolean deleteProject(Integer projectId) {
		
		boolean status = false;
		try{
			sessionFactory.getCurrentSession().delete(getProjectById(projectId));
			status = true;
			return status;
		}
		catch(Exception e){
			throw new RuntimeException();
		}
	}
}
