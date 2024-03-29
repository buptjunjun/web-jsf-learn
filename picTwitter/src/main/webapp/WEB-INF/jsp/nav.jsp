<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
	System.out.println("path="+path);
%>
<script type="text/javascript">
var host = "<%=path%>"; //http://localhost:8080/picture/
$(document).ready(function (){
	$("#loginBtn").click(function(){
		if(testlogin())
			return;
		showLoginDiv();
	});
	$("#logoutBtn").click(function(){
		logoutFB();
		refresh();
	});
	refresh();
	});
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.tagselect {
	color: red;
	}
	#login
	{
		float:right;
		width:auto;
		height:auto;
		font-size: 14px;
		line-height: 20px;
		color: white;
		margin:0px;
		padding:2px;
		
	}

	#login span:hover {
		color: red;
		cursor: pointer;
	}
	
	#about
	{
		float:right;
		width:auto;
		height:auto;
		font-size: 14px;
		line-height: 20px;
		color: white;
		margin:0px;
		padding:2px;	
	}
	
	#about:hover {
		color: red;
		cursor: pointer;
	}
	
	.seperateLien
	{
		float:right;
		color: white;
		margin:0px;
		padding:2px;	
	}
	
	#userName
	{
		font-size:12px;
		margin-right:5px;
	}
	
</style>
	<jsp:include page="login.jsp"></jsp:include>
	<div id="nav">		
		<div id="navdetail">
			<div id="navcontent">
				<a href="<%=path%>"><span id="logo">Picfalls</span></a>		
				
				<c:forEach items="${tags}" var="tag">  
					<c:choose>
						<c:when test="${tag.type == currtype}">
							<a href="<%=path%>/${tag.type}"> <span class="tag tagselect">${tag.type} </span></a>
						</c:when>
						<c:otherwise>
							<a href="<%=path%>/${tag.type}"> <span class="tag">${tag.type} </span></a>
						</c:otherwise>
					</c:choose>
				</c:forEach>  	
				
				<div id="about">
					<span id="aboutTxt">ABOUT US</span>
				</div>
				<div class="seperateLien"><span>|</span></div>
				<div id="login" >
					<div style="float:left">
						<img id="userhead" style="" width="20" height="20" src="${user.pic}" onerror="$(this).prop('src','<%=path%>/resources/img/user_head.png');"/>
					</div>
					<div style="float:left">
						<span id="userName">${user.name}</span>		
						<span id="loginBtn">LOGIN</span>
						<span id="logoutBtn">LOGOUT</span>
					</div>
				</div>
			
				<div class="clear"></div>
			</div>	
		</div>			
	</div>
	

<script type="text/javascript">
refresh();
</script>