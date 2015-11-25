package com.sjsu.cmpe275.projectmanager.configuration;

public final class Constants {

	/****** EMAIL INVITATION **********/
	public static String SMTP_AUTH = "mail.smtp.auth";
	public static String VALUE_TRUE = "true";
	public static String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static String SMTP_HOST = "mail.smtp.host";
	public static String SMTP_HOST_VALUE = "smtp.gmail.com";
	public static String PORT = "mail.smtp.port";
	public static String PORT_VALUE = "587";
	public static String EMAIL_SUBJECT = "Request to join project: ";
	public static boolean TRUE = true;
	public static boolean FALSE = false;
	public static String ACCEPT = "accept";
	public static String REJECT = "reject";
	public static String INVITATION_PENDING = "pending";
	public static String INVITATION_ACCEPT = "accepted";
	public static String INVITATION_REJECT = "rejected";

	/***** PROJECT STATES ********/
	public static String PROJECT_NEW = "new";
	public static String PROJECT_PLANNING = "planning";
	public static String PROJECT_ONGOING = "ongoing";
	public static String PROJECT_COMPLETED = "completed";
	public static String PROJECT_CANCELLED = "cancelled";

	/***** TASK STATES ********/
	public static String TASK_ASSIGNED = "assigned";
	public static String TASK_NEW = "new";
	public static String TASK_STARTED = "started";
	public static String TASK_FINISHED = "finished";
	public static String TASK_CANCELLED = "cancelled";

	/********** ROLES ************/
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final int ENABLED = 1;
}
