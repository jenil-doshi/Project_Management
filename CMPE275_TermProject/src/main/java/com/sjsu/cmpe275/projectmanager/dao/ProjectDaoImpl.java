package com.sjsu.cmpe275.projectmanager.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.configuration.Queries;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.Task;
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

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public boolean completeProjectById(int projectId) {

		boolean status = false;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_PROJECT_TASK_LIST);
			query.setParameter("projetId", projectId);
			List<String> statusList = query.list();
			if (statusList != null) {
				for (String statList : statusList) {
					if (statList.equals(Constants.TASK_FINISHED) || statList.equals(Constants.TASK_CANCELLED)) {
						sessionFactory.getCurrentSession().delete(getProjectById(projectId));
						status = true;
						return status;
					}
				}
			}
			return status;

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	
/////////////////////////////////Project_Cancel////////////////////////////////////////
	/* @SuppressWarnings("unchecked")
	@Transactional
	@Override
	public boolean cancelProjectById(int projectId) {

		boolean status = false;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_PROJECT_TASK_LIST);
			query.setParameter("projetId", projectId);
			List<String> statusList = query.list();
			if (statusList != null) {
				for (String statList : statusList) {

					if (statList.equals(Constants.PROJECT_NEW) || statList.equals(Constants.PROJECT_PLANNING) || statList.equals(Constants.PROJECT_ONGOING))	
//						sessionFactory.getCurrentSession().delete(getProjectById(projectId));
						sessionFactory.getCurrentSession().delete(getProjectById(projectId));

						status = true;
						return status;
					}
				}
			  
			
			return status;

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}*/
	 /////////////////////////////////////////////////////////////////////////////////////////////
	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean getTasksForProject(int pid) {
		boolean startProject = true;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASKS_FOR_PROJECT);
			Project project = getProjectById(pid);
			query.setParameter("project", project);
			List<Task> tasksList = query.list();
			if (tasksList != null) {
				for (Task task : tasksList) {
					if (!(task.getTaskState().equalsIgnoreCase(Constants.TASK_ASSIGNED))
							|| task.getEstimated_time() == 0) {
						startProject = false;
						break;
					}
				}
			} else {
				startProject = false;
				System.out.println("No Tasks present in project");
			}

		} catch (Exception e) {
			startProject = false;
			throw new RuntimeException("A Runtime exception has occured");
		}
		return startProject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersList(int pid) {
		List<User> usersList = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_USERS_PROJECT_INFO);
			query.setParameter("pid", pid);
			query.setParameter("accepted", Constants.INVITATION_ACCEPT);
			List<Integer> userIdList = query.list();
			// List uIdsList = Arrays.asList(userIdList);
			Query userQuery = sessionFactory.getCurrentSession().createQuery(Queries.GET_USERS_LIST)
					.setParameterList("userIdList", userIdList);
			usersList = userQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception has occurred");
		}
		return usersList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjects(int userId, String role) {
		List<Project> projList = null;
		Query query = null;
		try {
			String sql = null;
			if (role.equalsIgnoreCase(Constants.ROLE_USER)) {
				sql = Queries.GET_PID_FORM_USER_PROJECT_INFO;
				query = sessionFactory.getCurrentSession().createQuery(sql).setParameter("userId", userId);
			} else {
				sql = Queries.GET_PID_FROM_PROJECT_FOR_USER;
				query = sessionFactory.getCurrentSession().createQuery(sql).setParameter("userId", userId);
			}
			List<Integer> projIdList = query.list();
			if (projIdList != null) {
				Query queryList = sessionFactory.getCurrentSession()
						.createQuery("from Project where pid in (:projIdList)")
						.setParameterList("projIdList", projIdList);
				projList = queryList.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime Exception has occured.");
		}

		return projList;
	}

}
