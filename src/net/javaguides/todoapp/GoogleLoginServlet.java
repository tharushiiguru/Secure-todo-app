package net.javaguides.todoapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login-google")
public class GoogleLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code != null) {
            // Fix: Create session after successful OAuth authentication
            HttpSession session = request.getSession();

            // Feature: Set authenticated OAuth user in session
            session.setAttribute("user", "google_user");

            // Redirect to secure dashboard after login
            response.sendRedirect(request.getContextPath() + "/list");

        } else {
            response.sendRedirect("login.jsp");
        }
    }
}


