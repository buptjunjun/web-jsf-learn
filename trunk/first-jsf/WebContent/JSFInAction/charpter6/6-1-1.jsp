<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="/WEB-INF/tag.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>
	<h:form>
	<f:loadBundle basename="localizationResources_en" var="bundle"/>
	<h:outputText value="#{bundle['father']}"></h:outputText><br/>
	<h:outputText value="#{bundle.mother}"></h:outputText><br/>
	<h:outputText value="#{bundle.laopo}"></h:outputText> <br/>
<%-- 	<h:inputText id="date" value="#{charpter6.date }" validator="#{charpter6.ipValidator}" >
		<f:attribute name="version" value="4"></f:attribute>
		<f:convertDateTime />
	</h:inputText>
	 --%>
	 
	<h:inputText id="date2" required="true" value="#{charpter6.ipv4}">
		<%-- <t:ipValidator ipVersion="4"/> --%>
	</h:inputText>
	
	<h:commandButton  type="submit" value="submit"></h:commandButton> <br />
	<h:message for="date2"></h:message>
	
	<%-- log options --%>
	<h:panelGrid columns="2" style="border-collapse:separate;"  
		columnClasses=",nowrap," >									
		
		<h:column>
			<h:panelGrid  columns="1" style="margin-bottom:5px">
				 <h:outputText value="Log options:  "></h:outputText> 
				 <h:outputText value="&nbsp;" escape="false" /> 
				 <h:outputText value="&nbsp;" escape="false" />
			</h:panelGrid>		
		</h:column>
		
		<h:column>
			<h:selectOneRadio   style="margin-left:20px"  layout="pageDirection" >
				<f:selectItem  itemValue="" itemLabel="System log"/>
				<f:selectItem  itemValue="" itemLabel="Inbound log"/>
				<f:selectItem  itemValue="" itemLabel="Outbound log"/>
			</h:selectOneRadio>
		</h:column>								
	</h:panelGrid>

	</h:form>
</f:view>
</body>
</html>