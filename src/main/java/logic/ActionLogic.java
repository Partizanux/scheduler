package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import main.Configs;
import my_exceptions.FmtDataException;
import my_exceptions.IncorrectEmailException;
import my_exceptions.IncorrectLoginException;
import my_exceptions.UserExistsException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class ActionLogic {
	private static final Logger log = Logger.getLogger(ActionLogic.class);

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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = Configs.getConnection();

			PreparedStatement pst = conn.prepareStatement(GET_ID);
			pst.setString(1, login);
			rs = pst.executeQuery();
			rs.next();
			Integer ii = rs.getInt(1);

			ps = conn.prepareStatement(ADD_QUERY);

			ps.setString(1, date);
			ps.setString(2, time);
			ps.setString(3, task);
			ps.setInt(4, status);
			ps.setInt(5, ii);
			ps.setInt(6, 0);

			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                 log.warn("Failed to close rs", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
            }
		}
	}
	
	public static void delTask(String id, String login) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = Configs.getConnection();

			st = conn.prepareStatement(DEL_QUERY);
			st.setString(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) { 
                	log.warn("Failed to close st", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
            }
		}
	}
	
	public static void doneTask(String id, String login) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = Configs.getConnection();

			st = conn.prepareStatement(DONE_QUERY);
			st.setString(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) { 
                	log.warn("Failed to close st", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
            }
		}
	}
	
	public static boolean checkLogin (String login, String pass){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	conn = Configs.getConnection();
            ps = conn.prepareStatement(CHECK);
            ps.setString(1, login);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            rs.next();
          if (rs.getInt(1) == 1)
            	return true;
            return false;
            
       }catch(Exception e){
            e.printStackTrace();
       }
       finally{
    	   if (rs != null) {
               try {
                   rs.close();
               } catch (SQLException e) {
                log.warn("Failed to close rs", e);
               }
           }
           if (ps != null) {
               try {
                   ps.close();
               } catch (SQLException e) { 
                log.warn("Failed to close st", e);     
               }
           }
           if (conn != null) {
               try {
                   conn.close();
               } catch (SQLException e) {
                log.warn("Failed to close conn", e);
               }
           }
       }
		return false;
	}
	
	public static void registration(String fname, String lname, String login,
			String pass, String email) throws UserExistsException,
			IncorrectLoginException, IncorrectEmailException {

		if (!checkLoginCorrect(login))
			throw new IncorrectLoginException();
		
		if (!checkEmailCorrect(email))
			throw new IncorrectEmailException();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			conn = Configs.getConnection();
			ps = conn.prepareStatement(CHECK_EXISTS);
			ps.setString(1, login);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) != 0) {
				throw new UserExistsException();
			}

			pst = conn.prepareStatement(REG_QUERY);
			pst.setString(1, fname);
			pst.setString(2, lname);
			pst.setString(3, login);
			pst.setString(4, pass);
			pst.setString(5, email);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                 log.warn("Failed to close rs", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (pst !=null) {
                try {
                    pst.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
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
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		Connection conn = null;
		
		try {
			conn = Configs.getConnection();

			pst1 = conn.prepareStatement(GET_ID);
			pst1.setString(1, login);
			rslt = pst1.executeQuery();
			rslt.next();
			Integer id = rslt.getInt(1);

			pst2 = conn.prepareStatement(VIEW_QUERY);
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
			if (rslt != null) {
                try {
                    rslt.close();
                } catch (SQLException e) {
                 log.warn("Failed to close rs", e);
                }
            }
            if (pst1 != null) {
                try {
                    pst1.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (pst2 !=null) {
                try {
                    pst2.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
            }
		}
		return tasks;
	}
	
	public static void mailNotification(String id, String notify) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = Configs.getConnection();

			String query = null;
			if(notify.equals("no"))
				query = NOTIFICATION_ON;
			if(notify.equals("yes"))
				query = NOTIFICATION_OFF;
			
			st = conn.prepareStatement(query);
			st.setString(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) { 
                 log.warn("Failed to close st", e);     
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                 log.warn("Failed to close conn", e);
                }
            }
		}
	}
	
}
