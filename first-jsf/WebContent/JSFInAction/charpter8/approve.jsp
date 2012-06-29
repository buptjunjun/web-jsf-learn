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
		<h:panelGrid columns="2" >
			<f:facet name="header">
				<h:panelGrid>
					<f:facet name="header">
						<h:outputText value="Approve a project"></h:outputText>
					</f:facet>
					<h:outputText	value="Application messages." styleClass="errors"/>
				</h:panelGrid>
			</f:facet>
			
			<h:outputText value="Name:"/>
			<h:outputText value="Inventory Manager 2.0"
			styleClass="project-data"/>
			<h:outputText value="Type:"/>
			<h:outputText value="Internal Web Application"
			styleClass="project-data"/>
			<h:outputText value="Initiated by:"/>
			<h:outputText value="Rip Van Winkle"
			styleClass="project-data"/>
			<h:outputText value="Requirements contact:"/>
			<h:outputText value="Joan TooBusy"
			styleClass="project-data"/>
			<h:outputText value="Requirements contact e-mail:"/>
			<h:outputText value="toobusy@deathmarch.com"
			styleClass="project-data"/>
				
				
				
			</h:panelGrid>		
	</h:form>	
</body>
</html>
</f:view>