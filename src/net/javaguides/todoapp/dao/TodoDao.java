//package net.javaguides.todoapp.dao;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import net.javaguides.todoapp.model.Todo;
//
//public interface TodoDao {
//
//	void insertTodo(Todo todo) throws SQLException;
//
//	Todo selectTodo(long todoId);
//
//	List<Todo> selectAllTodos();
//
//	boolean deleteTodo(int id) throws SQLException;
//
//	boolean updateTodo(Todo todo) throws SQLException;
//
//}

package net.javaguides.todoapp.dao;

import java.sql.SQLException;
import java.util.List;
import net.javaguides.todoapp.model.Todo;

public interface TodoDao {
	void insertTodo(Todo todo) throws SQLException;
	Todo selectTodo(long todoId, String username);
	List<Todo> selectAllTodos(String username);
	boolean deleteTodo(int id, String username) throws SQLException;
	boolean updateTodo(Todo todo, String username) throws SQLException;
}
