package com.sjsu.cmpe275.projectmanager.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserProjectInfo;

@Configuration
@Repository("projectDao")
@Component
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public boolean createProject(Project project) {

		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(project);
			User owner = (User) sessionFactory.getCurrentSession().get(User.class, project.getOwner().getUserId());
			project.setOwner(owner);
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occured");
		}

	}

	@Override
	public boolean saveInvitationStatus(UserProjectInfo info) {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(info);
			status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception Has occured");
		}

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
		try {
			sessionFactory.getCurrentSession().delete(getProjectById(projectId));
			status = true;
			return status;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
