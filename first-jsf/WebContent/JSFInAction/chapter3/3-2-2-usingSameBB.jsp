<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>jstl and jsf using the same Back Bean</title>
</head>
<body>
<f:view>
	<jsp:useBean class="jsf.chapter3.BB.ExampleBean" id="example"  scope="session"/>
	<p>
	<h:outputText> jstl and jsf using the same Back Bean</h:outputText>
	</p>
	
	<h:form>
	    <h:outputLabel for="inputInt">
	    	<h:outputText>How many times you want to repeat?</h:outputText>
	    </h:outputLabel>
	    <h:inputText id="inputInt" value="#{example.number}"></h:inputText>
	    <h:commandButton value="GO"></h:commandButton>
	    
	    <p>
    		
				<c:forEach begin="0" end="${example.number - 1}" var="count">
					Queen Tracey will achieve world domination.<br>
				</c:forEach>

	    </p>
	</h:form>
</f:view>
</body>
</html>