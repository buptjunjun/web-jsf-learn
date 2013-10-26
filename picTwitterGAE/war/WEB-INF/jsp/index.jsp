<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + "" + request.getContextPath();
	System.out.println("path=" + path);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pic galaxy</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/resources/style/common.css" /> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>

<style type="text/css">
	
	div
	{
	border-radius: 5px 5px 5px 5px;
	}
	
	#hot
	{		
		margin:auto;
		width:1024px;
		height:10px;
		z-index: 999;
		padding-top:60px;
		
	}
	.hottag
	{
		vertical-align:bottom;
		font-size: 15px;
		margin-left:10px;
	    line-height: 20px;
	    font-weight:bold;
	    color:#9A9D9F;
	    padding:5px;
	    border:1px white solid;
	    border:1px solid;
	   
	}
	#hot span:hover
	 {
		 background-color: rgb(255,111,1111);
		 color:white;
		 cursor:pointer;
	 }
	.hottagSelect
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
		padding-top:10px;
		overflow:hidden;
	}
	.box
	{
		width:220px;
		padding:2px;
		margin:5px;
		margin-top:20px;
		border: 1px solid #DFDFE0;
		height:auto;		
		vertical-align:center;
		text-align:center;		
		background:white;
   		box-shadow: 0 1px 1px #9C9C9C;
	}
	.column
	{
		width:250px;
		float:left;
		height:auto;
		text-align:center;
		padding:2px;	
		overflow:hidden;
	}
	
	.comment
	{
		 width:%;
		 height:auto;
		 text-align:left;
		 padding:2px;
		 margin-left:10px;
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
		 cursor:pointer;
	}
	
	.mainimg
	{
		margin-top:10px;
	}
	
	.comment span
	{
		margin-left:18px;
		font-size:11px;
	}
	.comment a:hover
	{
		color:red;
		cursor:pointer;
	}
	

	.mainimg_a
	{
	}
	
	.clear
	{
		clear:both;
	}
	
</style>

<script type="text/javascript">
var host = "<%=path%>"; //http://localhost:8080<%=path%>/

var querying = false;
function resetquerying()
{
	querying = false;
}

function rating(id,url1)
{
	$.ajax({
			type: "post",	
			contentType: "text/plain; charset=utf-8",  
			url: url1,
			data:id, 
			success:function (data)   // request success.
			{				
			}
	  });
}

var goodurl = host+"/api/good";
function good(id)
{
	
	rating(id,goodurl);
}

var badurl = host+"/api/bad";
function bad(id)
{	
	rating(id,badurl);
}

var collecturl = host+"/api/collect";
function collect(id,item)
{	
	if(testlogin())
	{
		
		alert(id);
		$.ajax({
				type: "post",		
				contentType: "text/plain; charset=utf-8",  
				url: collecturl,
				data:id, 
				success:function (data)   // request success.
				{				
					if(data=="true")
					{
						$(item).prop("class","img_background collect1");
						$(item).off();
						add(item);
					}
					else
					{
						alert("fail");	
					}
				}
		  });
	}
	else
	{
		alert("please login firstly!");	
	}
}

function loadMore()
{
	if(window.lastID == null || window.lastID == undefined)
		window.lastID = $(".hiddenid").last().text();
	id = window.lastID;
	
	var searchURL = host+"/api/loadmore";
	var kindtxt = $("#kind").text();
	var typetxt = $("#currtype").text();
	
	var mydata = '{"type":"' + typetxt
	+ '","kind":"' + kindtxt
	+ '","id":"' + id
	+ '"}'; 
	querying = true;
	setTimeout(resetquerying, 3000);
	$(function()
	{
		var url =  searchURL;
		$.ajax({
				type: "post",			
				url: url,
				dataType:"json",
				data:mydata,
				contentType: "application/json; charset=utf-8",  
				success:function (data)   // request success.
				{				
					//alert(data.length);
					
					if(data!= null && data.length>0)
					{	
						for(var i = 0; i< data.length;i++)
						{
							// find the shortest column
							var shortest = getShortestColum();						
						
							var $copy =$("#hiddenbox").clone(true);
							$copy.css("display","block");
							$copy.removeAttr("id");
							var item = data[i];
							$copy.find(".hiddenid").text(item.id);
							$copy.find(".mainimg_a").prop('href',"<%=path%>/detail/"+item.id);
							$copy.find(".mainimg").prop('src',item.url);
							$copy.find(".good span").text(item.good);
							$copy.find(".bad span").text(item.bad);
							$copy.find(".collect span").text(item.collect);
							$copy.find(".post span").text(item.comment);	
							
							var shortestColumn = "#column"+shortest; 
							$(shortestColumn).append($copy);
							window.lastID = item.id; 
						}
					} 
				}
		  });
	 }
	);
	
}


function getShortestColum()
{
	var height0 = $("#column0").height();
	var height1 = $("#column1").height();
	var height2 = $("#column2").height();
	var height3 = $("#column3").height();
	var heights = [height0,height1,height2,height3];
	
	var shortest = height0;
	var hold = 0;
	for(var i = 0;i < heights.length;i++)
	{
		if(heights[i]<shortest)
		{
			shortest = heights[i];
			hold = i;
		}	
	}
	return hold;
}
$(window).scroll(function(){  
	
	// if scrollbar is within px of bottom loadMore content
	var span=$(document).height() - $(this).scrollTop() - $(this).height();
    if (span <10 && querying == false) 
    	loadMore();  

});  


function add(item)
{
	var $span = $(item).children()[0];
	var current = $($span).text();
	$($span).text(parseInt(current)+1);	
}


$(function(){
	
	$(".img_background.good").on("click",function(){
		var id = $(this).parent().attr("itemid");
		good(id);
		add(this);
		$(this).prop("class","img_background good1");
		$(this).off();
	}).on("mouseover",function()
			{
			$(this).prop("class","img_background good1");
	}).on("mouseleave",function()
			{
		$(this).prop("class","img_background good");
	});

	$(".img_background.bad").on("click",function(){
		var id = $(this).parent().attr("itemid");
		bad(id);
		add(this);
		$(this).prop("class","img_background bad1");
		$(this).off();
	}).on("mouseover",function()
			{
		$(this).prop("class","img_background bad1");
	}).on("mouseleave",function()
			{
		$(this).prop("class","img_background bad");
	});
	
	$(".img_background.collect").on("click",function(){
		var id = $(this).parent().attr("itemid");
		collect(id,$(this));
	}).on("mouseover",function()
			{
			$(this).prop("class","img_background collect1");
	}).on("mouseleave",function()
			{
		$(this).prop("class","img_background collect");
	});
	
});
</script>


</head>
<body>
	<span id="kind" class="hiddenid">${kind}</span>
	<span id="currtype" class="hiddenid">${currtype}</span>
	<jsp:include page="nav.jsp"></jsp:include>
	<div id="hot">
		<c:forEach items="${kinds}" var="item">
			<c:choose>
				<c:when test="${item == kind}">
					<a href="<%=path %>/${currtype}/${item}"> <span class="hottag hottagSelect">${item}</span></a>
				</c:when>
				<c:otherwise>
					<a href="<%=path %>/${currtype}/${item}"> <span class="hottag">${item}</span></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<div id="content">
	<div class="box" id="hiddenbox" style="display:none">
		<span class="hiddenid">${item.id}</span>
		<a href="<%=path%>/detail/${item.id}?kind=${kind}" class="mainimg_a"><img width=200  class="mainimg" src="${item.url}"  /></a>
		<div class="comment"  itemid = "${item.id}">
		  <a class="img_background good""><span>${item.good}  </span></a> 
		  <a class="img_background bad"><span>${item.bad} </span></a>  
		  <a class="img_background collect"><span>${item.collect} </span></a>  
		  <a class="img_background post"><span>${item.comment} </span></a> 
	</div>
	
	</div>			
		<div class="column" id="column0">
			<c:forEach items="${items}" var="item" begin="0" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/detail/${item.id}?kind=${kind}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"></a>
					<div class="comment" itemid = "${item.id}">
					  <a class="img_background good"><span>${item.good}  </span></a> 
					  <a class="img_background bad"><span>${item.bad} </span></a>  
					 <%--  <a class="img_background collect"><span>${item.collect} </span></a>   --%>
					  <a class="img_background post"><span>${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column1">
			<c:forEach items="${items}" var="item" begin="1" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/detail/${item.id}?kind=${kind}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"  /></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good""><span>${item.good}  </span></a> 
					  <a class="img_background bad"><span>${item.bad} </span></a>  
					 <%--  <a class="img_background collect"><span>${item.collect} </span></a>   --%>
					  <a class="img_background post"><span>${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column2">
			<c:forEach items="${items}" var="item" begin="2" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/detail/${item.id}?kind=${kind}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good"><span>${item.good}  </span></a> 
					  <a class="img_background bad"><span>${item.bad} </span></a>  
					 <%--  <a class="img_background collect"><span>${item.collect} </span></a>   --%>
					  <a class="img_background post"><span>${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column3">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/detail/${item.id}?kind=${kind}" class="mainimg_a"><img width=200 class="mainimg" src="${item.url}"></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good"><span>${item.good}  </span></a> 
					  <a class="img_background bad"><span>${item.bad} </span></a>  
					<%--   <a class="img_background collect"><span>${item.collect} </span></a>   --%>
					  <a class="img_background post"><span>${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="clear"></div>
	</div>
</body>
</html>