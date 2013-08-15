<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>form</title>
</head>
<body>
<sf:form method="post" commandName="form"  modelAttribute="form" enctype="multipart/form-data"> 
name:<sf:input path="name" value="${name}" /> <sf:errors path="name" delimiter=", "/><br>
age:<sf:input path="age" value="${age}"/><sf:errors path="age" /><br> 
picture:<input name="image" type="file" /><br/>
<input type="submit" value="注册" name="testSubmit"/>    
<input type="reset" value="重置" />    
</sf:form>   
</body>
</html>