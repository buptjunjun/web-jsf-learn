<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>

 	<h:outputText value=" escape test: <a> hello </a>" escape="false" /> <br />
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
	
	<p>
		<h:outputLink value="http://www.baidu.com">
			<h:outputText value="go to baidu"></h:outputText>
			<f:param name="hl" value="zh-CN"></f:param>
		</h:outputLink>
	</p>
	
	
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
</f:view>
</body>
</html>