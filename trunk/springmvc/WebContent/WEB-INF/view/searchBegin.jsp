<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>search for flight service</title>
</head>
<body>
	<h1>search for flight </h1>	
	
	<!-- searchFligtCriteria is the "searchFligtCriteria" in SearchFlightController -->
	<spring:nestedPath path="searchFligtCriteria">
		<form action="search" method="post">
			<spring:bind path="departFrom">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
			<spring:bind path="departOn">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
			<spring:bind path="arriveAt">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
			<spring:bind path="returnOn">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
			<input type="submit" value="search">
		</form>
	</spring:nestedPath>
</body>
</html>