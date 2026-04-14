//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import net.javaguides.todoapp.model.LoginBean;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class LoginDao {
//
//	public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
//		boolean status = false;
//
//		Class.forName("com.mysql.jdbc.Driver");
//
//		try (Connection connection = JDBCUtils.getConnection();
//				// Step 2:Create a statement using connection object
//				PreparedStatement preparedStatement = connection
//						.prepareStatement("select * from users where username = ? and password = ? ")) {
//			preparedStatement.setString(1, loginBean.getUsername());
//			preparedStatement.setString(2, loginBean.getPassword());
//
//			System.out.println(preparedStatement);
//			ResultSet rs = preparedStatement.executeQuery();
//			status = rs.next();
//
//		} catch (SQLException e) {
//			// process sql exception
//			JDBCUtils.printSQLException(e);
//		}
//		return status;
//	}
//}




//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import net.javaguides.todoapp.model.LoginBean;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class LoginDao {
//
//	public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
//		boolean status = false;
//		String username = loginBean.getUsername();
//		String password = loginBean.getPassword();
//
//		Class.forName("com.mysql.jdbc.Driver");
//
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement()) {
//
//			// VULNERABLE: string concatenation
//			String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
//			System.out.println(sql);
//			ResultSet rs = stmt.executeQuery(sql);
//			status = rs.next();
//
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}
//		return status;
//	}
//}


//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import net.javaguides.todoapp.model.LoginBean;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class LoginDao {
//
//	public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
//		boolean status = false;
//		String username = loginBean.getUsername();
//		String password = loginBean.getPassword();
//
//		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
//		try (Connection connection = JDBCUtils.getConnection();
//			 PreparedStatement pstmt = connection.prepareStatement(sql)) {
//			pstmt.setString(1, username);
//			pstmt.setString(2, password);
//			ResultSet rs = pstmt.executeQuery();
//			status = rs.next();
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}
//		return status;
//	}
//}


package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import net.javaguides.todoapp.model.LoginBean;
import net.javaguides.todoapp.utils.JDBCUtils;

public class LoginDao {
	// Fix: Prevented SQL Injection by using PreparedStatement instead of dynamic SQL queries
	public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
		String username = loginBean.getUsername();
		String password = loginBean.getPassword();
		String sql = "SELECT password FROM users WHERE username = ?";
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String storedHash = rs.getString("password");
				return BCrypt.checkpw(password, storedHash);
			}
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return false;
	}
}