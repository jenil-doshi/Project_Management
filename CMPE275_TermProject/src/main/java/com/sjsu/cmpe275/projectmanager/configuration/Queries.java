package com.sjsu.cmpe275.projectmanager.configuration;

public class Queries {

	public static String GET_TASKS_FOR_PROJECT = "from Task where project =:project";
	public static String GET_USERS_PROJECT_INFO = "select u.uid from UserProjectInfo u where PID = :pid and AcceptanceStatus = :accepted";
	public static String GET_USERS_LIST = "from User where uid in (:userIdList)";
}
