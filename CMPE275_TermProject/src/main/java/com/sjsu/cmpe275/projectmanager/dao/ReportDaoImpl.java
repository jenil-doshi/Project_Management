package com.sjsu.cmpe275.projectmanager.dao;

import java.util.List;

import org.hibernate.Query;
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

@Configuration
@Repository("reportDao")
@Component
public class ReportDaoImpl implements ReportDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	TaskDao taskDao;

	@Autowired
	ProjectDao projectDao;

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public int getTaskUnitsFinished(int pid) {
		int actualTaskUnitsSum = 0;
		int actualTime;
		try {
			Project project = projectDao.getProjectById(pid);
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASKS_FOR_PROJECT)
					.setParameter("project", project);
			List<Task> tasksList = query.list();
			if (!tasksList.isEmpty() && tasksList != null) {
				for (Task task : tasksList) {
					 
					if(task.getActual_time()==null){
						actualTime = 0;
					}else{
						actualTime = task.getActual_time();
					}
					actualTaskUnitsSum += actualTime;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime exception has occurred");
		}
		return actualTaskUnitsSum;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public int taskUnitsTobeFinished(int pid) {
		int estimatedUnitsSum = 0;
		try {
			Project project = projectDao.getProjectById(pid);
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASKS_FOR_PROJECT)
					.setParameter("project", project);
			List<Task> tasksList = query.list();
			if (!tasksList.isEmpty() && tasksList != null) {
				for (Task task : tasksList) {
					if ((task.getActual_time() == null||task.getActual_time() == 0)&&(!task.getTaskState().equalsIgnoreCase(Constants.TASK_CANCELLED))) {
						estimatedUnitsSum += task.getEstimated_time();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime exception has occurred");
		}
		return estimatedUnitsSum;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public int getTaskUnitsCancelled(int pid) {
		int taskUnitsCancelled = 0;
		try {
			Project project = projectDao.getProjectById(pid);
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASKS_FOR_PROJECT)
					.setParameter("project", project);
			List<Task> tasksList = query.list();
			if (!tasksList.isEmpty() && tasksList != null) {
				for (Task task : tasksList) {
					if (task.getTaskState().equalsIgnoreCase(Constants.PROJECT_CANCELLED)) {
						taskUnitsCancelled += task.getEstimated_time();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime exception has occurred");
		}
		return taskUnitsCancelled;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public int getTaskUnitsAtPlanningPhase(int pid) {
		int taskUnitsAtPlanningPhase = 0;
		try {
			Project project = projectDao.getProjectById(pid);
			Query query = sessionFactory.getCurrentSession().createQuery(Queries.GET_TASKS_FOR_PROJECT)
					.setParameter("project", project);
			List<Task> tasksList = query.list();
			if (!tasksList.isEmpty() && tasksList != null) {
				for (Task task : tasksList) {
					taskUnitsAtPlanningPhase += task.getEstimated_time();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("A Runtime exception has occurred");
		}
		return taskUnitsAtPlanningPhase;
	}

}
