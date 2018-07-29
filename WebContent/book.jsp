<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<html>
	<head>
		<title>Book Page</title>
	</head>

	<body>
		<h1>Book Info</h1>
		
		<c:url var="bookSearchLink" value="BookController">
			<c:param name="patronID" value="${patron.id}"></c:param>
		</c:url>
		
		<c:url var="bookTakeOutLink" value="BookController">
			<c:param name="patronID" value="${patron.id}"></c:param>
			<c:param name="bookID" value="${book.id}"></c:param>
			<c:param name="command" value="TAKEOUT"></c:param>
		</c:url>
		
		<h3>Title : ${book.title}</h3>
		<h3>Author : ${book.author}</h3>
		<h3>Genre : ${book.genre}</h3>
		<h3>Return Date : ${date}</h3>
		
		<a href="${bookSearchLink}">Cancel</a>
		<a href="${bookTakeOutLink}" onclick="if (!(confirm('Please note the book must be returned before ${date.month} ${date.dayOfMonth}, ${date.year}?'))) return false">Take Out</a>
	</body>

</html>