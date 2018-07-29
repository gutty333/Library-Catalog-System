<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<html>
	<head>
		<title>Search Books</title>
	</head>
	
	<body>
		<h1>Search through our Catalog</h1>
		
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
		
		
		<h3>Search Here</h3>
		
		<!-- Book Search Bar and Filters -->
		<form action="BookController" method="GET">
			<input type="text" placeholder="Book Title or Author Name" name="search">
			
			<input type="hidden" name="patronID" value="${patron.id}">
			
			<select name="genre">
				<option>Select Genre</option>
				<c:forEach var="current" items="${genreList}">
					<option>${current}</option>
				</c:forEach>
			</select>
			
			<input type="submit" value="Search">
		</form>
		
		
		<!-- Table show casing our search result -->
		<table border="1">
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Genre</th>
				<th>Available</th>
				<th>Action</th>
			</tr>

			<c:forEach var="current" items="${books}">
				<tr>
					<td>${current.title}</td>
					<td>${current.author}</td>
					<td>${current.genre}</td>
					
					<c:choose>
						<c:when test="${current.available}">
							<td>YES</td>
							
							<c:url var="bookLink" value="BookController">
								<c:param name="patronID" value="${patron.id}"></c:param>
								<c:param name="bookID" value="${current.id}"></c:param>
								<c:param name="command" value="TAKE"></c:param>
							</c:url>
							
							<td><a href="${bookLink}">Take Out</a></td>
						</c:when>
						
						<c:otherwise>
							<td>NO</td>
							<td>N/A</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>			
		</table>
	</body>
</html>