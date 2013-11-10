<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>
<%
	String path = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + "" + request.getContextPath();
	System.out.println("path=" + path);
%>
<jsp:include page="common.jsp"></jsp:include>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${currtag} - picture falls</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/resources/style/common.css" /> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
	
div
{
	border-radius: 5px 5px 5px 5px;
}	
</style>

<script type="text/javascript">
var host = "<%=path%>"; //http://localhost:8080<%=path%>/
var enableloadmore= "${loadmore}";

var querying = false;
function resetquerying()
{
	querying = false;
}



function loadMore()
{
	if(window.page == null || window.page == undefined)
		window.page = 3;
	page = window.page;
	
	var searchURL = host+"/api/loadmore";
	var sorttxt = $("#sort").text();
	var tagtxt = $("#currtag").text();
	
	var mydata = '{"param1":"' + tagtxt
				+ '","param2":"' + sorttxt
				+ '","param3":"' + page
				+ '"}'; 
	querying = true;
	setTimeout(resetquerying, 2000);
	$(function()
	{
		var url =  searchURL;
		$.ajax({
				type: "post",			
				url: url,
				dataType:"json",
				data:mydata,
				contentType: "application/json; charset=utf-8",  
				success:function (response)   // request success.
				{				
					if(response.status == "false")
						return;
					
					//alert(data.length);
					var data = response.data;		
					var page = parseInt(window.page);
					if(data!= null && data.length>0)
					{	
						var base = page*20;
						for(var i = 0; i< data.length;i++)
						{
							// find the shortest column
							var shortest = getShortestColum();						
						
							var $copy =$("#hiddenbox").clone(true);
							$copy.css("display","block");
							$copy.removeAttr("id");
							var item = data[i];
							var skip = base+i;
							$copy.find(".hiddenid").text(item.id);
							$copy.find(".mainimg_a").prop('href',"<%=path %>/"+item.tag+"/"+item.id+"?sort="+sorttxt+"&skip="+skip);
							$copy.find(".mainimg").prop('src',item.url);
							$copy.find(".description span").text(item.desc);
							$copy.find(".good span").text(item.good);
							$copy.find(".bad span").text(item.bad);
							$copy.find(".collect span").text(item.collect);
							$copy.find(".post span").text(item.comment);	
							$copy.find(".comment").attr("itemid",item.id);
							var shortestColumn = "#column"+shortest; 
							$(shortestColumn).append($copy);							
						}
						
						window.page = page+1;
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
    if (span <500 && querying == false) 
    	loadMore();  

});  



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
<script src="<%=path%>/resources/script/common.js" type="text/javascript"></script>

</head>
<body>
	<span id="sort" class="hiddenid">${sort}</span>
	<span id="currtag" class="hiddenid">${currtag}</span>
	<jsp:include page="nav.jsp"></jsp:include>
	<div id="hot">
		<c:forEach items="${sorts}" var="item">
			<c:choose>
				<c:when test="${item == sort}">
					<a href="<%=path %>/${currtag}?sort=${item}"> <span class="hottag hottagSelect">${item}</span></a>
				</c:when>
				<c:otherwise>
					<a href="<%=path %>/${currtag}?sort=${item}"> <span class="hottag">${item}</span></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<div id="content">
	<div class="box" id="hiddenbox" style="display:none">
		<span class="hiddenid">${item.id}</span>
		<a href="<%=path%>/${item.tag}/${item.id}?sort=${sort}&skip=${vs.count}" class="mainimg_a"><img width=200  class="mainimg" src="${item.url}"  /></a>
		<div class="description">
			<span>${item.desc}</span>
		</div>
		<div class="comment"  itemid = "${item.id}">
		  <a class="img_background good""><span>${item.good}  </span></a> 
		  <a class="img_background bad"><span>${item.bad} </span></a>  
		 <%--  <a class="img_background collect"><span>${item.collect} </span></a>   --%>
		  <a class="img_background post"><span>${item.comment} </span></a> 
	</div>
	
	</div>			
		<div class="column" id="column0">
			<c:forEach items="${items}" varStatus="vs" var="item" begin="0" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/${item.tag}/${item.id}?sort=${sort}&skip=${0+(vs.count-1)*4}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"></a>
					<div class="description">
						<span>${item.desc}</span>
					</div>
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
			<c:forEach items="${items}" varStatus="vs" var="item" begin="1" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/${item.tag}/${item.id}?sort=${sort}&skip=${1+(vs.count-1)*4}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"  /></a>
					<div class="description">
						<span>${item.desc}</span>
					</div>
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
			<c:forEach items="${items}" varStatus="vs" var="item" begin="2" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/${item.tag}/${item.id}?sort=${sort}&skip=${2+(vs.count-1)*4}" class="mainimg_a"><img  width=200 class="mainimg" src="${item.url}"></a>
					<div class="description">
						<span>${item.desc}</span>
					</div>
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
			<c:forEach items="${items}" varStatus="vs" var="item" begin="3" step="4">  
				<div class="box">
					<span class="hiddenid">${item.id}</span>
					<a href="<%=path%>/${item.tag}/${item.id}?sort=${sort}&skip=${3+(vs.count-1)*4}" class="mainimg_a"><img width=200 class="mainimg" src="${item.url}"></a>
					<div class="description">
						<span>${item.desc}</span>
					</div>
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