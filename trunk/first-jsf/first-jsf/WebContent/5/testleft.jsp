<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type ="text/javascript" src ="../js/jquery-1.7.1.js" ></script>
<title>Insert title here</title>
</head>

 
<body>
  <form id="form1">
    <div id ="divMsg">Hello World</div>
    <input type ="button" id ="btnShow" value ="show" />
    <input type ="button" id ="btnHide"  value="hide" /><br />
    <input type ="button"  id ="btnChange" value ="modified content is Hello World too" />
   
    <script type ="text/javascript" >
    $("#btnShow").bind("click",function(event){ $("#divMsg").show();});
    $("#btnHide").bind("click",function(event){ $("#divMsg").hide();});
    $("#btnChange").bind("click",function(event){$("#divMsg").html("Hello World too");});
   
    </script>
    </form>
</body>
</html>