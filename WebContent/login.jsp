<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<html>
	<head>
		<title>Login Page</title>
	</head>

	<body>
		<h1>Library Catalog System</h1>
	
		<h3>Login Page</h3>
		
		<p>${error}<p>
		
		<form action="LoginController" method="POST">
			Email: <input type="text" name="email">
			<br><br>
			
			Password: <input type="password" name="password">
			<br><br>
			
			<a href="index.jsp">Cancel</a>
			<input type="submit" value="Login">
		</form>
	</body>
</html>