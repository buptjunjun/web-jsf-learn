<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#login
	{
		float:right;
		width:auto;
		height:auto;
		font-size: 14px;
		line-height: 20px;
		color: white;
		margin:0px;
		padding:0px;
	}

	#login:hover {
		color: red;
		cursor: pointer;
	}
	
</style>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
	
<script type="text/javascript">
var FACEBOOK = "3";
// user face book data to fill the form
function fillFormFB(info)
{
	$("#idSource").prop("value","1000016664811391");
	$("#source").prop("value",FACEBOOK);
	$("#name").prop("value","Andy Yang");
	$("#gender").prop("value","m");
	$("#pic").prop("value","http://tp1.sinaimg.cn/1641153660/50/5627699277/1" );
	$("#url").prop("value","http://www.baidu.com");
	$("#otherInfo").prop("value",info);
	$("#user").submit();
}
function fillFormFBReal(info)
{
	$("#idSource").prop("value",info.id);
	$("#source").prop("value",FACEBOOK);
	
	if(info.gender == "male")		
		$("#gender").prop("value","m");
	else if(info.gender == "female")
		$("#gender").prop("value","f");
	else
		$("#gender").prop("value","o");
	
	$("#pic").prop("value","http://graph.facebook.com/100001666481139/picture" );
	
	$("#url").prop("value","http://www.baidu.com");
	$("#otherInfo").prop("value",info);
	$("#user").submit();
}
$(document).ready(function (){
	$("#loginBtn").click(function(){fillFormFB();
	refresh();
	});});
function refresh()
{
	//alert($("#userName").text()); 
	if($("#userName").text() != undefined && $("#userName").text() != null&&$("#userName").text() != "" )
		$("#loginBtn").hide();
	else
	{
		$("#loginBtn").show();
	}
}
	
</script>
	<div id="nav">
		<span id="kind" class="hiddenid">${kind}</span>
		<div id="navdetail">
			<div id="navcontent">
				<span id="logo">Picture Falls</span>				
				
				<c:forEach items="${tags}" var="tag">  
					<a href="http://localhost:8080/picture/pic/${tag.type}/weekly"> <span class="tag">${tag.type} </span></a>
				</c:forEach>  	
				<div id="login">
					<div class="userInfo">
						<img style="" width="25" height="25" src="${user.pic}"/>
						<span id="userName">${user.name}</span>
					</div>				
					<span id="loginBtn">login</span>
				</div>
				<div class="clear"></div>
			</div>	
		</div>			
	</div>
	
	<form name="user" id="user" class="login" style="display:none !important;" action="/picture/login" method="post">
	<input id="idSource" name="idSource"></input><br>
	<input id="source" name="source"></input><br>
	<input id="name" name="name"></input><br>
	<input id="gender" name="gender"></input><br>
	<input id="pic" name="pic"></input><br>
	<input id="url" name="url"></input><br>
	<input id="otherInfo" name="otherInfo"></input><br>
	<input type="submit" name="testSubmit" value="注册">
</form>
<script type="text/javascript">
refresh();
</script>