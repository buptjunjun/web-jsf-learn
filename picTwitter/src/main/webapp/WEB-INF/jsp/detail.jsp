<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pic galaxy</title>

<style type="text/css">
	ul 
	{
		list-style:none;
		margin:0px;
		padding:0px;
	}
	ul li
	{
		margin:0px;
		padding:2px;
	}
	
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
		height:80px;
		z-index: 999;
		position:fixed;
		padding-bottom:10px;
		
	}
	#navdetail
	{
		padding:5px;
		width:100%;
		margin:auto;
		height:auto;
		z-index: 999;
		background-color: rgb(255,111,1111);
	}
	#navcontent
	{
		width:1024px;
		margin:auto;
		padding-top:6px;
	}
	#logo
	{
	    font-size: 24px;
	    line-height: 20px;
	    color:white;
	}
	
	.tag
	{

		margin-left:20px;
		font-size: 16px;
	    line-height: 20px;
	    color:white;
	    padding:5px;
	}
	.tag:hover
	{
		color:red;
	}
	
	
	#content
	{
		margin:auto;
		width:1024px;
		height:auto;
		text-align:center;
		padding-top:15px;
		padding-left:5px;
		background-color: white;
	}
	#left
	{
	 width:700px;
	 height:auto;
	 float:left;
	 border:1px solid #D9D9D6
	}
	#right
	{
	 width:300px;
	 height:auto;
	 float:left;
	 	border: 1px  black;
	}
	#comment
	{
		width:680px;
		margin:auto;
		
		
	}
	
	.cmtContex
	{
		height:50px;
		width:500px;
	}
	#submit
	{
		width:560px;
		height:60px;
		margin:auto;
		
	}
	
	#submitBtn
	{
	    
	 -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #334455;
    background-image: -moz-linear-gradient(center top , #334455, #334455);
    background-repeat: repeat-x;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    border-image: none;
    border-radius: 5px 5px 5px 5px;
    border-style: ;
    border-width: 1px;
    box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05);
    color: #FFFFFF;
    cursor: pointer;
    display: inline-block;
    float: right;
    font-size: 16px;
    font-weight:bold;
    height: 34px;
    line-height: 18px;
    margin-bottom: 0;
    padding: 4px 10px;
    text-align: center;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
    vertical-align: middle;
    width: 65px;
	}
	
	#comments
	{
		width:680px;
		margin:auto;
		padding-bottom:30px;
	
	}
	.oneComment
	{
		width:575px;
		height:auto;
		border-bottom: 1px dotted #D9D9D6;
		margin:auto;
		padding-top:10px;
	}
	#pic
	{
		padding-top:10px;
	}
	.userPic
	{	
		display:inline;
		width:60px;
		float:left;
		
	}
	.usercomment
	{
		height:55px;
		width:450px;
		float:left;
		text-align:left;
		
	}
	
	#bread
	{
		margin:auto;
		padding-top:55px;
		font-size:15px;
		text-align:left;
		width:1024px;
		height:30px;
		color: rgb(255,111,1111);
		border-bottom:1px solid #D9D9D6
	}
	.breadItem
	{
		padding-left:2px;
		padding-top:1px;
		padding-bottom:1px;
		color:white;
	}
	.breadItem:hover
	{
		color:white;
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
	</div>

	<div id="bread">
		<span style="font-size:16px; font-weight:bold;">current position:</span>
		<span class="breadItem">${item.type}></span>
	</div>
	<div id="content">
		<div id="left">
			<div id="pic">
				<img width=600  src="${item.url1 }"/>
				<p>${item.desc }</p>
			</div>
			
			<div id="comment">
				<img width=50 height=50 src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
				<textarea class="cmtContex" id="comment-box" placeholder="say something..." selectionstart="0" selectionend="0"></textarea>
				<div id="submit">
					<button id="submitBtn">submit</button>
				</div>		
			</div>
			
			<div id="comments">				
				<c:forEach items="${comments}" var="comment" >  			
					<div class="oneComment">
						<div class="userPic">
							<img width=50 height=50  src="${comment.user.pic}" />
							<div style="clear:both"></div>
						</div>
						<div class="usercomment">
							<ul>
								<li>
									<span>${comment.user.name}:</span> 
									<span>${comment.comment.comment}</span>
								</li>
								<li>
									<span>${comment.comment.formatDate}</span> 
								</li>
							</ul>
							<div style="clear:both"></div>
						</div>
						<div style="clear:both"></div>
					</div>		
			</c:forEach> 
			</div>
			<div style="clear:both"></div>
		</div>

		<div id="right">
			
		</div>
		
		<div style="clear:both"></div>
	</div>
</body>
</html>