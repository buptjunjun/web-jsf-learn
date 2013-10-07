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
		background-color: rgb(202,201,196);
	    color: #333333;
	    font-family: "Helvetica Neue",Helvetica,STheiti,微软雅黑,宋体,Arial,Tahoma,sans-serif,serif;
	    font-size: 14px;
	    line-height: 20px;
	    margin: 0;
	}
	
	#nav
	{
		top:0px;
		width:100%;
		height:70px;
		z-index: 999;
		position:fixed;
		padding-bottom:10px;
		
	}
	#navdetail
	{
		padding-top:5px;
		width:100%;
		margin:auto;
		height:35px;
		z-index: 999;
		background-color: rgb(255,111,1111);
	}
	
	#logo
	{
	    font-size: 20px;
	    line-height: 20px;
	    color:white;
	}
	
	#hot
	{
		
		margin:auto;
		width:1024px;
		height:50px;
		z-index: 999;
		padding-top:15px;
		background-color: rgb(202,201,196);
		
	}
	.hottag
	{
		vertical-align:bottom;
		font-size: 18px;
		margin-left:10px;
	    line-height: 20px;
	    font-weight:bold;
	    color:#9A9D9F;
	    padding:5px;
	    border:1px white solid;
	   
	}
	#hot  span:hover
	 {
		 background-color: rgb(255,111,1111);
		 color:white;
	 }
	
	#content
	{
		margin:auto;
		width:1024px;
		height:auto;
		text-align:center;
		padding-top:100px;
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
		background-color: white;
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
	
	#navcontent
	{
		width:1024px;
		margin:auto;
		padding-top:12px;
	}
	
	.tag
	{

		margin-left:20px;
		font-size: 15px;
	    line-height: 20px;
	    color:white;
	    padding:5px;
	}
	.tag:hover
	{
		 background-color: rgb(202,201,196);
	}
	
	.mainimg
	{
		margin-top:10px;
	}
	
</style>
</head>
<body>
	<div id="nav">
			<div id="navdetail">
				<div id="navcontent">
					<span id="logo">Picture Falls</span>				
					
					<c:forEach items="${tags}" var="tag">  
						<span class="tag">${tag.type} </span>
					</c:forEach>  	
				</div>
				
			</div>
			<div id="hot">
				<span class="hottag">newest</span>
				<span class="hottag">weekly</span>
				<span class="hottag">monthly</span>
			</div>

	</div>

	<div id="content">
		<div class="column">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<a href="/picture/detail/${item.id}"><img class="mainimg" src="${item.url}"></a>
					<div class="comment">
					 <a >${item.good}</a> 
					 <a >${item.bad}</a> 
					 <a >${item.collect}</a>  
					</div>
				</div>				
			</c:forEach> 
		</div>
				<div class="column">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<a href="/detail/${item.id}"><img class="mainimg" src="${item.url}"></a>
					<div class="comment">
					 <a >${item.good}</a> 
					 <a >${item.bad}</a> 
					 <a >${item.collect}</a>  
					</div>
				</div>				
			</c:forEach> 
		</div>
				<div class="column">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<a href="/detail/${item.id}"><img class="mainimg" src="${item.url}"></a>
					<div class="comment">
					 <a >${item.good}</a> 
					 <a >${item.bad}</a> 
					 <a >${item.collect}</a>  
					</div>
				</div>				
			</c:forEach> 
		</div>
				<div class="column">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<a href="/detail/${item.id}"><img class="mainimg" src="${item.url}"></a>
					<div class="comment">
					 <a >${item.good}</a> 
					 <a >${item.bad}</a> 
					 <a >${item.collect}</a>  
					</div>
				</div>				
			</c:forEach> 
		</div>
		<div style="clear:both"></div>
	</div>
</body>
</html>