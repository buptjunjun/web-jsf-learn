<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	function onClick(who)
	{
		alert(who.value);
	}
</script>

</head>
<body>
<f:view>
<h:form>
	<p>
		<h:selectManyCheckbox valueChangeListener ="#{charpter5.testValueChange}" 
							 >
			<f:selectItem	itemValue="#{cat}" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
			<f:selectItems value="#{charpter5.colors}" />
		</h:selectManyCheckbox>
	</p>
	
	<p>
		<h:selectManyListbox tabindex="4">
			<f:selectItem	itemValue="#{cat}" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
			<f:selectItems value="#{charpter5.colors}" />
		</h:selectManyListbox>
	</p>
	
	<p>
		<h:selectManyMenu>
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
			
		</h:selectManyMenu> 
	</p>
	
	<p>
		<h:selectOneRadio>
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
		</h:selectOneRadio>
	</p>
	
	<p>
		<h:selectOneListbox  size="3">
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
		</h:selectOneListbox>
	</p>
	
	<p>
		<h:selectOneMenu >
			<f:selectItem	itemValue="1" itemLabel="cat" />
			<f:selectItem	itemValue="2" itemLabel="dog" />
			<f:selectItem	itemDisabled="true" itemValue="3" itemLabel="none" />		
		</h:selectOneMenu>
	</p>
	
	
	<p>
	   <h:inputText accesskey="i"></h:inputText>
	</p>
	
	<p>
	   <h:inputTextarea accesskey="i"></h:inputTextarea>
	</p>
	
	<p>
	  <h:selectBooleanCheckbox title="Registered?" value="#{charpter5.registered}"></h:selectBooleanCheckbox>
	</p>
	

	<h:selectOneListbox id ="selectOneList" value="#{charpter5.view}"  size="1" onchange="onClick(this)">
		<f:selectItem itemValue="firstName" itemLabel="firstName"  />
		<f:selectItem itemValue="lastName" itemLabel="lastName" />
	</h:selectOneListbox>
	
	<p>
		<h:dataTable border="1" value="#{charpter5.userList}" var="user" binding="#{charpter5.dataTable}">
			<h:column rendered="#{charpter5.view == 'firstName'}">
				<f:facet name="header">
					<h:outputText value="Fist Name"></h:outputText>
				</f:facet>
				<h:inputText  value="#{user.firstName}"  />
			</h:column>
			
			<h:column rendered="#{charpter5.view == 'lastName'}" >
				<f:facet name="header"  >
					<h:outputText value="last Name"></h:outputText>
				</f:facet>
				<h:outputText value="#{user.lastName}"  ></h:outputText>
			</h:column>
			
			<h:column >
				<f:facet name="header">
					<h:outputText value="Balance"></h:outputText>
				</f:facet>
				<h:outputText value="#{user.currency}"></h:outputText>
			</h:column>
			
			<h:column >
				<f:facet name="header">
					<h:outputText value="Registered?"></h:outputText>
				</f:facet>
				<h:outputText value="#{user.registered}"></h:outputText>
			</h:column>
			
			<h:column >
				<f:facet name="header">
					<h:outputText value="delete"></h:outputText>
				</f:facet>
				<h:commandLink  actionListener="#{charpter5.deleteUser}">
						<h:outputText value="Delete"></h:outputText>
					</h:commandLink>
			</h:column>		
		</h:dataTable>
	</p>
	
	

	<h:selectManyCheckbox id="selectManyCheckboxColor" value="#{charpter5.selectedColorItems}">
		<f:selectItems value="#{charpter5.colors}"/>
	</h:selectManyCheckbox>

	<h:panelGroup>
		<h:commandButton value="Submit"/>
		<h:commandButton value="Reset" type="reset"/>
	</h:panelGroup>
</h:form>
</f:view>
</body>
</html>