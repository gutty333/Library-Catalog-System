<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
	<head>
		<title>Profile Page</title>
	</head>
	
	<body>
		<h1>Profile Page</h1>
		<h3>Welcome: ${patron.firstName} ${patron.lastName}</h3>
		
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
		
		<!-- Patron Info -->
		<h3>District ID : 4257-100-${patron.id}</h3>
		<h3>First Name : ${patron.firstName}</h3>
		<h3>Last Name : ${patron.lastName}</h3>
		<h3>Email : ${patron.email}</h3>
		<h3>Total Fines : $ <fmt:formatNumber type="number" minFractionDigits="2" value="${patron.totalFines}"/></h3>
	</body>
</html>