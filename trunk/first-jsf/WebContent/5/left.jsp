<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type ="text/javascript" src ="../js/jquery-1.7.1.js" ></script>
<script type="text/javascript">

$(document).ready(function() {
	   $("a").click(function() {
	     alert("Hello world!");
	   });
	   
	 });
	 
$(document).ready(function() {
	$("a").click(function() {
		$("#orderedlist").find("li").each( function(i) {  $(this).html("modified");});
	}
);
});
	
$(document).ready(function() {
	$("#selectall").click(function() {
		$("#selectOne").each(function() {  $(this).selected=true;});					
		}
);
});
</script> 

</head>
<body>
<%--  <f:view>
  <a >link</a>
  <h:form id="myform">
  <h:column id="firtCol">
	 <h:selectBooleanCheckbox id="selectall" ></h:selectBooleanCheckbox> <br />
	 <h:selectBooleanCheckbox ></h:selectBooleanCheckbox><br />
	 <h:selectBooleanCheckbox ></h:selectBooleanCheckbox><br />
	 <h:selectBooleanCheckbox ></h:selectBooleanCheckbox><br />
	 <h:selectBooleanCheckbox ></h:selectBooleanCheckbox><br />
  </h:column>
  
  </h:form>
</f:view> --%>
<form>
 <input type="checkbox" id="selectall"></input> <br />
 <input type="checkbox" id="selectOne"></input> <br />
 <input type="checkbox" id="selectAnother"></input> <br />
</form>
<ol id ="orderedlist">
  <li>Coffee</li>
  <li>Tea</li>
  <li>Milk</li>
</ol>
<br />
<a >link</a>
</body>
</html>