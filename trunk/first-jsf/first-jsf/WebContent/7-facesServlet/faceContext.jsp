<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>
	<h:outputText value="show the root path on server"></h:outputText>
	<br />	
	<%-- show the root path --%>
	<h:panelGrid rendered="#{faceservettest.show}">
		 <h:outputText value="#{faceservettest.info}">
		</h:outputText>
	</h:panelGrid>
	<br />
	
	<%--button to submit --%>
	<h:form>
	    <h:commandButton action="#{faceservettest.showFacesContext}" value="show root path"></h:commandButton>
	</h:form>
	
	
    <h:outputText value="show the messages"></h:outputText>
	<br />	
	<%--button to submit --%>
	<h:form>
		<h:inputText id = "testMessage" required="true"></h:inputText>
		<h:message for="testMessage"></h:message>
	    <h:commandButton action="#{faceservettest.showMessageText}" value="show message"></h:commandButton>
	</h:form>
</f:view>
</body>
</html>