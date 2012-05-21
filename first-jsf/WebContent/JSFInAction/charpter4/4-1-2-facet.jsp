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
	<h:dataTable value="#{charpter4.favoriteSites}" var="site">
	    <f:facet name="header">
	    	<h:outputText value="Table Header"></h:outputText>
	    </f:facet>
	    
	    <h:column>
	    	<f:facet name="header"> 
	    		<h:outputText value="colum header"></h:outputText>
	    	</f:facet>
	    	<h:outputText value="#{site}"></h:outputText>
	    </h:column>
	    
	    <f:facet name="footer">
	    	<h:panelGroup>
	    		<h:commandButton value="next page" action="#{charpter4.nextPage }"></h:commandButton>
	    		<h:commandButton value="previous page" action="#{charpter4.prePage }"></h:commandButton>
	    	</h:panelGroup>
	    </f:facet>
	</h:dataTable>
</f:view>
</body>
</html>