<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
	System.out.println("path="+path);
%>
<jsp:include page="common.jsp"></jsp:include>
<html>
<head>
<title>pic galaxy</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<%=path %>/resources/style/common.css" />
<script>
	var host = "<%=path%>"; //http://localhost:8080ture/
</script>
 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/script/common.js" type="text/javascript"></script>

<style type="text/css">

div
{
	border-radius: 5px 5px 5px 5px;
}

#aboutDetail
{
	padding-top:200px;
	margin:auto;
	width:800px;
	
}

</style>


</head>
<body>
	<jsp:include page="nav.jsp"></jsp:include>

	<div id="content">
		<div id="aboutDetail">
		<span style="color:black; font-size:20px;">Email:</span>
		<span style="color:red; font-size:18px;">buptjunjun#gmail.com </span>
		<span style="color:black;font-size:	16px;">(please replace '#' with '@')</span>
		</div>
		<div style="clear: both"></div>
	</div>
</body>
</html>