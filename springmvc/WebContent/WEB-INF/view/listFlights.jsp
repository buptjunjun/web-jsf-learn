<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> flights </title>
</head>
<body>
	<h1>flights available</h1>
	
	<ul>
		<c:forEach items="${flights}" var="flight">
			<li>${flight.totalCost}  from  ${flight.departFrom}</li>
			
		</c:forEach>
	</ul>
	<p><a href="search">search a flight</a></p>
</body>
</html>