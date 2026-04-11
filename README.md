Secure Todo Web Application
---------------------------
IE5042 – Software Security Assignment

Group Members
-------------
AMR Adithya - MS26909578

KGT Lakshani – MS26900254


Project Overview
----------------
This project is a secure version of a Todo Web Application developed using JSP, Servlets, JDBC, and MySQL.

The original application (third-party project) contained several security vulnerabilities. These vulnerabilities were identified using manual testing and security tools, and then fixed using secure coding practices based on the OWASP Top 10.

🔗 GitHub Links

- Original Project (Third-Party): https://github.com/sourcecodeexamples/todo-application-jsp-servlet-jdbc-mysql
- Secured Project (This Repository): https://github.com/tharushiiguru/Secure-todo-app

🎥 Demo Video
YouTube Link: https://youtu.be/vPHvjuLH1OM

Technologies Used
-----------------
Java (JSP & Servlets)

JDBC

MySQL

Apache Tomcat

OWASP ZAP

Features
--------
* User Registration and Login
* Todo Management (Create, Update, Delete, View)

   > Secure Session-Based Authentication
   
   > Google OAuth Login Integration
   
   > Identified Vulnerabilities and Fixes
   
   
1. SQL Injection (High)

- Vulnerability: User inputs were directly included in SQL queries

- Fix: Implemented Prepared Statements


2. Missing Access Control (High)

  - Vulnerability: Unauthorized users could access protected pages

  - Fix: Implemented Authentication Filter


3. OAuth Access Control Misconfiguration

  - Vulnerability: OAuth callback endpoint was blocked by filter

  - Fix: Whitelisted /login-google endpoint in filter


4. Insecure Direct Object Reference (IDOR) (High)

  - Vulnerability: Users could access other users’ data

  - Fix: Validated user ownership before data access


5. Plaintext Password Storage (High)

  - Vulnerability: Passwords stored in plain text

  - Fix: Implemented password hashing


6. Cross-Site Scripting (XSS) (Medium)

  - Vulnerability: Script injection via user input

  - Fix: Input validation and output encoding


7. Hardcoded Credentials (Medium)

  - Vulnerability: Database credentials stored in source code

  - Fix: Moved credentials to external configuration file


OAuth Implementation
--------------------
Google OAuth 2.0 was integrated to enhance authentication.

Users can log in using their Google accounts
OAuth callback handled via /login-google
Session created after successful authentication


Security Best Practices Applied
-------------------------------
 > Input validation and sanitization
 
 > Use of prepared statements
 
 > Secure session management
 
 > Access control using filters
 
 > Password hashing
 
 > Externalized configuration
 
 > Security testing using OWASP ZAP
 
 
How to Run the Project
----------------------

1. Clone the repository: git clone https://github.com/tharushiiguru/Secure-todo-app.git
2. Import into IntelliJ IDEA
3. Configure database connection
4. Run using Apache Tomcat
5. Access the application: http://localhost:8080/todo_application_jsp_servlet_jdbc_mysql_master_war_exploded

Project Structure
----------------

dao/ – Database operations

model/ – Data models

web/ – Servlets and Controllers

utils/ – Utility classes

References
-----------

OWASP Top 10

OWASP ZAP

SQLMap


Conclusion
----------

This project demonstrates how common web application vulnerabilities can be identified and mitigated using secure coding practices. The integration of Google OAuth further enhances authentication security and improves user experience.
