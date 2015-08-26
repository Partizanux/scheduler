package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import main.Configs;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

//daily notify
public class SendMailTimer extends TimerTask{
	private Hashtable<Integer, Integer> userTasks = new Hashtable<Integer, Integer>();//K:idtask - V:iduser
	private static final String TASKS_TO_BE_SEND = 
				"SELECT idtask, iduser FROM tasks WHERE status=0 AND notify=1 AND date=?";
		@Override
		public void run() {
			userTasks.clear();
			
			getUserTasks();
			
			Integer key;
			Integer value;
			Enumeration<Integer> e = userTasks.keys();
			while(e.hasMoreElements()){
				key = e.nextElement();
				value = userTasks.get(key);
				
				System.out.println("before sending mail");
				
				MailSender.sendMail(key, value);
				
				System.out.println("after mail was sent");
			}
			
		}
		
		private void getUserTasks() {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = sdf.format(date);
			
			ResultSet rslt = null;

			Connection conn = null;
			try {
				conn = Configs.getConnection();
				
				PreparedStatement pst = conn.prepareStatement(TASKS_TO_BE_SEND);
				pst.setString(1, strDate);
				rslt = pst.executeQuery();
				while(rslt.next()){
					int k = rslt.getInt(1);
					int v = rslt.getInt(2);
					userTasks.put(k, v);
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
		
}
