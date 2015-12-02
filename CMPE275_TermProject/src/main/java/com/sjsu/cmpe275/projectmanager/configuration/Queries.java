package com.sjsu.cmpe275.projectmanager.configuration;

public class Queries {

	public static final String GET_UID_FROM_PROJECT = "select owner from Project";
	public static final String GET_INVITATION_STATUS = "from UserProjectInfo where uid=:userId";
	public static String GET_TASKS_FOR_PROJECT = "from Task where project =:project";
	public static String GET_USERS_PROJECT_INFO = "select u.uid from UserProjectInfo u where PID = :pid and AcceptanceStatus = :accepted";
	public static String GET_USERS_LIST = "from User where uid in (:userIdList)";
	public static String GET_TASK_LIST = "from Task where project.pid = :projectId";
	public static String GET_USER_PROJECT_STATUS = "select u.acceptanceStatus from UserProjectInfo u where u.uid = :userId and u.pid = :projectId";
	public static String GET_TASK_LIST_FOR_DELETE = "select tid,taskname,description from task t, project p where t.project=p.pid = :projectId and p.owner =:userId";
	public static String GET_PID_FORM_USER_PROJECT_INFO = "select pid from UserProjectInfo where uid = :userId";
	public static String GET_PID_FROM_PROJECT_FOR_USER = "select pid from Project where owner.userId = :userId";
	// public static String GET_USERS_FROM_USER_ROLES="from User where email in
	// (select username from UserRoles where username:username and role=
	// :role)";

	public static String GET_USERS_FROM_USER_ROLES = "from User where email in (select username from UserRoles where NOT (username=:username))";

}
