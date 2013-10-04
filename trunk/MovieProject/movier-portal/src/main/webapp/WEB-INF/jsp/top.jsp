
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort();
%>
<head>
    <script type="text/javascript">
        function showTip(){
            document.getElementById("Tip").style.display="inline";
        }
    </script>
	
	<style>
		
		#top 
		{
			height:130px;
			width:1280px; 
			background:white;
			margin:auto; 
			border-bottom: 1px solid #DFDFE0;
		}
	
	.logo 
	{
		background: url("../resources/icon/logo.jpg");
		float: left;
		height: 100px;
		width:150px;
		margin: 14px 10px 0 0;
	}
	
	
	.sicon
	{
		background-image:url("../resources/icon/head-ico.png");
		width:22px;
		height:20px;
		background-position:-140px 0px;
		border:0px;
	}
	

	
	.height20
	{
		height:30px;
	}
	.width400
	{
		width:400px;
	}
	
	.login
	{
		float:left;
		margin-top:40px;
		margin-left:20px;
		width:200px;
		height:22px;
	}
	
	.fontChinese14px
	{
		font-family: "Microsoft Yahei","宋体";
		font-size:14px;
	}
	.sina
	{
		background-image:url("../icon/head-ico.png");
		width:20px;
		height:30px;
		background-position:0px -135px;
		border:0px;
	}
	
	.sina1
	{
		background-image:url("../icon/head-ico.png");
		width:20px;
		height:30px;
		background-position:0px -160px;
	}
	
	.qq
	{
		background-image:url("../icon/head-ico.png");
		width:22px;
		height:30px;
		background-position:-22px -135px;
		border:0px;
	}
	
	.qq1
	{
		background-image:url("../icon/head-ico.png");
		width:22px;
		height:30px;
		background-position:-22px -160px;
	}
	
	.renren
	{
		background-image:url("../icon/head-ico.png");
		width:30px;
		height:30px;
		background-position:-50px -135px;
		border:0px;
	}
	
	.renren1
	{
		background-image:url("../icon/head-ico.png");
		width:30px;
		height:30px;
		background-position:-50px -160px;
	}
	
	.record
	{	background-image:url("../icon/head-ico.png");
		width:20px;
		height:20px;
		background-position:0px -222px;
	}
	
	
	.sliceDownIcon
	{
		background-image:url("../icon/head-ico.png");
		width:20px;
		height:20px;
		background-position:0px -222px;
	}
	#record
	{
		width:150px;
	}
	.floatLeft
	{
		float:left;
	}
	.lg
	{	
	  margin-top:5px;;
	}
	
	.marginLeft10px
	{
		margin-left:10px;
	}
	
	.marginTop20px
	{
		margin-top:45px;
	}
	
	.search
	{
		float:left;
		margin-top:40px;
		margin-left:150px;
		width:500px;
		font-size:16px;
		
	}
	</style>
</head>

    <div id="top" >
		<a class="logo" href="#"></a>
		<div class="search">
			<form>
				<input id="query" class="height20 width400" type="text" style="" autocomplete="off" value="全网搜索影片" name="q">
				<span class="height20"> <input id="qbtn" class="sicon" type="button" onclick="" name=""></span>
			</form>
		</div>
		<div class="login fontChinese14px">
			<div id="lg " class="lg floatLeft marginLeft10px"><span>登陆</span></div>
			<div id="sina " class="sina1 floatLeft marginLeft10px"></div>
			<div id="qq" class="qq1 floatLeft marginLeft10px"></div>
			<div id="renren" class="renren1 floatLeft marginLeft10px"></div>
		</div>
		
		<div id="record" class="floatLeft marginLeft10px marginTop20px fontChinese14px">
			<div class="record floatLeft"></div>
			<span> 播放记录</span>
		</div>
	</div>
	