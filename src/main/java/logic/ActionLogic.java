package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Configs;
import my_exceptions.FmtDataException;
import my_exceptions.IncorrectLoginException;
import my_exceptions.UserExistsException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class ActionLogic {

	private static final String ADD_QUERY = "INSERT INTO tasks (date, time, task, status, iduser, notify) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String GET_ID = "SELECT iduser FROM users WHERE login = ?";// login is unique
	private static final String DEL_QUERY = "DELETE FROM tasks WHERE idtask = ?";
	private static final String DONE_QUERY = "UPDATE tasks SET status = 1 WHERE idtask = ?";
	private static final String CHECK = "SELECT COUNT(*) FROM users WHERE login = ? AND pass = ?";
	private static final String REG_QUERY = "INSERT INTO users (firstName, lastName, login, pass, email) VALUES (?, ?, ?, ?, ?);";
	private static final String CHECK_EXISTS = "SELECT COUNT(*) FROM users WHERE login = ?";
	private static final String VIEW_QUERY = "SELECT idtask, date, time, task, status, notify FROM tasks WHERE iduser = ? ORDER BY date, time";
	
	private static final String NOTIFICATION_ON = "UPDATE tasks SET notify = 1 WHERE idtask = ?";
	private static final String NOTIFICATION_OFF = "UPDATE tasks SET notify = 0 WHERE idtask = ?";
	
	public static void addTask(String login, String dd, String mm, String yy,
			String hour, String min, String task, int status)
			throws FmtDataException {
		String date;
		String time;

		int d = -1;
		int m = -1;
		int y = -1;
		int h = -1;
		int mnt = -1;
		
		try {
			d = Integer.parseInt(dd);
			m = Integer.parseInt(mm);
			y = Integer.parseInt(yy);
			h = Integer.parseInt(hour);
			mnt = Integer.parseInt(min);
		} catch (NumberFormatException e1) {
			throw new FmtDataException();
		}

		if (d < 1 || d > 31 || m < 1 || m > 12 || y < 0 || h < 0 || h > 23
				|| mnt < 0 || mnt > 59)
			throw new FmtDataException();

		date = yy + "-" + mm + "-" + dd;
		time = hour + ":" + min;

		Connection conn = null;
		ResultSet rs;
		try {
			conn = Configs.getConnection();

			PreparedStatement pst = conn.prepareStatement(GET_ID);
			pst.setString(1, login);
			rs = pst.executeQuery();
			rs.next();
			Integer ii = rs.getInt(1);

			PreparedStatement ps = conn.prepareStatement(ADD_QUERY);

			ps.setString(1, date);
			ps.setString(2, time);
			
			System.out.println(task);// ! ! !
			
			ps.setString(3, task);
			ps.setInt(4, status);
			ps.setInt(5, ii);
			ps.setInt(6, 0);

			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();// Release connection back to the pool
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void delTask(String id, String log) {

		Connection conn = null;
		try {
			conn = Configs.getConnection();

			PreparedStatement st = conn.prepareStatement(DEL_QUERY);
			st.setString(1, id);
			st.executeUpdate();

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
	
	public static void doneTask(String id, String log) {

		Connection conn = null;
		try {
			conn = Configs.getConnection();

			PreparedStatement st = conn.prepareStatement(DONE_QUERY);
			st.setString(1, id);
			st.executeUpdate();

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
	
	public static boolean checkLogin (String login, String pass){

        Connection conn = null;
        try{
        	conn = Configs.getConnection();
            PreparedStatement ps = conn.prepareStatement(CHECK);
            ps.setString(1, login);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            rs.next();
          if (rs.getInt(1) == 1)
            	return true;
            return false;
            
       }catch(Exception e){
            e.printStackTrace();
       }
       finally{
            try {
                 if(conn != null)
                 conn.close();
            } catch (SQLException e) {
                 e.printStackTrace();
            }
       }
		return false;
	}
	
	public static void registration(String fname, String lname, String log,
			String pass, String email) throws UserExistsException,
			IncorrectLoginException {

		if (!checkLoginCorrect(log) || !checkEmailCorrect(email))
			throw new IncorrectLoginException();

		Connection conn = null;
		ResultSet rs;
		try {
			conn = Configs.getConnection();
			PreparedStatement ps = conn.prepareStatement(CHECK_EXISTS);
			ps.setString(1, log);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) != 0) {
				throw new UserExistsException();
			}
			rs.close();

			PreparedStatement pst = conn.prepareStatement(REG_QUERY);
			pst.setString(1, fname);
			pst.setString(2, lname);
			pst.setString(3, log);
			pst.setString(4, pass);
			pst.setString(5, email);
			pst.execute();

		} catch (SQLException e) {
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

	private static boolean checkLoginCorrect(String log) {
		Pattern p = Pattern
				.compile("_?[-0-9a-zA-Z]{3,10}_?[-0-9a-zA-Z]{0,10}_?");
		Matcher m = p.matcher(log);
		return m.matches();
	}
	
	private static boolean checkEmailCorrect(String email) {
		Pattern p = Pattern
				.compile("\\A[^@]+@([^@\\.]+\\.)+[^@\\.]+\\Z");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static List<UTasks> getTasks(String login) {

		List<UTasks> tasks = new ArrayList<UTasks>();

		ResultSet rslt = null;

		Connection conn = null;
		try {
			conn = Configs.getConnection();

			PreparedStatement pst1 = conn.prepareStatement(GET_ID);
			pst1.setString(1, login);
			rslt = pst1.executeQuery();
			rslt.next();
			Integer id = rslt.getInt(1);

			PreparedStatement pst2 = conn.prepareStatement(VIEW_QUERY);
			pst2.setInt(1, id);
			rslt = pst2.executeQuery();

			while (rslt.next()) {
				UTasks t = new UTasks();
				t.setIdt(rslt.getInt(1));
				t.setDate(rslt.getString(2));
				t.setTime(rslt.getString(3));
				t.setTaskmsg(rslt.getString(4));
				if (rslt.getBoolean(5) == false)//false
					t.setStatus("not_done");
				else
					t.setStatus("done");
				
				int notify = rslt.getInt(6);
				
				switch (notify) {
					case 0:
						t.setNotification("no");
						break;
					case 2:
						t.setNotification("was sent");
						break;
					default:
						t.setNotification("yes");
						break;
				}

				tasks.add(t);

			}

		} catch (MySQLSyntaxErrorException msqle) {
		} catch (SQLException sqle) {
			// when smb tries to load "view.jsp" without session
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
		return tasks;
	}
	
	public static void mailNotification(String id, String notify) {
		Connection conn = null;
		try {
			conn = Configs.getConnection();

			String query = null;
			if(notify.equals("no"))
				query = NOTIFICATION_ON;
			if(notify.equals("yes"))
				query = NOTIFICATION_OFF;
			
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, id);
			st.executeUpdate();

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
