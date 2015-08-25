package main;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Configs {
	private static DataSource src = null;
	
	static{
		try {
			Context ctx = new InitialContext();
			src = (DataSource)ctx.lookup("java:comp/env/jdbc/ConnectionPool");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return src.getConnection();
	}
}
