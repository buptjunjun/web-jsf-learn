<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<%=path%><c:url value='/resources/styles/main.css'/>">   

<title>${movie.name}</title>
</head>
<body>
	<div id="header">
	</div>
	
	<div id="navigation">
	</div>
	
	<div id="content">
		<sf:form method="post" commandName="movie"  modelAttribute="movie" enctype="multipart/form-data"> 
		name:<sf:input path="name" value="${name}" /> <sf:errors path="name" delimiter=", "/><br>
		age:<sf:input path="age" value="${age}"/><sf:errors path="age" /><br> 
		picture:<input name="image" type="file" /><br/>
		<input type="submit" value="注册" name="testSubmit"/>    
		<input type="reset" value="重置" />    
</sf:form>   
	</div>
</body>
</html>