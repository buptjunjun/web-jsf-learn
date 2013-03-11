<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" charset=GBK"  content="java c c++ python android ���� Ա ��ũ ����  web html jsp servlet ����">
<title>�������� | �۳���Ա�Լ�����������</title>
<link rel="icon" href="../icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../icon/ico.ico" type="image/x-icon" />

<script type="text/javascript">

// reset the query String
function resetQueryStr()
{
	var queryStr = document.getElementById("queryStr");
	var queryStrInput = document.getElementById("query");
	var queryText = queryStr.innerText;
	var queryText = queryStr.innerHTML;
	
	if(queryText != null && queryText != "undefined")
		queryStrInput.value = queryText;
	else
		queryStrInput.value = "";
	
}
</script>

<!--   color of em --> 
<style>
 em{   
    color: #CC0000;
    font-style: normal;
    }
body
{
padding-left:20px;
}
#content
{
	width:800px;
	padding-right:10px;	
}
</style>

</head>
<body>
	<%@ include file="common/header.jsp" %>
	<br>
	<jsp:useBean id="searchCriteria"  class="com.coderlong.search.springmvc.beans.SearchCriteria" scope="request" ></jsp:useBean>
		
	<spring:nestedPath path="searchCriteria">
	<form name= "form" action="" method="get">
		<div>
		  <input id="query" name="query" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
		  <input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		</div>
	</form>
	
	</spring:nestedPath>
	
	<br>
	<div id ="content" >
	<ul>
<%-- 	     <c:forEach items="${results}" var="result">
			<li>${result.title}</li>
			
		</c:forEach> --%>
 		<c:forEach items="${results}" var="result">
			<div>	
			<div><a href=${result.url}  target="_blank"> ${result.title}</a></div>
			<div>${result.content}</div>
			<div style='color:green'> ${result.url}  |  ${result.date}</div>
			</div><br>
		</c:forEach> 
	</ul>
	</div>
	<div  style ='display:none' id="queryStr" >${criteria.query}</div>
	<%@ include file="common/footer.jsp" %>
	
	<script type="text/javascript">
	
	 resetQueryStr();
	</script>
</body>