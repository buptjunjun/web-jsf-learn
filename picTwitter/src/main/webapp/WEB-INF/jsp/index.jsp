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
<link type="text/css" rel="stylesheet" href="http://localhost:8080/picture/resources/style/common.css" />  
<script type="text/javascript" src="http://localhost:8080/picture/resources/script/jquery-1.7.1.js"></script>
<script type="text/javascript">

var querying = false;
function resetquerying()
{
	querying = false;
}


function loadMore()
{
	var id = $(".hiddenid").last().text();
	var searchURL = "http://localhost:8080/picture/api/";
	querying = true;
	 setTimeout("resetquerying()", 3000);
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
					 var $columns = $(".column");
					
					
					//alert(data.length);
					
					if(data!= null && data.length>0)
					{	
						for(var i = 0; i< data.length;i++)
						{
							// find the shortest column
							var shortest = 0;
							for(var j = 0; j < $columns.length; j++)
							{
								if($columns[j].height > shortest)	
									shortest = j;
							}	
							
							var $copy =$(".box").first().clone(true);
							
							var item = data[i];
							$copy.find(".hiddenid").text(item.id);
							$copy.find(".mainimg_a").prop('href',"/picture/detail/"+item.id);
							$copy.find(".mainimg").prop('src',item.url);
							$copy.find(".good span").text(item.good);
							$copy.find(".bad span").text(item.bad);
							$copy.find(".collect span").text(item.collect);
							$copy.find(".post span").text(item.comment);							
							$("#column1").append($copy);
						}
					} 
				}
		  });
	 }
	);
	
}

$(window).scroll(function(){  
	
	// if scrollbar is within 100px of bottom loadMore content
	var span=$(document).height() - $(this).scrollTop() - $(this).height();
    if (span <400 && querying == false) 
    	loadMore();  

});  
</script>


<style type="text/css">
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
	
	#hot
	{
		
		margin:auto;
		width:1024px;
		height:50px;
		z-index: 999;
		padding-top:15px;
		background-color: rgb(202,201,196);
		
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
	#hot  span:hover
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
		padding-top:100px;
	}
	.box
	{
		width:220px;
		padding:2px;
		margin:5px;
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
	
	.hiddenid
	{
		display:none;
	}
	.mainimg_a
	{
	}
	
	.clear
	{
		clear:both;
	}
	
</style>
</head>
<body>
	<div id="nav">
			<div id="navdetail">
				<div id="navcontent">
					<span id="logo">Picture Falls</span>				
					
					<c:forEach items="${tags}" var="tag">  
						<a href="http://localhost:8080/picture/pic/${tag.type}/weekly"> <span class="tag">${tag.type} </span></a>
					</c:forEach>  	
				</div>
				
			</div>
			<div id="hot">
				<a href="http://localhost:8080/picture/pic/${currtype}/newest"> <span class="hottag">newest</span></a>
				<a href="http://localhost:8080/picture/pic/${currtype}/weekly"> <span class="hottag">weekly</span></a>
				<a href="http://localhost:8080/picture/pic/${currtype}/monthly"> <span class="hottag">monthly</span></a>
			</div>

	</div>

	<div id="content">
		<div class="column" id="column0">
			<c:forEach items="${items}" var="item" begin="0" step="1">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="/picture/detail/${item.id}" class="mainimg_a"><img class="mainimg" src="${item.url}"></a>
					<div class="comment">
					  <a class="img_background good"><span>1000${item.good}  </span></a> 
					  <a class="img_background bad"><span>1000${item.bad} </span></a>  
					  <a class="img_background collect"><span>100${item.collect} </span></a>  
					  <a class="img_background post"><span>100${item.comment} </span></a> 
					</div>
				</div>				
			</c:forEach> 
		</div>
	<div class="column" id="column1">
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>