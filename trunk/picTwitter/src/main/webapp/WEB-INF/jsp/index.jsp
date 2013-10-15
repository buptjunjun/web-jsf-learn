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
<link type="text/css" rel="stylesheet" href="http://www.coderlong.com/common.css" /> 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>

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
		background-color:  #D1D1E8;
		
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
	   
	}
	#hot span:hover
	 {
		 background-color: rgb(255,111,1111);
		 color:white;
		 cursor:pointer;
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
		background-color: white;
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
		 width:100%;
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

var querying = false;
function resetquerying()
{
	querying = false;
}

function rating(id,url1)
{
	$.ajax({
			type: "post",			
			url: url1,
			data:id, 
			success:function (data)   // request success.
			{				
			}
	  });
}

var goodurl = "http://localhost:8080/picture/api/good";
function good(id,item)
{
	
	rating(id,goodurl);
}

var badurl = "http://localhost:8080/picture/api/bad";
function bad(id)
{	
	rating(id,badurl);
}

var collecturl = "http://localhost:8080/picture/api/collect";
function collect(id,item)
{	
	if(testlogin())
	{
		
		alert(id);
		$.ajax({
				type: "post",			
				url: collecturl,
				data:id, 
				success:function (data)   // request success.
				{				
					if(data=="true")
					{
						$(item).prop("class","img_background collect1");
						$(item).off();
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
	var id = $(".hiddenid").last().text();
	var searchURL = "http://localhost:8080/picture/api/item/";
	querying = true;
	setTimeout(resetquerying, 3000);
	$(function()
	{
		var url =  searchURL+id;
		$.ajax({
				type: "get",			
				url: url,
				dataType:"json",
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
						
							var $copy =$(".box").first().clone(true);
							
							var item = data[i];
							$copy.find(".hiddenid").text(item.id);
							$copy.find(".mainimg_a").prop('href',"/picture/detail/"+item.id);
							$copy.find(".mainimg").prop('src',item.url);
							$copy.find(".good span").text(item.good);
							$copy.find(".bad span").text(item.bad);
							$copy.find(".collect span").text(item.collect);
							$copy.find(".post span").text(item.comment);	
							
							var shortestColumn = "#column"+shortest; 
							$(shortestColumn).append($copy);
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
	
	// if scrollbar is within 100px of bottom loadMore content
	var span=$(document).height() - $(this).scrollTop() - $(this).height();
    if (span <10 && querying == false) 
    	loadMore();  

});  


$(function(){
	
	$(".img_background.good").on("click",function(){
		var id = $(this).parent().attr("itemid");
		good(id);
		$(this).prop("class","img_background good1");
		$(this).off();
	});

	$(".img_background.bad").on("click",function(){
		var id = $(this).parent().attr("itemid");
		bad(id);
		$(this).prop("class","img_background bad1");
		$(this).off();
	});
	
	$(".img_background.collect").on("click",function(){
		var id = $(this).parent().attr("itemid");
		collect(id,$(this));
		
	});
	
});
</script>


</head>
<body>
	<jsp:include page="nav.jsp"></jsp:include>
	<div id="hot">
				<a href="http://localhost:8080/picture/pic/${currtype}/newest"> <span class="hottag">newest</span></a>
				<a href="http://localhost:8080/picture/pic/${currtype}/weekly"> <span class="hottag">weekly</span></a>
				<a href="http://localhost:8080/picture/pic/${currtype}/monthly"> <span class="hottag">monthly</span></a>
	</div>
	<div id="content">
		<div class="column" id="column0">
			<c:forEach items="${items}" var="item" begin="0" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="/picture/detail/${item.id}?kind=${kind}" class="mainimg_a"><img class="mainimg" src="${item.url}"></a>
					<div class="comment" itemid = "${item.id}">
					  <a class="img_background good"><span>1000${item.good}  </span></a> 
					  <a class="img_background bad"><span>1000${item.bad} </span></a>  
					  <a class="img_background collect"><span>100${item.collect} </span></a>  
					  <a class="img_background post"><span>100${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column1">
			<c:forEach items="${items}" var="item" begin="1" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="/picture/detail/${item.id}?kind=${kind}" class="mainimg_a"><img class="mainimg" src="${item.url}"  /></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good""><span>1000${item.good}  </span></a> 
					  <a class="img_background bad"><span>1000${item.bad} </span></a>  
					  <a class="img_background collect"><span>100${item.collect} </span></a>  
					  <a class="img_background post"><span>100${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column2">
			<c:forEach items="${items}" var="item" begin="2" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="/picture/detail/${item.id}?kind=${kind}" class="mainimg_a"><img class="mainimg" src="${item.url}"></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good"><span>1000${item.good}  </span></a> 
					  <a class="img_background bad"><span>1000${item.bad} </span></a>  
					  <a class="img_background collect"><span>100${item.collect} </span></a>  
					  <a class="img_background post"><span>100${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="column" id="column3">
			<c:forEach items="${items}" var="item" begin="3" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="/picture/detail/${item.id}?kind=${kind}" class="mainimg_a"><img class="mainimg" src="${item.url}"></a>
					<div class="comment"  itemid = "${item.id}">
					  <a class="img_background good"><span>1000${item.good}  </span></a> 
					  <a class="img_background bad"><span>1000${item.bad} </span></a>  
					  <a class="img_background collect"><span>100${item.collect} </span></a>  
					  <a class="img_background post"><span>100${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
		
		<div class="clear"></div>
	</div>
</body>
</html>