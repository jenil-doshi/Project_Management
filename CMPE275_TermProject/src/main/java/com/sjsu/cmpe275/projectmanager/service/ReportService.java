package com.sjsu.cmpe275.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cmpe275.projectmanager.dao.ReportDao;
import com.sjsu.cmpe275.projectmanager.model.Project;

@Service
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.dao")
@Transactional
public class ReportService {
	@Autowired
	private ReportDao reportDao;

	public Project getReport(int pid) {
		Project report = new Project();
		report.setTaskUnitsAtPlanningPhase(reportDao.getTaskUnitsAtPlanningPhase(pid));
		report.setTaskUnitsFinished(reportDao.getTaskUnitsFinished(pid));
		report.setTaskUnitsCancelled(reportDao.getTaskUnitsCancelled(pid));
		report.setTaskUnitsTobeFinished(reportDao.taskUnitsTobeFinished(pid));

		report.setPercentTaskUnitsFinished(
				Math.round((report.getTaskUnitsFinished() / (double) report.getTaskUnitsAtPlanningPhase()) * 100));
		report.setPercentTaskUnitsTobeFinished(
				Math.round((report.getTaskUnitsTobeFinished() / (double) report.getTaskUnitsAtPlanningPhase()) * 100));
		report.setPercentTaskUnitsCancelled(
				Math.round((report.getTaskUnitsCancelled() / (double) report.getTaskUnitsAtPlanningPhase()) * 100));
		return report;
	}

}
