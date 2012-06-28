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
		<h:panelGrid 
			columns="1" cellpadding="5" >
			<f:facet name="header">
			<h:outputText
			value="Inbox - approve or reject projects"/>
			</f:facet>
			<h:outputText
			value="Application messages."
			styleClass="errors"/>
			<h:panelGrid columns="6"
			styleClass="table-background"
			rowClasses="table-odd-row,table-even-row"
			cellpadding="3">
			<h:commandLink styleClass="table-header">
			<h:outputText value="Project name"/>
			</h:commandLink>
			<h:commandLink styleClass="table-header">
			<h:outputText value="Type"/>
			</h:commandLink>
			<h:commandLink styleClass="table-header">
			<h:outputText value="Status"/>
			</h:commandLink>
			<h:panelGroup/>
			<h:panelGroup/>
			<h:panelGroup/>
			<h:outputText
			value="Inventory Manager v2.0"/>
			<h:outputText
			value="Internal Desktop Application"/>
			<h:outputText
			value="Requirements/Analysis"/>
			<h:commandLink action="approve">
			<h:outputText value="Approve"/>
			</h:commandLink>
			<h:commandLink action="reject">
			<h:outputText value="Reject"/>
			</h:commandLink>
			<h:commandLink action="details">
			<h:outputText value="Details"/>
			</h:commandLink>
			<h:outputText value="TimeTracker"/>
			<h:outputText value="Internal Web Application"/>
			<h:outputText value="Requirements/Analysis"/>
			<h:commandLink action="approve">
			<h:outputText value="Approve"/>
			</h:commandLink>
			<h:commandLink action="reject">
			<h:outputText value="Reject"/>
			</h:commandLink>
			<h:commandLink action="details">
			<h:outputText value="Details"/>
			</h:commandLink>
			</h:panelGrid>
		</h:panelGrid>
	</h:form>	
</body>
</html>
</f:view>