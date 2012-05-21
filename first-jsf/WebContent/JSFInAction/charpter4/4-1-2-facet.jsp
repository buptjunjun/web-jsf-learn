<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.column1
{
background-color:blue;
}

.column2
{
background-color:green;
}

</style>

<title>Insert title here</title>
</head>
<body>
<f:view locale="es">

 	<h:outputText value=" escape test: <a> hello </a>" escape="false" /> <br />
 	
	<%-- outputFormat test --%>
	<p>
		<h:outputFormat value="hello baidu :{0}; hello google:{1} ">
			<f:param value="#{charpter4.favoriteSites[0]}"></f:param>
			<f:param value="www.google.com"></f:param>
		</h:outputFormat>
	</p>	
	<p>
		<h:outputFormat value="hello baidu :{0} {0,choice,0#times | 1#time | 2#times} ">
			<f:param value="#{charpter4.times}"></f:param>
		</h:outputFormat>
	</p>
	
	<%-- outputText test--%>
	<p>
		<h:outputLink value="http://www.baidu.com">
			<h:outputText value="go to baidu"></h:outputText>
			<f:param name="hl" value="zh-CN"></f:param>
		</h:outputLink>
	</p>
	
	<%-- messages test --%>
	<h:form>
	<p>
		<h:outputLabel for="myinput">
			<h:outputText value="enter text:"></h:outputText>
		</h:outputLabel>
		<h:inputText id="myinput">
		    	<f:validateLength minimum="1" maximum="3" />
		    	<f:validateLongRange minimum="1" maximum="100" />
		</h:inputText>
		<h:messages for="myinput"
				   showDetail="true"
				   showSummary="true"
				   warnStyle="color:green"
				   infoStyle="color:blue"
				   errorStyle="color:red"
				   globalOnly="false"
		/>
	</p>
	<h:commandButton value="sumbmit"></h:commandButton>
	
	</h:form>
	
	<%-- show image --%>
	<p>
		<h:graphicImage url="/images/logo_cn.png" 
						alt="this is google"
						title="welcome to google"
						width = "300"
						height="200"
		/>
	</p>
	
	<%--facet test --%>
	<h:dataTable value="#{charpter4.favoriteSites}" var="site">
	    <f:facet name="header">
	    	<h:outputText value="Table Header"></h:outputText>
	    </f:facet>
	    
	    <h:column>
	    	<f:facet name="header"> 
	    		<h:outputText value="colum header"></h:outputText>
	    	</f:facet>
	    	<h:outputText value="#{site}"></h:outputText>
	    </h:column>
	    <br />	    
	    
	    <f:facet name="footer">
	    	<h:panelGroup>
	    		<h:commandButton value="next page" action="#{charpter4.nextPage }"></h:commandButton>
	    		<h:commandButton value="previous page" action="#{charpter4.prePage }"></h:commandButton>
	    	</h:panelGroup>
	    </f:facet>
	</h:dataTable>
	
	<%-- test panelGroup--%>
	<h:panelGroup style="background-color: blue" > 
		<h:graphicImage url="/images/logo_cn.png" 
						alt="this is google"
						title="welcome to google"
						width = "30"
						height="20" 
						/>
		<h:outputText value="column 1"></h:outputText>
		<h:outputText value="column 2"></h:outputText>
	</h:panelGroup>
	
	
	<%-- panelGrid --%>
	<h:panelGrid columns="4"  cellpadding="1" border="1" columnClasses="column1,column2" >
		<f:facet name="header">
			<h:panelGroup>
				<h:graphicImage url="/images/logo_cn.png" 
							alt="this is google"
							title="welcome to google"
							width = "30"
							height="20"
							 />
			<h:outputText value="this is header"></h:outputText>
			</h:panelGroup>
		</f:facet>
		
		<h:outputText value="(1,1)"/>
		<h:outputText value="(1,2)"/>
		<h:outputText value="(1,3)"/>
		<h:outputText value="(1,4)"/>
		<h:outputText value="(2,1)"/>
		<h:outputText value="(2,2)"/>
		<h:outputText value="(2,3)"/>
		<h:outputText value="(2,4)"/>
		<h:outputText value="(3,1)"/>
		<h:outputText value="(3,2)"/>
		<h:outputText value="(3,3)"/>
		<h:outputText value="(3,4)"/>

		<f:facet name="footer">
			<h:outputText value="this is footer"></h:outputText>
		</f:facet>
	</h:panelGrid>
</f:view>
</body>
</html>