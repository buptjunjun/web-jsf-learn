<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>
<%
	String path = "http://" + request.getServerName() + ":"+ request.getServerPort() + "" + request.getContextPath();
%>
<html>
<head>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/themes/base/jquery-ui.css" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js" type="text/javascript"></script>
<%--
<script src="<%=path%>/resources/script/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.mouse.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.draggable.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.position.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.resizable.js"></script>
<script src="<%=path%>/resources/script/ui/jquery.ui.dialog.js"></script>  --%>


<style>
	.demo-description {
		clear: both;
		padding: 12px;
		font-size: 1.3em;
		line-height: 1.4em;
		}

		.ui-draggable, .ui-droppable {
			background-position: top;
		}
		
		#dialogLogin
		{
			border-radius:	0px;
			border:1px solid #F5F5F5;
			background:none repeat scroll 0 0 #F5F5F5;
		}
		
		.loginItem
		{
			border-radius:5px;
			margin:auto;
			width:260px;
			height:40px;
			border:1px solid black;
			background:#3B5998;
			padding:2px;		
			text-align:center;
			font-size:16px;	
			margin-top:25px;	
		}	

		.loginItemLeft
		{
			float:left;
		}
		
		.loginItemRight
		{
			float:left;
			color:white;
			margin-top:8px;
		}
		
		.colorFB
		{
			background:#3B5998;
		}
		
		.loginItem:hover
		{
			cursor:pointer;
		}
		
		.ui-dialog-titlebar {height:20px;}
		.ui-widget-header { background: rgb(255, 111, 1111); box-shadow: 0 0 4px rgba(250,0,0,0.5); color:white;}
		
	</style>
	
	
	
<script type="text/javascript">


function trim(str)
{
     return str.replace(/(^\s*)|(\s*$)/g, '');
}

	
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
	var url1 = host+"/api/login";
	var mydata = '{"idSource":"' + info.id
				+ '","source":"' + FACEBOOK
				+ '","name":"' + info.name
				+ '","gender":"' + info.gender
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

/* function loginFB()
{
	//fillFormFBjson(); //for test
	fillFormFBRealJson();
} */

 function loginFB()
{
		FB.getLoginStatus(function(response) {
		  if (response.status === 'connected') 
		  {
	    	   // get the current user info and submit
	    	   FB.api('/me', function(res) {fillFormFBRealJson(res);});
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
						    	   FB.api('/me', function(res) {fillFormFBRealJson(res);});
						        }  
						    } 
						    else 
						    {
						        // The person cancelled the login dialog
						    }
						});	 
		  }
		 });
		  
	} 

function logoutFB()
{
	  //FB.logout();
	  window.open(host+"/logout","_self");
}


function showLoginDiv()
{		
	$( "#dialogLogin" ).dialog({
		width:300,
		height:150,
		modal: true
	});
}

function hideLoginDiv()
{		
	$( "#dialogLogin" ).dialog("close");
}

$(document).ready(function (){
	$("#loginItemFB").click(function(){
		loginFB();
		refresh();
		hideLoginDiv();
	});
	refresh();
	});
	
</script>
	
<div id="dialogLogin" style="display:none" title="Signin">
	<%-- <div class="loginItem" id="loginItemFB"><img width=160 height=40 src="<%=path %>/resources/img/btn-login-facebook.png"/></div>	 --%>
	<div class="loginItem" id="loginItemFB">
			<div class="loginItemLeft"><img width=40 height=40  src="<%=path%>/resources/img/FaceBook_256x256.png"  onerror="$(this).prop('src','<%=path%>/resources/img/user_head.png')"></img></div>		
			<div class="loginItemRight"><span>SIGN IN WITH FACEBOOK</span></div>
	</div>
</div>

	
<div id="fb-root"></div>
<script>
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '726510294042746', // App ID
    channelUrl : '//localhost:8080/picture/channel', // Channel File
    status     : true, // check login status
    cookie     : false, // enable cookies to allow the server to access the session
    xfbml      : true,  // parse XFBML
    oauth      : true
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
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-45452589-1', 'picfalls.com');
  ga('send', 'pageview');

</script>
</head>

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
</html>