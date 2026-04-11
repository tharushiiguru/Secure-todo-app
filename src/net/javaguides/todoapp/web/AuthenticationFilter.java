//package net.javaguides.todoapp.web;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebFilter("/*")
//public class AuthenticationFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization if needed
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String uri = req.getRequestURI();
//        String contextPath = req.getContextPath();
//
//        // Allow access to login, register, and static resources without authentication
//        boolean isLoginPage = uri.endsWith("login") || uri.contains("/login/login.jsp");
//        boolean isRegisterPage = uri.endsWith("register") || uri.contains("/register/register.jsp");
//        boolean isStaticResource = uri.contains(".css") || uri.contains(".js") || uri.contains(".jpg") || uri.contains(".png");
//
//        HttpSession session = req.getSession(false);
//        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
//
//        if (isLoggedIn || isLoginPage || isRegisterPage || isStaticResource) {
//            chain.doFilter(request, response);
//        } else {
//            res.sendRedirect(contextPath + "/login");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup if needed
//    }
//}


package net.javaguides.todoapp.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// No @WebFilter annotation – mapping will be in web.xml
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // optional: print confirmation
        System.out.println("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        // Allow access to login, register, logout, and static resources without authentication
        // Fix: Added authentication check to restrict unauthorized access
        boolean isLoginPage = uri.endsWith("/login")
                || uri.contains("/login/login.jsp")
                || uri.endsWith("/login-google"); // Fix: Allowed OAuth endpoint to bypass authentication filter
        boolean isRegisterPage = uri.endsWith("/register") || uri.contains("/register/register.jsp");
        boolean isLogout = uri.endsWith("/logout");
        boolean isStaticResource = uri.contains(".css") || uri.contains(".js") || uri.contains(".jpg") || uri.contains(".png");

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn || isLoginPage || isRegisterPage || isStaticResource || isLogout) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
        // optional
        System.out.println("AuthenticationFilter destroyed");
    }
}