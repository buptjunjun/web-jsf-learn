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
	<form:errors></form:errors> 
	<spring:nestedPath path="searchFligtCriteria">
		<form action="" method="post">
			<spring:bind path="departFrom">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
		<%-- 	date :
			<spring:bind path="departOn">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind> --%>
			<br>
			<spring:bind path="arriveAt">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
<%-- 			date:
			<spring:bind path="departOn">
				<input type="text" name="${status.expression }" value="${status.value }"/>
			</spring:bind>
			<br> --%>
			
			<input type="submit" value="search">
		</form>
	</spring:nestedPath>
</body>
</html>