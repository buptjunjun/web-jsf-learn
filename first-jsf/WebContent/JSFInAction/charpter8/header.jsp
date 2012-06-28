<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<f:subview id="header">
<h:form id = "head_form">
	<h:panelGrid columns="3" cellpadding="0" cellspacing="0">
		<h:panelGrid border="0" columns="5">
			<h:outputText value="ProjectTrack"></h:outputText>
			<h:commandLink action="inbox">
				<h:outputText value="Inbox"></h:outputText>
			</h:commandLink>

			<h:commandLink action="show_all">
				<h:outputText value="Show All"></h:outputText>
			</h:commandLink>
			
			<h:commandLink action="create">
				<h:outputText value="Create"></h:outputText>
			</h:commandLink>
			
			<h:commandLink action="logout">
				<h:outputText value="Logout"></h:outputText>
			</h:commandLink>									
			
		</h:panelGrid>
		
		<h:panelGroup>
			<h:outputLabel for="language_select">
				<h:outputText value="Language:"></h:outputText>
			</h:outputLabel>
			
			<h:selectOneListbox size="1" id="language_select">
			    <f:selectItem itemLabel="English"  itemValue="English" />
			    <f:selectItem itemLabel="Russion"  itemValue="Russion" />
			</h:selectOneListbox>
			
			<h:commandButton value="Go" ></h:commandButton>
		</h:panelGroup>
		
		<h:outputText value="user_name"></h:outputText>
	</h:panelGrid>
</h:form>
</f:subview>