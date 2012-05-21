<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>jsf 嵌套 jstl</title>
</head>
<body>
<f:view>
	<h1>
		<h:outputLabel>
			<h:outputText> example of using jsf tags with other custom tags</h:outputText>
		</h:outputLabel>
	</h1>
	<p>
		<c:out value="here is the value of web.xml" />
		<blockquote>
			<f:verbatim>
			 <c:import url="/WEB-INF/web.xml" />
			</f:verbatim>
		</blockquote>	
	</p>
</f:view>
</body>
</html>