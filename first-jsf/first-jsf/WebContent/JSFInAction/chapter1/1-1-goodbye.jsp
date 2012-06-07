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
	<h:form id="goodbyeForm">
		<P>
		<h:outputText id="welcomeOutput"
				 	  value="goodbye" 
				 	  style="font-size:24; font-style:bold; color:green">
		</h:outputText>
		</P>	
		
		<h:outputText id="helloBeanOutputLabel"
		 			  value="Number of controls displayed:">
		 </h:outputText>
		 
		<h:outputText id="helloBeanOutput"
		 			  value="#{helloBean.numControls}">
		 </h:outputText> 
	</h:form>
</f:view>
</body>
</html>