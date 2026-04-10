<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<jsp:include page="../common/header.jsp"></jsp:include>
	<div class="container col-md-8 col-md-offset-3" style="overflow: auto">
		<h1>Login Form</h1>
		<form action="<%=request.getContextPath()%>/login" method="post">

			<div class="form-group">
				<label for="uname">User Name:</label> <input type="text"
					class="form-control" id="username" placeholder="User Name"
					name="username" required>
			</div>

			<div class="form-group">
				<label for="uname">Password:</label> <input type="password"
					class="form-control" id="password" placeholder="Password"
					name="password" required>
			</div>


			<button type="submit" class="btn btn-primary">Submit</button>

			<hr>

			<a href="https://accounts.google.com/o/oauth2/v2/auth?
client_id=593569313201-pjraqa8gnai324en40r8mq9v9t8qmsft.apps.googleusercontent.com
&redirect_uri=http://localhost:8080/todo_application_jsp_servlet_jdbc_mysql_master_war_exploded/login-google
&response_type=code
&scope=openid email profile"
			   class="btn btn-danger">
				Login with Google
			</a>


		</form>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>