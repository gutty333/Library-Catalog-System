<html>
	<head>
		<title>Signup Page</title>
	</head>

	<body>
		<h1>Library Catalog System</h1>
	
		<h3>Signup Page</h3>
		
		<p>${error}<p>
		
		<form action="SignupController" method="POST">
			First Name: <input type="text" name="firstName">
			<br><br>
			
			Last Name: <input type="text" name="lastName">
			<br><br>
			
			Email: <input type="text" name="email">
			<br><br>
			
			Password: <input type="password" name="password">
			<br><br>
			
			<a href="index.jsp">Cancel</a>
			<input type="submit" value="Signup">
		</form>
	</body>
</html>