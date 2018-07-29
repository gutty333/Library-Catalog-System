<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<html>
	<head>
		<title>Patron Books</title>
	</head>
	
	<body>
		<h1>List of books you are currently Holding</h1>
		
		<!-- Navigation Menu Buttons -->
		<ul>
			<c:url var="profileLink" value="PatronController">
				<c:param name="patronID" value="${patron.id}"></c:param>
			</c:url>
			
			<c:url var="bookSearchLink" value="BookController">
				<c:param name="patronID" value="${patron.id}"></c:param>
			</c:url>
			
			<c:url var="myBooksLink" value="BookController">
				<c:param name="patronID" value="${patron.id}"></c:param>
				<c:param name="command" value="MYBOOKS"></c:param>
			</c:url>
		
		
			<li><a href="${profileLink}">Profile</a></li>
			<li><a href="${bookSearchLink}">Search Books</a></li>
			<li><a href="${myBooksLink}">My Books</a></li>
			<li><a href="index.jsp">Logout</a></li>
		</ul>
		
		<h3>My Books Here</h3>
		
		<p>${message}<p>
		<p>${message2}<p>
		
		<!-- Table show casing the books the patron is holding -->
		<table border="1">
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Genre</th>
				<th>Action</th>
			</tr>

			<c:forEach var="current" items="${books}">
				<tr>
					<td>${current.title}</td>
					<td>${current.author}</td>
					<td>${current.genre}</td>
					
					<c:url var="bookLink" value="BookController">
						<c:param name="patronID" value="${patron.id}"></c:param>
						<c:param name="bookID" value="${current.id}"></c:param>
						<c:param name="command" value="RETURN"></c:param>
					</c:url>
							
					<td><a href="${bookLink}">Return Book</a></td>
				</tr>
			</c:forEach>			
		</table>
	</body>
</html>