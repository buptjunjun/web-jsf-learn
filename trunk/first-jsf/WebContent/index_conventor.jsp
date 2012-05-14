<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>title</title>
</head>

<f:view>
<body>
<h:form id = "helloForm">
 <h3>please enter your name </h3>
 <tr>
 	<td> name:</td>
	 <td>
	 <h:inputText id ="userName" value="#{userBean.userName}" />
	 <h:message for="userName"/>
	 </td>
 </tr>
 
 <h:commandButton id="helloCommand" type="submit" value="Submit" action="#{userBean.decide}"/>
</h:form>
</body>
</f:view>

</html>