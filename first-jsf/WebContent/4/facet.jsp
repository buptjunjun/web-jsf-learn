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
    <h:panelGrid columns="3" cellpadding="1" border="1" width="40%">
    <f:facet name="header">
    	<h:outputText value="header of form"></h:outputText>
    </f:facet>
    	<h:outputText value="(1,1)"></h:outputText>
    	<h:outputText value="(1,2)"></h:outputText>
    	<h:outputText value="(1,3)"></h:outputText>
    	<h:outputText value="(2,1)"></h:outputText>
    	<h:outputText value="(3,2)"></h:outputText>
    	<h:outputText value="(3,3)"></h:outputText>   	
    <f:facet name="footer">
    	<h:outputText value="foot of form"></h:outputText>
    </f:facet>
    </h:panelGrid>
</f:view>
</body>
</html>