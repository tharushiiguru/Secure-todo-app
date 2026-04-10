//package net.javaguides.todoapp.utils;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDate;
//
//public class JDBCUtils {
//
//	private static String jdbcURL = "jdbc:mysql://localhost:3306/todo_management";
//	private static String jdbcUsername = "root";
//	private static String jdbcPassword = "";   // XAMPP default is empty
//
//	public static Connection getConnection() {
//		Connection connection = null;
//		try {
////			Class.forName("com.mysql.jdbc.Driver");
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return connection;
//	}
//
//	public static void printSQLException(SQLException ex) {
//		for (Throwable e : ex) {
//			if (e instanceof SQLException) {
//				e.printStackTrace(System.err);
//				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//				System.err.println("Message: " + e.getMessage());
//				Throwable t = ex.getCause();
//				while (t != null) {
//					System.out.println("Cause: " + t);
//					t = t.getCause();
//				}
//			}
//		}
//	}
//
//	public static Date getSQLDate(LocalDate date) {
//		return java.sql.Date.valueOf(date);
//	}
//
//	public static LocalDate getUtilDate(Date sqlDate) {
//		return sqlDate.toLocalDate();
//	}
//}



package net.javaguides.todoapp.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

public class JDBCUtils {

	private static String jdbcURL;
	private static String jdbcUsername;
	private static String jdbcPassword;

	static {
		try (InputStream input = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
			Properties props = new Properties();
			props.load(input);
			jdbcURL = props.getProperty("db.url");
			jdbcUsername = props.getProperty("db.user");
			jdbcPassword = props.getProperty("db.password");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load database properties", e);
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public static Date getSQLDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

	public static LocalDate getUtilDate(Date sqlDate) {
		return sqlDate.toLocalDate();
	}
}