package com.sjsu.cmpe275.projectmanager.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
@Component
public class AOPLogging {
	static Logger log = Logger.getLogger(AOPLogging.class.getName());

	@Before("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.*(..))")

	public void beforeLogging(JoinPoint joinPoint) {

		try {

			log.info("Entering API method: " + joinPoint.getSignature().getName() + "()");

		} catch (Throwable e) {

			e.printStackTrace();

		}

	}

	@After("execution(* com.sjsu.cmpe275.projectmanager.controller.*.*(..))")

	public void afterLogging(JoinPoint joinPoint) {

		try {

			log.info("Exiting API method: " + joinPoint.getSignature().getName() + "()");

		} catch (Throwable e) {

			e.printStackTrace();

		}

	}

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.createProject(..))")
	public void createProject(JoinPoint jp) throws Throwable {
		try {
			int userId = (int) jp.getArgs()[0];
			log.info("USER with id:" + userId + " creates a Project");
		} catch (Exception e) {
			log.info("AOPLogging:Exception: Create Project");
		}
	}

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.updateProject(..))")
	public void updateProject(JoinPoint jp) throws Throwable {
		try {
			int pid = (int) jp.getArgs()[1];
			int userId = (int) jp.getArgs()[0];
		
			log.info("USER with id:" + userId + " updates a Project with Project ID " + pid);
		} catch (Exception e) {
			log.info("AOPLogging:Exception: Update Project");
		}
	}
	

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.startProject(..))")
	public void startProject(JoinPoint jp) throws Throwable {
		try{
		int pid = (int) jp.getArgs()[0];
		int userId = (int) jp.getArgs()[1];
		log.info("USER with id:" + userId + " starts a Project with Project ID " + pid);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Start Project");
		}
	}

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.completeProject(..))")
	public void completeProject(JoinPoint jp) throws Throwable {
		try{
		int pid = (int) jp.getArgs()[0];
		int userId = (int) jp.getArgs()[1];
		log.info("USER with id:" + userId + " completes a Project with Project ID " + pid);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Complete Project");
		}
	}
	
	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.ProjectController.cancelProject(..))")
	public void cancelProject(JoinPoint jp) throws Throwable {
		try{
		int pid = (int) jp.getArgs()[0];
		int userId = (int) jp.getArgs()[1];
		log.info("USER with id:" + userId + " cancels a Project with Project ID " + pid);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Cancel Project");
		}
	}
	
	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.TaskController.createTask(..))")
	public void createTask(JoinPoint jp) throws Throwable {
		try {
			int userId = (int) jp.getArgs()[0];
			int pid = (int)jp.getArgs()[1];
			log.info("USER with id:" + userId + " creates a Task in project: "+pid);
		} catch (Exception e) {
			log.info("AOPLogging:Exception: Create Task");
		}
	}

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.TaskController.updateTask(..))")
	public void updateTask(JoinPoint jp) throws Throwable {
		try {
			int taskId = (int) jp.getArgs()[0];
			int userId = (int) jp.getArgs()[1];
		
			log.info("USER with id:" + userId + " updates a Task with Task ID " + taskId);
		} catch (Exception e) {
			log.info("AOPLogging:Exception: Update Task");
		}
	}
	

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.TaskController.startTask(..))")
	public void startTask(JoinPoint jp) throws Throwable {
		try{
		int taskId = (int) jp.getArgs()[0];
		log.info("Task started with Task ID " + taskId);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Start Task");
		}
	}

	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.TaskController.finishTask(..))")
	public void finishTask(JoinPoint jp) throws Throwable {
		try{
		int taskId = (int) jp.getArgs()[0];
		log.info("Task completed with a Task ID " + taskId);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Complete Task");
		}
	}
	
	@AfterReturning("execution(* com.sjsu.cmpe275.projectmanager.controller.TaskController.cancelTask(..))")
	public void cancelTask(JoinPoint jp) throws Throwable {
		try{
		int taskId = (int) jp.getArgs()[0];
		int userId = (int) jp.getArgs()[1];
		log.info("USER with id:" + userId + " cancels a Task with Task ID " + taskId);
		}catch(Exception e){
			log.info("AOPLogging:Exception: Cancel Task");
		}
	}

}
