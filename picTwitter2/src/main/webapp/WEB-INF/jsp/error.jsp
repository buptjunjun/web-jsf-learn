<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
	System.out.println("path="+path);
%>
<jsp:include page="common.jsp"></jsp:include>
<script type="text/javascript">
var host = "<%=path%>"; //http://localhost:8080/picture/
</script>

<html>
<head>
<title>pic galaxy</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="<%=path %>/resources/style/common.css" /> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<style type="text/css">

div
{
	border-radius: 5px 5px 5px 5px;
}
ul {
	list-style: none;
	margin: 0px;
	padding: 0px;
}

ul li {
	margin: 0px;
	padding: 2px;
}

#content {
	margin: auto;
	width: 1024px;
	min-height: 800px;
	height: auto;
	text-align: center;
	padding-top: 15px;
	padding-left: 5px;
	background-color: white;
	margin-top:60px;
}
.opps
{
	color:red;
	font-size:30px;
	margin-bottom:10px;
}
img
{
	border-radius: 10px 10px 10px 10px;
}
</style>


<script type="text/javascript">


</script>
</head>
<body>
	<span id="kind" class="hiddenid">${kind}</span>
	<jsp:include page="nav.jsp"></jsp:include>	
	<div id="content">
		<div class="opps">oops! something bad happened!</div>
		<div><img  src="<%=path%>/resources/img/404.png" onerror="$(this).prop('src','<%=path%>/resources/img/user_head.png');"/></div>
		<div style="clear: both"></div>
	</div>
</body>
</html>