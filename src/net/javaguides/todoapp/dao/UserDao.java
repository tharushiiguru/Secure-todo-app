//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import net.javaguides.todoapp.model.User;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class UserDao {
//
//	public int registerEmployee(User employee) throws ClassNotFoundException {
//		String INSERT_USERS_SQL = "INSERT INTO users"
//				+ "  (first_name, last_name, username, password) VALUES "
//				+ " (?, ?, ?, ?);";
//
//		int result = 0;
//		try (Connection connection = JDBCUtils.getConnection();
//				// Step 2:Create a statement using connection object
//				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
//			preparedStatement.setString(1, employee.getFirstName());
//			preparedStatement.setString(2, employee.getLastName());
//			preparedStatement.setString(3, employee.getUsername());
//			preparedStatement.setString(4, employee.getPassword());
//
//			System.out.println(preparedStatement);
//			// Step 3: Execute the query or update query
//			result = preparedStatement.executeUpdate();
//
//		} catch (SQLException e) {
//			// process sql exception
//			JDBCUtils.printSQLException(e);
//		}
//		return result;
//	}
//
//}

//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import net.javaguides.todoapp.model.User;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class UserDao {
//
//	public int registerEmployee(User user) throws ClassNotFoundException {
//		String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
//		try (Connection connection = JDBCUtils.getConnection();
//			 PreparedStatement pstmt = connection.prepareStatement(sql)) {
//			pstmt.setString(1, user.getFirstName());
//			pstmt.setString(2, user.getLastName());
//			pstmt.setString(3, user.getUsername());
//			pstmt.setString(4, user.getPassword()); // Will be hashed later (fix #4)
//			return pstmt.executeUpdate();
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//			return 0;
//		}
//	}
//}


package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import net.javaguides.todoapp.model.User;
import net.javaguides.todoapp.utils.JDBCUtils;

public class UserDao {
	public int registerEmployee(User user) throws ClassNotFoundException {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, hashed);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
			return 0;
		}
	}
}
