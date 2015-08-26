package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import main.Configs;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class MailSender {
	private static final String CHOOSE_TASK_QUERY = "SELECT * FROM tasks WHERE idtask = ?";
	private static final String GET_EMAIL = "SELECT email FROM users WHERE iduser = ?";
	private static final String EMAIL_WAS_SENT = "UPDATE tasks SET notify = 2 WHERE idtask = ?";
	private static String subject = "Task Notification";
	private static String from = "YourSchedulerApp Notification <YourSchedulerApp@hotmail.com>";
	private static final Logger log = Logger.getLogger(MailSender.class);

	public static void sendMail(int idtask, int iduser) {

		StringBuilder sb = new StringBuilder();
		
		ResultSet rslt = null;

		Connection conn = null;
		try {
			conn = Configs.getConnection();
			
			PreparedStatement pst1 = conn.prepareStatement(GET_EMAIL);
			pst1.setInt(1, iduser);
			rslt = pst1.executeQuery();
			rslt.next();
			String email = rslt.getString(1);

			PreparedStatement pst2 = conn.prepareStatement(CHOOSE_TASK_QUERY);
			pst2.setInt(1, idtask);
			rslt = pst2.executeQuery();
			rslt.next();

			sb.append(rslt.getString(2));
			sb.append(" ");
			sb.append(rslt.getString(3));
			sb.append(" \r\n");
			sb.append(rslt.getString(4));

			if (rslt.getBoolean(5) == false){//status - not done				
				if (send(iduser, subject, sb.toString(), from, email) == true) {
					PreparedStatement pst3 = conn
							.prepareStatement(EMAIL_WAS_SENT);
					pst3.setInt(1, idtask);
					pst3.executeUpdate();
				}
				}
			
		} catch (MySQLSyntaxErrorException msqle) {
			msqle.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean send(int iduser, String subject, String text, String fromEmail,
			String toEmail) {
		boolean success = false;
/*
		ResourceBundle rb = ResourceBundle.getBundle("email");
		String host = (String) rb.getObject("mail.smtp.host");
		String port = (String) rb.getObject("mail.smtp.port");
		String auth = (String) rb.getObject("mail.smtp.auth");
		String starttls = (String) rb.getObject("mail.smtp.starttls.enable");
		final String user = (String) rb.getObject("mail.smtp.user");
		final String password = (String) rb.getObject("password");
		*/
		System.out.println("properties:");
		Properties props = null;
		try {
			props = new Properties();
			FileInputStream in = new FileInputStream("email");
			props.load(in);
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		final String user = (String) props.getProperty("mail.smtp.user");
		final String password = (String) props.getProperty("password");

		System.out.println(user);
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				  });
		
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(fromEmail));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));

			message.setSubject(subject);

			message.setText(text);

			Transport.send(message);

			success = true;
			
		} catch (SendFailedException se) {
			log.error("email wasn't sent to user " + iduser, se);
			success = false;
		} catch (MessagingException e) {
			log.error("email wasn't sent to user " + iduser, e);
			success = false;
		}
		
		return success;
	}
}
