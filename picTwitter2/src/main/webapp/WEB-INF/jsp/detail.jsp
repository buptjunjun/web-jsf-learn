<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
	System.out.println("path="+path);
%>
<jsp:include page="common.jsp"></jsp:include>
<html>
<head>
<title>pic galaxy</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<%=path %>/resources/style/common.css" />
<script>
	var host = "<%=path%>"; //http://localhost:8080ture/
</script>
 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/script/common.js" type="text/javascript"></script>

<style type="text/css">


div
{
	border-radius: 5px 5px 5px 5px;
}
ul {
	list-style: none;
	margin: 0px;
	padding: 0px;
}

ul li {
	margin: 0px;
	padding: 2px;
}

#content {
	margin: auto;
	width: 1024px;
	min-height: 800px;
	height: auto;
	text-align: center;
	padding-top: 15px;
	padding-left: 5px;
	background-color: white;
}

#left {
	width: 700px;
	height: auto;
	min-height: 768px;
	float: left;
	border: solid 1px #F5F5F5;
}



#right {
	margin-left:10px;
	width: 300px;
	height: auto;
	float: left;
	border: 1px black;
	border: solid 1px #F5F5F5;
}

#detailComment {
	width: 680px;
	margin: auto;
	border-bottom:solid 1px #F5F5F5;
	float:left;
	padding-bottom:20px;
	
}

.cmtContex {
	height: 50px;
	width: 550px;
	margin-left:10px;
}

#submit {
	width: 560px;
	height: 60px;
	margin: auto;
}

#submitBtn {
	-moz-border-bottom-colors: none;
	-moz-border-left-colors: none;
	-moz-border-right-colors: none;
	-moz-border-top-colors: none;
	background-color: #334455;
	background-image: -moz-linear-gradient(center top, #334455, #334455);
	background-repeat: repeat-x;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	border-image: none;
	border-radius: 5px 5px 5px 5px;
	border-style:;
	border-width: 1px;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px
		rgba(0, 0, 0, 0.05);
	color: #FFFFFF;
	cursor: pointer;
	display: inline-block;
	float: right;
	font-size: 14px;
	font-weight: bold;
	height: 34px;
	line-height: 10px;
	margin-bottom: 0;
	padding: 2px 5px;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	vertical-align: middle;
	width: 70px;
	margin-top: 5px;
}
#submitBtn:hover
{
	color: red;
}
#comments {
	width: 680px;
	margin: auto;
	padding-bottom: 30px;
}

.oneComment {
	width: 575px;
	height: auto;
	border-bottom: 1px dotted #D9D9D6;
	margin: auto;
	padding-top: 10px;
}

#pic {
	padding-top: 10px;
	margin-bottom:20px;
	border-bottom:solid 1px #F5F5F5;
	float:left;
}
#bigpic
{
	padding-left:40px;
}
.userPic {
	display: inline;
	width: 60px;
	float: left;
}

.usercomment {
	height: 55px;
	width: 450px;
	float: left;
	text-align: left;
}

#bread {
	margin: auto;
	padding-top: 55px;
	font-size: 15px;
	text-align: left;
	width: 1024px;
	height: 30px;
	color: rgb(255, 111, 1111);
}

.breadItem {
	padding-left: 2px;
	padding-top: 1px;
	padding-bottom: 1px;
	color: rgb(255, 111, 1111);
}

.breadItem:hover {
	color: red;
	cursor: pointer;
}

#arrow {
	top: 260px;
	width: 600px;
	height: auto;
	z-index: 999;
	position: fixed;
	font-size: 40px;
	color: red;
}

#arrayleft {
	float: left;
	width: 32;
	height: 48;
	margin-left: 2px;
}

#arrayright {
	float: right;
	width: 32;
	height: 48;
	margin-right: 2px;
}

.detail_addthis
{
	width:300px;
	float:left;
	margin-top:10px;
	margin-left:50px;
}

#desc
{
	font-size:16px;
	margin-left:45px;
	margin-right:45px;
	margin-top:20px;
	text-align:left;
	color:black;
	
}

#rating
{
	padding-top:10px;
	margin-left:40px;
	margin-right:40px;
	padding-bottom:10px;
	position:fixed;
	top:150px;
	left:700px;
	float:right;
	z-index: 999;
}
.ratingItem
{
	border:1px  rgb(255, 111, 1111) solid;
	border-radius:8px 8px 8px 8px;
	width:100px;
	height:16px;
	padding:3px;
	padding-top:0px;
	padding-bottom:5px;
	margin-bottom:5px;
	margin-left:10px;
	font-size:20px;
	text-align:left;
	cursor:pointer;
	color:red;
}

.ratingItem span
{
	padding-left:20px;
}
</style>


<script type="text/javascript">
Date.prototype.format = function(mask) {  
	   
    var d = this;  
  
    var zeroize = function (value, length) {  
  
        if (!length) length = 2;  
  
        value = String(value);  
  
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {  
  
            zeros += '0';  
  
        }  
  
        return zeros + value;  
  
    };    
  
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|M{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function($0) { 
  
        switch($0) {  
  
            case 'd':   return d.getDate();  
  
            case 'dd':  return zeroize(d.getDate());  
  
            case 'ddd': return ['Sun','Mon','Tue','Wed','Thr','Fri','Sat'][d.getDay()];  
  
            case 'dddd':    return ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'][d.getDay()];  
  
            case 'M':   return d.getMonth() + 1;  
  
            case 'MM':  return zeroize(d.getMonth() + 1);  
  
            case 'MMM': return ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'][d.getMonth()];  
  
            case 'MMMM':
            return ['January','February','March','April','May','June','July','August','September','October','November','December'][d.getMonth()];  
  
            case 'yy':  return String(d.getFullYear()).substr(2);  
  
            case 'yyyy':    return d.getFullYear();  
  
            case 'h':   return d.getHours() % 12 || 12;  
  
            case 'hh':  return zeroize(d.getHours() % 12 || 12);  
  
            case 'H':   return d.getHours();  
  
            case 'HH':  return zeroize(d.getHours());  
  
            case 'm':   return d.getMinutes();  
  
            case 'mm':  return zeroize(d.getMinutes());  
  
            case 's':   return d.getSeconds();  
  
            case 'ss':  return zeroize(d.getSeconds());  
  
            case 'l':   return zeroize(d.getMilliseconds(), 3);  
  
            case 'L':   var m = d.getMilliseconds();  
  
                    if (m > 99) m = Math.round(m / 10);  
  
                    return zeroize(m);  
  
            case 'tt':  
                   return d.getHours() < 12 ? 'am' : 'pm';  
               
            case 'TT':  return d.getHours() < 12 ? 'AM' : 'PM';  
  
            case 'Z':   return d.toUTCString().match(/[A-Z]+$/);  
  
            // Return quoted strings with the surrounding quotes removed  
  
            default:    return $0.substr(1, $0.length - 2);  
  
        }  
  
    });  
  
};  


function comment2FB()
{
	var description1 = $("#itemDesc").text();
	var caption1=$("#itemDesc").text();
	var picture1=$("#itemUrl").text();
	var link1=window.location.href;
	var name1 = "share from picfalls"
	
	FB.ui(
	  {
	    method: 'feed',
	    name: name1,
	    link: link1,
	    picture: picture1,
	    caption: caption1,
	    description: description1
	  },
	  function(response) {
	    if (response && response.post_id) {
	      alert('Post was published.');
	    } else {
	      alert('Post was not published.');
	    }
	  }
	);
}

function comment()
{
	var url1 = host+"/api/comment";
	var id = $("#itemID").text();
	var commentdata = $("#comment-box").val();
	var mydata = '{"comment":"' + commentdata + '","commentTo":"'  
    + id + '"}';     
	$.ajax({
			type: "post",			
			url: url1,
			dataType:"json",
			data: mydata, 
			contentType: "application/json; charset=utf-8",  
			success:function (data)   // request success.
			{				
				if(data!=null && data.result == "ok");
				{
					var $copy =$("#commenthidden").clone(true);
					$copy.css("display","block");
					$copy.removeAttr("id");
									
					$copy.find("img").prop('src',$("#userhead").prop("src"));
					$copy.find(".commentUserName").text($("#userName").text());  
					$copy.find(".commentConetent").text(commentdata);
					$copy.find(".commentDate").text(new Date().format("yyyy-mm-dd hh:mm:ss"));		
					$("#comments:first").before($copy);
				}
				
				comment2FB();
				
			}
	  });
}


function loadComments()
{
	var id = $("#itemID").text();
	var searchURL = host+"/api/comment/"+trim(id);
	$(function()
	{
		var url =  searchURL;
		$.ajax({
				type: "get",			
				url: url,
				dataType:"json",
				contentType: "application/json; charset=utf-8",  
				success:function (data)   // request success.
				{														
					if(data!= null && data.length>0)
					{
						for(var i = 0; i< data.length;i++)
						{
							// find the shortest column		
						
							var $copy =$("#commenthidden").clone(true);
							$copy.css("display","block");
							$copy.removeAttr("id");
							
							var item = data[i];
							if(item.user!=null && item.user!="undefined")
							{
								$copy.find("img").prop('src',item.user.pic);
								$copy.find(".commentUserName").text(item.user.name);
							}
							
							if(item.comment!=null && item.comment!="undefined")
							{
								  
								$copy.find(".commentConetent").text(item.comment.comment);
								$copy.find(".commentDate").text(item.comment.formatDate);
							}
							
							$("#comments").append($copy);
						}
					}  
				}
		  });
	 }
	);
	
}


$(document).ready(function ()
	{ 
		$("#arrayleft").mouseover(function(){ $(this).attr("class","img_array_background leftarrow1");});
		$("#arrayleft").mouseleave(function(){ $(this).attr("class","img_array_background leftarrow");});
		
		$("#arrayright").mouseover(function(){ $(this).attr("class","img_array_background rightarrow1");});
		$("#arrayright").mouseleave(function(){ $(this).attr("class","img_array_background rightarrow");});	
		
		$("#arrayleft").click(function(){prev();});
		
		$("#arrayright").click(function(){next();});
		
		
		$("#bigpic").click(function(){next();});
		


		
				
		$("#submitBtn").click(function(){
			if(!testlogin())
				showLoginDiv();
			else
			    comment();
		});
		
		$("#right .img_background.good").on("click",function(){
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

		$("#right .img_background.bad").on("click",function(){
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
		
		$("#right .img_background.collect").on("click",function(){
			var id = $(this).parent().attr("itemid");
			collect(id,$(this));
		}).on("mouseover",function()
				{
				$(this).prop("class","img_background collect1");
		}).on("mouseleave",function()
				{
			$(this).prop("class","img_background collect");
		});
		
		
		loadComments();
		
		
		$("#rating .ratingItem.img_background.good").on("click",function(){
			var id = $("#itemID").last().text();
			good(id);
			add(this);
			$(this).prop("class","ratingItem img_background good1");
			$(this).off();
		}).on("mouseover",function()
				{
				$(this).prop("class"," ratingItem img_background good1");
		}).on("mouseleave",function()
				{
			$(this).prop("class","ratingItem img_background good");
		});

		$("#rating .img_background.bad").on("click",function(){
			var id = $("#itemID").last().text();
			bad(id);
			add(this);
			$(this).prop("class"," ratingItem img_background bad1");
			$(this).off();
		}).on("mouseover",function()
				{
			$(this).prop("class","ratingItem img_background bad1");
		}).on("mouseleave",function()
				{
			$(this).prop("class","ratingItem img_background bad");
		});
		
		$("#rating .img_background.collect").on("click",function(){
			var id = $("#itemID").last().text();
			collect(id,$(this));
		}).on("mouseover",function()
				{
				$(this).prop("class","ratingItem img_background collect1");
		}).on("mouseleave",function()
				{
			$(this).prop("class","ratingItem img_background collect");
		});
		
	});
	
 /* $(window).scroll(function(){  
	
	// if scrollbar is within 100px of bottom loadMore content
	var scrollTop =  $(this).scrollTop();
	var picHeight= $("#pic").height();
	console.log(picHeight+"   "+scrollTop );
	var span=$(document).height() - $(this).scrollTop() - $(this).height();
    if(span < 10)
    {
    	$("#arrow").hide();
    	$("#rating").hide();
    }
    else
    {
    	$("#arrow").show();
    	$("#rating").show();
    } 

});    */
var urlpre =host+ "/pre/";
function prev()
{
	var id = $("#itemID").last().text();
	var tag  = $("#currtag").last().text();
	var skip  = $("#skip").last().text();
	var sort  = $("#sort").last().text();
	
	var url =urlpre+tag+"/"+id+"?sort="+sort+"&skip="+skip;
	window.open(url,"_self");
}

var urlnext = host+"/next/";
function next()
{
	var id = $("#itemID").last().text();
	var tag  = $("#currtag").last().text();
	var skip  = $("#skip").last().text();
	var sort  = $("#sort").last().text();
	
	var url =urlnext+tag+"/"+id+"?sort="+sort+"&skip="+skip;
	window.open(url,"_self");
}

</script>


</head>
<body>
	<span id="sort" class="hiddenid">${sort}</span>
	<span id="currtag" class="hiddenid">${currtag}</span>
	<span id="skip" class="hiddenid">${skip}</span>
	<span id="itemID" class="hiddenid">${item.id}</span>
	<span id="itemDesc" class="hiddenid">${item.desc}</span>
	<span id="itemUrl" class="hiddenid">${item.url}</span>
	<jsp:include page="nav.jsp"></jsp:include>
	<div id="bread">
		<span style="font-size: 12px; font-weight: bold;">current
			position:</span> <a class="breadItem"><span>main ></span></a> <a
			class="breadItem"
			href="<%=path %>/"><span>${currtag}></span></a>
	</div>
	<div id="content">
		<div id="left">
			<div id="rating">
					<div class="ratingItem img_background good"><span>${item.good}</span></div>
					<div class="ratingItem img_background bad"><span>${item.bad}</span></div>
					<div class="ratingItem img_background post"><span>${item.comment}</span></div>
					<div style="clear: both"></div>
			</div>
				
			<div id="pic">
				<div id="arrow">
					<div id="arrayleft" class="img_array_background leftarrow"
						title="previous one"></div>
					<div id="arrayright" class="img_array_background rightarrow"
						title="next one"></div>
					<div style="clear: both"></div>
				</div>
				<span class="hiddenid">${item.id}</span> 
				<img  id="bigpic" width=500 src="${item.url1 }" />
			
				<div id="desc">
						<span>${item.desc }</span>	
					</div>	
					<div style="clear: both"></div>
				</div>
			
			<div id="detailComment">
				<div style="float:left;margin-left:20px"><img width=50 height=50 src="${user.pic}"  onerror="$(this).prop('src','<%=path%>/resources/img/user_head.png');" /></div>
				<div style="float:left"> <textarea class="cmtContex" id="comment-box" name="comment" placeholder="say something..." selectionstart="0" selectionend="0"></textarea></div>
					<div class="detail_addthis">
					<!-- AddThis Button BEGIN -->
						<div class="addthis_toolbox addthis_default_style addthis_32x32_style">
						<a class="addthis_button_preferred_1"></a>
						<a class="addthis_button_preferred_2"></a>
						<a class="addthis_button_preferred_3"></a>
						<a class="addthis_button_preferred_4"></a>
						<a class="addthis_button_compact"></a>
						<a class="addthis_counter addthis_bubble_style"></a>
						</div>
						<script type="text/javascript">
							var addthis_config = {"data_track_addressbar":true};
							var addthis_share =
							{
									url:'<%=path%>/${item.tag}/${item.id}',
									title:'${item.desc} (from picfalls)'
							};
						</script>
						<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5258fdaa3b761a84"></script>
						<!-- AddThis Button END -->
					</div>
					
					<div id="submit">					
						<button id="submitBtn" type="submit">submit</button>
					</div>
					
				<div style="clear: both"></div>
			</div>
			<div class="oneComment" id="commenthidden" style="display:none">
				<div class="userPic">
					<img width=50 height=50 src="<%=path%>/resources/img/user_head.png" onerror="$(this).prop('src','<%=path%>/resources/img/user_head.png');"/>
					<div style="clear: both"></div>
				</div>
				<div class="usercomment">
					<ul>
						<li><span class="commentUserName">${comment.user.name}</span><span>:</span> <span  class="commentConetent">${comment.comment.comment}</span>
						</li>
						<li><span class="commentDate">${comment.comment.formatDate}</span></li>
					</ul>
					<div style="clear: both"></div>
				</div>
				<div style="clear: both"></div>
			</div>

			<div id="comments">
				<c:forEach items="${comments}" var="comment">
					<div class="oneComment">
						<div class="userPic">
							<img width=50 height=50 src="${comment.user.pic}" />
							<div style="clear: both"></div>
						</div>
						<div class="usercomment">
							<ul>
								<li><span >${comment.user.name}:</span> <span >${comment.comment.comment}</span>
								</li>
								<li><span >${comment.comment.formatDate}</span></li>
							</ul>
							<div style="clear: both"></div>
						</div>
						<div style="clear: both"></div>
					</div>
				</c:forEach>
			</div>
			<div style="clear: both"></div>
		</div>

		
		<div id="right">
			<div id="recommend" style="margin:auto">
				<c:forEach items="${recommends}" varStatus="vs" var="item">  
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
			<br/>
			<br/>
			<div class="clear"></div> 
			</div>
		</div>

		<div style="clear: both"></div>
	</div>
</body>
</html>