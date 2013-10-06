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
	body
	{
		color: #333333;
	}
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
	
	.comment
	{
		 width:100%;
		 height:auto;
		 text-align:left;
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
			<c:forEach items="${uis}" var="ui" begin="0" step="4">  
				<div class="box">
					<img  src="${ui.item.url}">
					<div class="comment">
					 <a >${ui.rating.good}</a> 
					 <a >${ui.rating.bad}</a> 
					 <a > ${ui.rating.collect}</a>  
					</div>
				</div>
				
				</c:forEach>  	
		</div>
		
		
		
		<div clear="both"></div>
	</div>
</body>
</html>