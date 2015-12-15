package com.sjsu.cmpe275.projectmanager.configuration;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {

	@Autowired
	private Environment environment;

	public boolean sendEmail(int uid, String recipientId, int projectId, String projectName, String projectOwner) {

		boolean emailSent = false;
		final String userId = environment.getProperty("userId");
		final String password = environment.getProperty("password");
		final String emailId = recipientId;
		Properties props = new Properties();
		props.put(Constants.SMTP_AUTH, Constants.VALUE_TRUE);
		props.put(Constants.STARTTLS_ENABLE, Constants.VALUE_TRUE);
		props.put(Constants.SMTP_HOST, Constants.SMTP_HOST_VALUE);
		props.put(Constants.PORT, Constants.PORT_VALUE);

		String subject = Constants.EMAIL_SUBJECT + " " + projectName;

		String body = "Dear User," + "\n\n This is an inivitation from Project Manager: " + projectOwner
				+ " to join Project: " + projectName + " \n\n Please click on the link below to accept the invitation."
				+ "\n\n http://cmpe275-env.elasticbeanstalk.com/project/invitationStatus/accept/" + uid + "/"
				+ recipientId + "/" + projectId + "\n\nTo reject the invitation click on the link below."
				+ "\n\n http://cmpe275-env.elasticbeanstalk.com/project/invitationStatus/reject/" + uid + "/"
				+ recipientId + "/" + projectId + "\n\n Regards, " + "\n\n " + projectOwner + "\n\n Project Manager";

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userId, password);
			}
		});
		try {
			if (isValidEmailAddress(emailId)) {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userId));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));

				message.setSubject(subject);
				message.setText(body);

				Transport.send(message);
				emailSent = Constants.TRUE;
				return emailSent;
			} else {
				emailSent = Constants.FALSE;
				return emailSent;
			}
		} catch (MessagingException e) {
			System.out.println("Messaging Exception");
			e.printStackTrace();
			emailSent = Constants.FALSE;
			return emailSent;
		}
	}

	private static boolean isValidEmailAddress(String emailId) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(emailId);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}
