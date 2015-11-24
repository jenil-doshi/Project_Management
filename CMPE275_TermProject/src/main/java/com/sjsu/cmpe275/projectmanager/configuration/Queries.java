package com.sjsu.cmpe275.projectmanager.configuration;

public class Queries {

	public static String GET_TASKS_FOR_PROJECT = "from Task where project =:project";
	public static String GET_USERS_PROJECT_INFO = "select u.uid from UserProjectInfo u where PID = :pid and AcceptanceStatus = :accepted";
	public static String GET_USERS_LIST = "from User where uid in (:userIdList)";
	public static String GET_TASK_LIST = "from Task where project.pid = :projectId";
	public static String GET_USER_PROJECT_STATUS = "select u.acceptanceStatus from UserProjectInfo u where u.uid = :userId and u.pid = :projectId";
	public static String GET_PROJECT_TASK_LIST = "select t.state from State t where projectId = :projectId";
}
