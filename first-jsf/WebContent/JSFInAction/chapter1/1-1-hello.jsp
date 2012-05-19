<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSF in action - hello world</title>
</head>
<body>
<f:view>
  <h:inputText id="helloInput"
	   				value="#{helloBean.numControls}"
	   				required ="true" >
	   		<f:validateLongRange minimum="1" maximum = "330" />
	   </h:inputText>
<h:form  id="welcomeForm">
	<h:outputText id="welcomeOutput" 
				  value="Welcome to JaveServer Faces"
				  style="font-size:24;color:green"
				  >
	</h:outputText>
	<p>
	    <h:message id="errors" for="helloInput" style="color:red"></h:message>
	</p>
	<p>
	   <h:outputLabel for="helloInput">
	       <h:outputText id="helloInputLabel"
	       				 value="Enter number of controls to display:"
	       ></h:outputText>
	   </h:outputLabel>
	   
	   <h:inputText id="helloInput"
	   				value="#{helloBean.numControls}"
	   				required ="true" >
	   		<f:validateLongRange minimum="1" maximum = "330" />
	   </h:inputText>
	</p>
	
	<p>
	   <h:panelGrid	 id = "controlPanel"
	   				 binding="#{helloBean.controlPanel}"
	   				 columns="4" border="1" cellspacing="0">
	   </h:panelGrid>
	</p>
	<h:commandButton id ="redisplayCommand"
					 type="submit"
					 value="redisplay"
					 actionListener="#{helloBean.addControls}"
					 >
	</h:commandButton>
	
		<h:commandButton id ="goodbyeCommand"
					 type="submit"
					 value="goodBye"
					 action="#{helloBean.goodbye}"
					 immediate="true"
					 >
	</h:commandButton>
</h:form>
</f:view>
</body>
</html>