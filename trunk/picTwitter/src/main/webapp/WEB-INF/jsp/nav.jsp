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
</script>

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

<script type="text/javascript" src="http://localhost/resources/jquery-1.7.1.js"></script>
	
<script type="text/javascript">

function trim(str)
{
     return str.replace(/(^\s*)|(\s*$)/g, '');
}

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

//user face book data to fill the form
function fillFormFBjson(info)
{
	var url1 = host+"/api/login";
	var mydata = '{"idSource":"' + "1000016664811391"
				+ '","source":"' + FACEBOOK
				+ '","name":"' + "Andy Yang"
				+ '","gender":"' + "m"
				+ '","pic":"' + "http://tp1.sinaimg.cn/1641153660/50/5627699277/1"
				+ '","url":"' + "http://www.baidu.com"
				+ '"}'; 
	$.ajax({
		type: "post",			
		url: url1,
		dataType:"json",
		data: mydata, 
		contentType: "application/json; charset=utf-8",  
		complete: function (data) 
		{				
			if(data!=null && data!=undefined && "ok"==data.responseText)
				loginJsonSuccess();
			refresh();
		}
		
		
  		});
	
	return false;
}

function loginJsonSuccess()
{
	$("#userhead").prop("src","http://tp1.sinaimg.cn/1641153660/50/5627699277/1");
	$("#userName").text("Andy Yang");
	
}

function fillFormFBRealJson(info)
{
	var url1 = path+"/api/login";
	var mydata = '{"idSource":"' + info.id
				+ '","source":"' + FACEBOOK
				+ '","name":"' + info.name
				+ '","gender":"' + gender
				+ '","pic":"' + "http://graph.facebook.com/"+info.id+"/picture"
				+ '","url":"' + info.link
				+ '"}'; 
	$.ajax({
		type: "post",			
		url: url1,
		dataType:"json",
		data: mydata, 
		contentType: "application/json; charset=utf-8",  
		complete: function (data) 
		{				
			if(data!=null && data!=undefined && "ok"==data.responseText)
			{
				loginJsonSuccess();
			}
			
			refresh();
		}
  		});
	
	return false;
}

function fillFormFBReal(info)
{
	$("#idSource").prop("value",info.id);
	$("#source").prop("value",FACEBOOK);
	$("#name").prop("value",info.name);
	if(info.gender == "male")		
		$("#gender").prop("value","m");
	else if(info.gender == "female")
		$("#gender").prop("value","f");
	else
		$("#gender").prop("value","o");
	
	$("#pic").prop("value","http://graph.facebook.com/"+info.id+"/picture" );
	
	$("#url").prop("value",info.link);
	$("#otherInfo").prop("value","");
	$("#user").submit();
}
$(document).ready(function (){
	$("#loginBtn").click(function(){
		loginFB();
		refresh();
	});
	$("#logoutBtn").click(function(){
		logoutFB();
		refresh();
	});
	refresh();
	});
	
function testlogin()
{
	if($("#userName").text() != undefined && $("#userName").text() != null && trim( $("#userName").text()) != "" )
		return true;
	return false;
}
function refresh()
{
	//alert($("#userName").text()); 
	if(testlogin())
	{	$("#loginBtn").hide();
		$("#logoutBtn").show();
	}
	else
	{
		$("#loginBtn").show();
		$("#logoutBtn").hide();
	}
}

function loginFB()
{
	fillFormFBjson();
}

/* function loginFB()
{
		FB.getLoginStatus(function(response) {
		  if (response.status === 'connected') 
		  {
	    	   // get the current user info and submit
	    	   FB.api('/me', function(res) {fillFormFBReal(res);});
		  } 
		  else 
		  {
			  FB.login(function(response) 
					  {
						    if (response.authResponse) 
						    {
						       if(response.status === 'connected')
						    	{
						    	   // get the current user info and submit
						    	   FB.api('/me', function(res) {fillFormFBReal(res);});
						        }  
						    } 
						    else 
						    {
						        // The person cancelled the login dialog
						    }
						});	 
		  }
		 });
		  
	} */

function logoutFB()
{
	  //FB.logout();
	  window.open(host+"/logout","_self");
}
</script>
	
<div id="fb-root"></div>
<script>
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '726510294042746', // App ID
    channelUrl : '//localhost:8080/picture/channel', // Channel File
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });

  };

  // Load the SDK asynchronously
  (function(d){
   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement('script'); js.id = id; js.async = true;
   js.src = "//connect.facebook.net/en_US/all.js";
   ref.parentNode.insertBefore(js, ref);
  }(document));

  // Here we run a very simple test of the Graph API after login is successful. 
  // This testAPI() function is only called in those cases. 
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('	, ' + response.name + '.');
    });
  }
</script>
	<div id="nav">
		<span id="kind" class="hiddenid">${kind}</span>
		<div id="navdetail">
			<div id="navcontent">
				<span id="logo">Picture Falls</span>				
				
				<c:forEach items="${tags}" var="tag">  
					<a href="<%=path%>/pic/"> <span class="tag">${tag.type} </span></a>
				</c:forEach>  	
				
				<div id="about">
					<span id="aboutTxt">ABOUT US</span>
				</div>
				<div class="seperateLien"><span>|</span></div>
				<div id="login">
					<div style="float:left">
						<img id="userhead" style="" width="20" height="20" src="${user.pic}" onerror="$(this).hide();return false;"/>
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
	
	<form name="user" id="user" class="login" style="display:none !important;" action="/picture/login" method="post">
		<input id="idSource" name="idSource"></input><br>
		<input id="source" name="source"></input><br>
		<input id="name" name="name"></input><br>
		<input id="gender" name="gender"></input><br>
		<input id="pic" name="pic"></input><br>
		<input id="url" name="url"></input><br>
		<input id="otherInfo" name="otherInfo"></input><br>
		<input type="submit" name="testSubmit" value="submit">
	</form>
<script type="text/javascript">
refresh();
</script>