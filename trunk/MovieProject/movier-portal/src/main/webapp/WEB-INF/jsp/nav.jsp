
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
		
		#nav
		{
			width:1280px; 					
			background: url("../resources/icon/bg.gif") repeat-x scroll ;
			height:38px;
			margin:auto;
			border-bottom: 1px solid #DFDFE0;
		}
		
				
		#nav ul li:hover /* 当有鼠标悬停在链接上 */
		{     
			border-bottom: 2px solid #0287CA;
			font-weight:bold;
			cursor: pointer;
		}  
		
		.nav ul li
		{
		float:left;
		font-family: "Microsoft Yahei","宋体";
		color: #0287CA;
		font-size:20px;
		font-weight:bold;
		padding:5px;
		margin-left:30px;
		margin-top:4px;
		border: 1px solid #DFDFE0;
		text-align:center;
		}
	
	</style>
</head>
	<div id="nav" class="nav">
		<ul>
			<li>电影</li> <li>连续剧</li>
		</ul>
	</div>