//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.javaguides.todoapp.model.Todo;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
///**
// * This DAO class provides CRUD database operations for the table todos in the
// * database.
// *
// * @author Ramesh Fadatare
// *
// */
//
//public class TodoDaoImpl implements TodoDao {
//
//	private static final String INSERT_TODOS_SQL = "INSERT INTO todos"
//			+ "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);";
//
//	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?";
//	private static final String SELECT_ALL_TODOS = "select * from todos";
//	private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
//	private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";
//
//	public TodoDaoImpl() {
//	}
//
//	@Override
//	public void insertTodo(Todo todo) throws SQLException {
//		System.out.println(INSERT_TODOS_SQL);
//		// try-with-resource statement will auto close the connection.
//		try (Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS_SQL)) {
//			preparedStatement.setString(1, todo.getTitle());
//			preparedStatement.setString(2, todo.getUsername());
//			preparedStatement.setString(3, todo.getDescription());
//			preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
//			preparedStatement.setBoolean(5, todo.getStatus());
//			System.out.println(preparedStatement);
//			preparedStatement.executeUpdate();
//		} catch (SQLException exception) {
//			JDBCUtils.printSQLException(exception);
//		}
//	}
//
//	@Override
//	public Todo selectTodo(long todoId) {
//		Todo todo = null;
//		// Step 1: Establishing a Connection
//		try (Connection connection = JDBCUtils.getConnection();
//				// Step 2:Create a statement using connection object
//				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TODO_BY_ID);) {
//			preparedStatement.setLong(1, todoId);
//			System.out.println(preparedStatement);
//			// Step 3: Execute the query or update query
//			ResultSet rs = preparedStatement.executeQuery();
//
//			// Step 4: Process the ResultSet object.
//			while (rs.next()) {
//				long id = rs.getLong("id");
//				String title = rs.getString("title");
//				String username = rs.getString("username");
//				String description = rs.getString("description");
//				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
//				boolean isDone = rs.getBoolean("is_done");
//				todo = new Todo(id, title, username, description, targetDate, isDone);
//			}
//		} catch (SQLException exception) {
//			JDBCUtils.printSQLException(exception);
//		}
//		return todo;
//	}
//
//	@Override
//	public List<Todo> selectAllTodos() {
//
//		// using try-with-resources to avoid closing resources (boiler plate code)
//		List<Todo> todos = new ArrayList<>();
//
//		// Step 1: Establishing a Connection
//		try (Connection connection = JDBCUtils.getConnection();
//
//				// Step 2:Create a statement using connection object
//				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS);) {
//			System.out.println(preparedStatement);
//			// Step 3: Execute the query or update query
//			ResultSet rs = preparedStatement.executeQuery();
//
//			// Step 4: Process the ResultSet object.
//			while (rs.next()) {
//				long id = rs.getLong("id");
//				String title = rs.getString("title");
//				String username = rs.getString("username");
//				String description = rs.getString("description");
//				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
//				boolean isDone = rs.getBoolean("is_done");
//				todos.add(new Todo(id, title, username, description, targetDate, isDone));
//			}
//		} catch (SQLException exception) {
//			JDBCUtils.printSQLException(exception);
//		}
//		return todos;
//	}
//
//	@Override
//	public boolean deleteTodo(int id) throws SQLException {
//		boolean rowDeleted;
//		try (Connection connection = JDBCUtils.getConnection();
//				PreparedStatement statement = connection.prepareStatement(DELETE_TODO_BY_ID);) {
//			statement.setInt(1, id);
//			rowDeleted = statement.executeUpdate() > 0;
//		}
//		return rowDeleted;
//	}
//
//	@Override
//	public boolean updateTodo(Todo todo) throws SQLException {
//		boolean rowUpdated;
//		try (Connection connection = JDBCUtils.getConnection();
//				PreparedStatement statement = connection.prepareStatement(UPDATE_TODO);) {
//			statement.setString(1, todo.getTitle());
//			statement.setString(2, todo.getUsername());
//			statement.setString(3, todo.getDescription());
//			statement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
//			statement.setBoolean(5, todo.getStatus());
//			statement.setLong(6, todo.getId());
//			rowUpdated = statement.executeUpdate() > 0;
//		}
//		return rowUpdated;
//	}
//}



//package net.javaguides.todoapp.dao;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.javaguides.todoapp.model.Todo;
//import net.javaguides.todoapp.utils.JDBCUtils;
//
//public class TodoDaoImpl implements TodoDao {
//
//	// No constants – we'll build SQL dynamically
//
//	@Override
//	public void insertTodo(Todo todo) throws SQLException {
//		// VULNERABLE: string concatenation
//		String sql = "INSERT INTO todos (title, username, description, target_date, is_done) VALUES ('"
//				+ todo.getTitle() + "', '" + todo.getUsername() + "', '" + todo.getDescription() + "', '"
//				+ JDBCUtils.getSQLDate(todo.getTargetDate()) + "', " + todo.getStatus() + ")";
//		System.out.println(sql);
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement()) {
//			stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}
//	}
//
//	@Override
//	public Todo selectTodo(long todoId) {
//		Todo todo = null;
//		String sql = "SELECT id, title, username, description, target_date, is_done FROM todos WHERE id = " + todoId;
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement();
//			 ResultSet rs = stmt.executeQuery(sql)) {
//			if (rs.next()) {
//				todo = new Todo(
//						rs.getLong("id"),
//						rs.getString("title"),
//						rs.getString("username"),
//						rs.getString("description"),
//						rs.getDate("target_date").toLocalDate(),
//						rs.getBoolean("is_done")
//				);
//			}
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}
//		return todo;
//	}
//
//	@Override
//	public List<Todo> selectAllTodos() {
//		List<Todo> todos = new ArrayList<>();
//		String sql = "SELECT * FROM todos";
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement();
//			 ResultSet rs = stmt.executeQuery(sql)) {
//			while (rs.next()) {
//				todos.add(new Todo(
//						rs.getLong("id"),
//						rs.getString("title"),
//						rs.getString("username"),
//						rs.getString("description"),
//						rs.getDate("target_date").toLocalDate(),
//						rs.getBoolean("is_done")
//				));
//			}
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}
//		return todos;
//	}
//
//	@Override
//	public boolean deleteTodo(int id) throws SQLException {
//		String sql = "DELETE FROM todos WHERE id = " + id;
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement()) {
//			return stmt.executeUpdate(sql) > 0;
//		}
//	}
//
//	@Override
//	public boolean updateTodo(Todo todo) throws SQLException {
//		String sql = "UPDATE todos SET title = '" + todo.getTitle()
//				+ "', username = '" + todo.getUsername()
//				+ "', description = '" + todo.getDescription()
//				+ "', target_date = '" + JDBCUtils.getSQLDate(todo.getTargetDate())
//				+ "', is_done = " + todo.getStatus()
//				+ " WHERE id = " + todo.getId();
//		try (Connection connection = JDBCUtils.getConnection();
//			 Statement stmt = connection.createStatement()) {
//			return stmt.executeUpdate(sql) > 0;
//		}
//	}
//}



package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.todoapp.model.Todo;
import net.javaguides.todoapp.utils.JDBCUtils;

public class TodoDaoImpl implements TodoDao {

	private static final String INSERT_TODOS_SQL = "INSERT INTO todos (title, username, description, target_date, is_done) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_TODO_BY_ID_AND_USER = "SELECT id, title, username, description, target_date, is_done FROM todos WHERE id = ? AND username = ?";
	private static final String SELECT_ALL_TODOS_BY_USER = "SELECT * FROM todos WHERE username = ?";
	private static final String DELETE_TODO_BY_ID_AND_USER = "DELETE FROM todos WHERE id = ? AND username = ?";
	private static final String UPDATE_TODO_BY_ID_AND_USER = "UPDATE todos SET title = ?, description = ?, target_date = ?, is_done = ? WHERE id = ? AND username = ?";

	@Override
	// Fix: Prevented SQL Injection by using PreparedStatement instead of dynamic SQL queries
	public void insertTodo(Todo todo) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(INSERT_TODOS_SQL)) {
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getUsername());
			pstmt.setString(3, todo.getDescription());
			pstmt.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
			pstmt.setBoolean(5, todo.isStatus());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	@Override
	public Todo selectTodo(long todoId, String username) {
		Todo todo = null;
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(SELECT_TODO_BY_ID_AND_USER)) {
			pstmt.setLong(1, todoId);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				todo = new Todo(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("username"),
						rs.getString("description"),
						rs.getDate("target_date").toLocalDate(),
						rs.getBoolean("is_done")
				);
			}
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todo;
	}

	@Override
	public List<Todo> selectAllTodos(String username) {
		List<Todo> todos = new ArrayList<>();
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_TODOS_BY_USER)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				todos.add(new Todo(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("username"),
						rs.getString("description"),
						rs.getDate("target_date").toLocalDate(),
						rs.getBoolean("is_done")
				));
			}
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todos;
	}

	@Override
	public boolean deleteTodo(int id, String username) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(DELETE_TODO_BY_ID_AND_USER)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, username);
			return pstmt.executeUpdate() > 0;
		}
	}

	@Override
	public boolean updateTodo(Todo todo, String username) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
			 PreparedStatement pstmt = connection.prepareStatement(UPDATE_TODO_BY_ID_AND_USER)) {
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getDescription());
			pstmt.setDate(3, JDBCUtils.getSQLDate(todo.getTargetDate()));
			pstmt.setBoolean(4, todo.isStatus());
			pstmt.setLong(5, todo.getId());
			pstmt.setString(6, username);
			return pstmt.executeUpdate() > 0;
		}
	}
}

