package com.sjsu.cmpe275.projectmanager.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@Component
@Service
@Repository
public interface ReportDao {

	

	int getTaskUnitsFinished(int pid);

	int taskUnitsTobeFinished(int pid);

	int getTaskUnitsCancelled(int pid);

	int getTaskUnitsAtPlanningPhase(int pid);

}
