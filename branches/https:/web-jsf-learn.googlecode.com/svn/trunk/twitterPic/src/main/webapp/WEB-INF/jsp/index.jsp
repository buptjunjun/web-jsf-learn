<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pic galaxy</title>

<style type="text/css">
	#nav
	{
		top:0px;
		width:100%;
		height:70px;
		border: 1px solid #DFDFE0;
		z-index: 999;
		position:fixed;
		padding-bottom:10px;
	}
	#navdetail
	{
		padding-top:5px;
		width:100%;
		margin:auto;
		height:30px;
		border: 1px solid black;
		z-index: 999;
	}
	#hot
	{
		
		margin:auto;
		width:1024px;
		height:30px;
		border: 1px solid red;
		z-index: 999;
		padding-top:5px;
	}
	
	#content
	{
		margin:auto;
		width:1024px;
		height:auto;
		text-align:center;
		padding-top:75px;
	}
	.box
	{
		width:220px;
		padding:2px;
		margin:5px;
		border: 1px solid #DFDFE0;
		height:auto;		
		vertical-align:center;
		text-align:center;
	}
	.column
	{
		width:250px;
		float:left;
		height:auto;
		text-align:center;
		padding:2px;	
	}
</style>
</head>
<body>
	<div id="nav">
			<div id="navdetail">
			
			</div>
			<div id="hot">
			
			</div>

	</div>

	<div id="content">
		<div class="column">
			<c:forEach items="${items}" var="item" begin="0" step="4">  
				<div class="box">
					<img width="202" height="284" src="${item.url}">
				</div>
				</c:forEach>  
			
			
			
		</div>
		
		<div class="column">		
			
				<c:forEach items="${items}" var="item" begin="1" step="4">  
				<div class="box">
					<img width="202" height="284" src="${item.url}">
				</div>
				</c:forEach>  
			
			
		</div>
		
		<div class="column">
			<c:forEach items="${items}" var="item" begin="2" step="4">  
				<div class="box">
					<img width="202" height="284" src="${item.url}">
				</div>
				</c:forEach>  
		</div>
		
		<div class="column">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<img width="202" height="284" src="${item.url}">
				</div>
				</c:forEach>  
		</div>
		
		<div clear="both"></div>
	</div>
</body>
</html>