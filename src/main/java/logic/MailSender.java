package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import main.Configs;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import filters.UserAuthFilter;

public class MailSender {
	private static Context ctx;
	private static final String CHOOSE_TASK_QUERY = "SELECT * FROM tasks WHERE idtask = ?";
	private static final String GET_EMAIL = "SELECT email FROM users WHERE iduser = ?";
	private static final String EMAIL_WAS_SENT = "UPDATE tasks SET notify = 2 WHERE idtask = ?";
	private static String subject = "Task Notification";
	private static String from = "YourSchedulerApp Notification <YourSchedulerApp@hotmail.com>";
	private static final Logger log = Logger.getLogger(MailSender.class);
	
	static {
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
						send(iduser, subject, sb.toString(), from, email);
						PreparedStatement pst3 = conn.prepareStatement(EMAIL_WAS_SENT);
						pst3.setInt(1, idtask);
						pst3.executeUpdate();
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

	private static void send(int iduser, String subject, String text, String fromEmail,
			String toEmail) {
		Session session = null;
		try {
			session = (Session) ctx.lookup("java:comp/env/mail/Session");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(fromEmail));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));

			message.setSubject(subject);

			message.setText(text);

			Transport.send(message);

		} catch (SendFailedException se) {
			log.error("email wasn't sent to user " + iduser, se);
		} catch (MessagingException e) {
			log.error("email wasn't sent to user " + iduser, e);
		}
	}
}
