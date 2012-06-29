<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<f:view>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="JSFInAction/charpter8/styleSheet.css"/>
	<h:outputText value="ProjectTrack"></h:outputText>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h:form>
		<h:panelGrid columns="1" >
			<f:facet name="header">
				<h:outputText value="Inbox - approve or reject projects"></h:outputText>
			</f:facet>
			<h:outputText value="Application messages"></h:outputText>
			<h:panelGrid columns="5"  border="0">
				
						<h:commandLink>
							<h:outputText value="Project name"></h:outputText>
						</h:commandLink>
						
						<h:commandLink>
							<h:outputText value="Type"></h:outputText>
						</h:commandLink>
						
						<h:commandLink>
							<h:outputText value="Status"></h:outputText>
						</h:commandLink>
						
						<h:commandLink>
							<h:outputText value="Waiting for"></h:outputText>
						</h:commandLink>
						
						<h:panelGroup></h:panelGroup>
							
						
						<h:outputText value="TimeTracker"/>
						<h:outputText value="Internal Web Application"/>
						<h:outputText value="Requirements/Analysis"/>
						<h:outputText value="Develope manager"/>
						<h:commandLink action="details">
						<h:outputText value="Details"/>		
						</h:commandLink>
						
						
						<h:outputText value="TimeTracker"/>
						<h:outputText value="Internal Web Application"/>
						<h:outputText value="Requirements/Analysis"/>
						<h:outputText value="QA manager"/>
						<h:commandLink action="details">
						<h:outputText value="Details"/>		
						</h:commandLink>
				
			</h:panelGrid>
		</h:panelGrid>
		
	</h:form>	
</body>
</html>
</f:view>