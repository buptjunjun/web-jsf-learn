<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>pic galaxy</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="http://www.coderlong.com/common.css" />  
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
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
	
	#content
	{
		margin:auto;
		width:1024px;
		min-height:800px;
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
	 min-height:768px;
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
    font-size: 14px;
    font-weight:bold;
    height: 34px;
    line-height: 10px;
    margin-bottom: 0;
    padding: 2px 5px;
    text-align: center;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
    vertical-align: middle;
    width: 70px;
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
		color: red;
		cursor:pointer;
	}
	
	
	#arrow
	{
		top: 300px;
		width: 700px;
		height: auto;
		z-index: 999;
		position: fixed;
		font-size:40px;
		color:red;
	}
	
	#arrayleft
	{
		float:left;
		width:32;
		height:48;
		margin-left:2px;
	}
	
	#arrayright
	{
		float:right;
		width:32;
		height:48;
		margin-right:2px;
		
	}
	

</style>


<script type="text/javascript">
$(document).ready(function ()
	{ 
		$("#arrayleft").mouseover(function(){ $(this).attr("class","img_array_background leftarrow1");});
		$("#arrayleft").mouseleave(function(){ $(this).attr("class","img_array_background leftarrow");});
		
		$("#arrayright").mouseover(function(){ $(this).attr("class","img_array_background rightarrow1");});
		$("#arrayright").mouseleave(function(){ $(this).attr("class","img_array_background rightarrow");});	
		
		$("#arrayleft").click(function(){prev();});
		
		$("#arrayright").click(function(){next();});
		
		
		$("#bigpic").click(function(){next();});
	});
	
/* $(window).scroll(function(){  
	
	// if scrollbar is within 100px of bottom loadMore content
	var scrollTop =  $(this).scrollTop();
	var picHeight= $("#pic").height();
	//console.log(picHeight+"   "+scrollTop );
    if(picHeight-scrollTop < 200)
    {
    	$("#arrow").hide();
    }
    else
    {
    	$("#arrow").show();
    } 

});   */
var host = "http://www.coderlong.com"
var urlpre =host+ "/picture/detail/pre/";
function prev()
{
	var id = $(".hiddenid").last().text();
	var url = urlpre+id+"?kind="+$("#kind").text();
	window.open(url,"_self");
}

var urlnext = host+"/picture/detail/next/";
function next()
{
	var id = $(".hiddenid").last().text();
	var url = urlnext+id+"?kind="+$("#kind").text();
	window.open(url,"_self");
}

</script>
</head>
<body>
	<span id="kind" class="hiddenid">${kind}</span>
	<jsp:include page="nav.jsp"></jsp:include>
	<div id="bread">
		<span style="font-size:12px; font-weight:bold;">current position:</span>
		<a  class="breadItem"><span>main ></span></a>
		<a  class="breadItem" href="http://localhost:8080/picture/pic/${item.type}/weekly" ><span>${item.type} ></span></a>
	</div>
	<div id="content">
		<div id="left">
			<div id="pic">
				<div id="arrow">			
					<div id="arrayleft" class="img_array_background leftarrow" title="previous one">	</div>
					<div id="arrayright" class="img_array_background rightarrow" title="next one"></div>
					<div style="clear:both"></div>
				</div>
				<span class="hiddenid">${item.id}</span>
				<img id="bigpic" width=600  src="${item.url1 }"/>
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