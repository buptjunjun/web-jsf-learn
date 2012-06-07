<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:subview id="header">
	<h:form>
	    <h:panelGrid columns="3"  width="100%" cellspacing="0" cellpadding="0" styleClass="header">
	    	<h:panelGrid
	    		columns="9"
	    		cellpadding="4"
	    		cellspacing="0"
	    		border="0">
	    		
	    		<h:outputText value="ProjectTrack:" styleClass="header-header"></h:outputText>
	    		<h:commandLink action="inbox" >
	    			<h:graphicImage></h:graphicImage>
	    			<h:outputText value="Inbox" styleClass="header-command"></h:outputText>
	    		</h:commandLink>
	    		
	    		<h:commandLink action="inbox" >
	    			<h:graphicImage></h:graphicImage>
	    			<h:outputText value="Inbox" styleClass="header-command"></h:outputText>
	    		</h:commandLink>
	    		
	    		<h:commandLink action="show_all" >
	    			<h:graphicImage></h:graphicImage>
	    			<h:outputText value="Show All" styleClass="header-showall"></h:outputText>
	    		</h:commandLink>
	    		
	    		<h:commandLink action="create" >
	    			<h:graphicImage></h:graphicImage>
	    			<h:outputText value="Create New" styleClass="header-createnew"></h:outputText>
	    		</h:commandLink>
	    		
	    		<h:commandLink action="logout" >
	    			<h:graphicImage></h:graphicImage>
	    			<h:outputText value="Logout" styleClass="header-logout"></h:outputText>
	    		</h:commandLink>   		
	    	</h:panelGrid>
	    	
	    	<h:panelGroup>
	    		<h:outputLabel for="languageSelect">
	    			<h:outputText value="Select Language"></h:outputText>
	    		</h:outputLabel>
	    		<h:selectOneListbox id = "languageSelect">
	    		 	<f:selectItem itemLabel="English" itemValue="english"/>
	    		 	<f:selectItem itemLabel="Russian" itemValue="russian"/>
	    		</h:selectOneListbox>
	    	</h:panelGroup>
	    	
	    	<h:commandButton value="GO" styleClass="language-select-button"></h:commandButton>
	    	
	    	<h:outputText value="proj_mgr" styleClass="user-name	"></h:outputText>
	    </h:panelGrid>
	</h:form>
</f:subview>