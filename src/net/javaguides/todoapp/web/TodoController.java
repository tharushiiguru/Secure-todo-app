//package net.javaguides.todoapp.web;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.javaguides.todoapp.dao.TodoDao;
//import net.javaguides.todoapp.dao.TodoDaoImpl;
//import net.javaguides.todoapp.model.Todo;
//
///**
// * ControllerServlet.java This servlet acts as a page controller for the
// * application, handling all requests from the todo.
// *
// * @email Ramesh Fadatare
// */
//
//@WebServlet("/")
//public class TodoController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private TodoDao todoDAO;
//
//	public void init() {
//		todoDAO = new TodoDaoImpl();
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String action = request.getServletPath();
//
//		try {
//			switch (action) {
//			case "/new":
//				showNewForm(request, response);
//				break;
//			case "/insert":
//				insertTodo(request, response);
//				break;
//			case "/delete":
//				deleteTodo(request, response);
//				break;
//			case "/edit":
//				showEditForm(request, response);
//				break;
//			case "/update":
//				updateTodo(request, response);
//				break;
//			case "/list":
//				listTodo(request, response);
//				break;
//			default:
//				RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
//				dispatcher.forward(request, response);
//				break;
//			}
//		} catch (SQLException ex) {
//			throw new ServletException(ex);
//		}
//	}
//
//	private void listTodo(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, IOException, ServletException {
//		List<Todo> listTodo = todoDAO.selectAllTodos();
//		request.setAttribute("listTodo", listTodo);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
//		dispatcher.forward(request, response);
//	}
//
//	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
//		dispatcher.forward(request, response);
//	}
//
//	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, ServletException, IOException {
//		int id = Integer.parseInt(request.getParameter("id"));
//		Todo existingTodo = todoDAO.selectTodo(id);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
//		request.setAttribute("todo", existingTodo);
//		dispatcher.forward(request, response);
//
//	}
//
//	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//
//		String title = request.getParameter("title");
//		String username = request.getParameter("username");
//		String description = request.getParameter("description");
//
//		/*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"),df);*/
//
//		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
//		Todo newTodo = new Todo(title, username, description, LocalDate.now(), isDone);
//		todoDAO.insertTodo(newTodo);
//		response.sendRedirect("list");
//	}
//
//	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		int id = Integer.parseInt(request.getParameter("id"));
//
//		String title = request.getParameter("title");
//		String username = request.getParameter("username");
//		String description = request.getParameter("description");
//		//DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
//
//		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
//		Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);
//
//		todoDAO.updateTodo(updateTodo);
//
//		response.sendRedirect("list");
//	}
//
//	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		int id = Integer.parseInt(request.getParameter("id"));
//		todoDAO.deleteTodo(id);
//		response.sendRedirect("list");
//	}
//}


package net.javaguides.todoapp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.javaguides.todoapp.dao.TodoDao;
import net.javaguides.todoapp.dao.TodoDaoImpl;
import net.javaguides.todoapp.model.Todo;

@WebServlet("/")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDAO;

	public void init() {
		todoDAO = new TodoDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Fix: Validated logged-in user before allowing access to resources (IDOR prevention)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		String username = getCurrentUsername(request);
		if (username == null && !action.equals("/login") && !action.equals("/register")) {
			response.sendRedirect("login");
			return;
		}

		try {
			switch (action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertTodo(request, response);
					break;
				case "/delete":
					deleteTodo(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateTodo(request, response);
					break;
				case "/list":
					listTodo(request, response);
					break;
				default:
					response.sendRedirect("list");
					break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private String getCurrentUsername(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null) ? (String) session.getAttribute("user") : null;
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String username = getCurrentUsername(request);
		List<Todo> listTodo = todoDAO.selectAllTodos(username);
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = getCurrentUsername(request);
		Todo existingTodo = todoDAO.selectTodo(id, username);
		if (existingTodo != null) {
			request.setAttribute("todo", existingTodo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("list?error=unauthorized");
		}
	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));
		String username = getCurrentUsername(request);
		Todo newTodo = new Todo(title, username, description, LocalDate.now(), isDone);
		todoDAO.insertTodo(newTodo);
		response.sendRedirect("list");
	}

	private void updateTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));
		String username = getCurrentUsername(request);
		Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);
		boolean updated = todoDAO.updateTodo(updateTodo, username);
		if (updated) {
			response.sendRedirect("list");
		} else {
			response.sendRedirect("list?error=unauthorized");
		}
	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = getCurrentUsername(request);
		boolean deleted = todoDAO.deleteTodo(id, username);
		if (deleted) {
			response.sendRedirect("list");
		} else {
			response.sendRedirect("list?error=unauthorized");
		}
	}
}
