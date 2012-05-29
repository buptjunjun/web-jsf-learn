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
	<h:form>
	<h:panelGrid columns="2" border="0" cellpadding="3" cellspacing="3">
				<h:graphicImage url="/images/logo_cn.png"
								alt="Welcome to ProjectTrack"
								title="Welcome to ProjectTrack"
								width="164" height="160"
								>
				</h:graphicImage>
				<h:panelGrid columns="3" border="0" cellpadding="5" cellspacing="3">
					<f:facet name="header">
						<h:outputText value="Project Track" styleClass="login_head"></h:outputText>
					</f:facet>
						
						<h:outputLabel for="userNameInput">
							<h:outputText value="Enter your user name:"></h:outputText>
						</h:outputLabel>					
						<h:inputText  id = "userNameInput" value="" required="true">
							<f:validateLength minimum="5" maximum="15"/>
						</h:inputText>
						<h:message for="userNameInput" styleClass="errors"></h:message>
				
						<h:outputLabel for="secretInput">
							<h:outputText value="Enter your secret:"></h:outputText>
						</h:outputLabel>					
						<h:inputSecret  id = "secretInput" value="" required="true" > </h:inputSecret>
						<h:message for="secretInput" styleClass="errors"></h:message>
						
						
						<h:panelGroup>
							<h:commandButton action="success" value="Submit" type="submit"> </h:commandButton>
						</h:panelGroup>
				</h:panelGrid>
		</h:panelGrid>		
	</h:form>	
</body>
</html>
</f:view>