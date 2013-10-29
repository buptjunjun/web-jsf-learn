<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
	System.out.println("path="+path);
%>
<script type="text/javascript">
var host = "<%=path%>"; //http://localhost:8080ture/
</script>

<html>
<head>
<title>pic galaxy</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="google-site-verification" content="ZwuzOT3g0hU4kLoQRpG1faoXlOQ_Jtw5ut28Lu3bPxA" />
<link type="text/css" rel="stylesheet" href="<%=path %>/resources/style/common.css" /> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
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
	width: 300px;
	height: auto;
	float: left;
	border: 1px black;
}

#comment {
	width: 680px;
	margin: auto;
	border-bottom:solid 1px #F5F5F5;
}

.cmtContex {
	height: 50px;
	width: 500px;
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
	top: 300px;
	width: 700px;
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
	font-size:18px;
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

function comment()
{
	var url1 = host+"/api/comment";
	
	
	$(function()
	{
		var id = $(".hiddenid").last().text();
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
					
				}
		  });
	 }
	);	
}


function loadComments()
{
	var id = $(".hiddenid").last().text();
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
		
		loadComments();
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
var urlpre =host+ "/detail/pre/";
function prev()
{
	var id = $(".hiddenid").last().text();
	var url = urlpre+id+"?kind="+$("#kind").text();
	window.open(url,"_self");
}

var urlnext = host+"/detail/next/";
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
		<span style="font-size: 12px; font-weight: bold;">current
			position:</span> <a class="breadItem"><span>main ></span></a> <a
			class="breadItem"
			href="<%=path %>/"><span>${item.type}></span></a>
	</div>
	<div id="content">
		<div id="left">
			<div id="pic">
				<div id="arrow">
					<div id="arrayleft" class="img_array_background leftarrow"
						title="previous one"></div>
					<div id="arrayright" class="img_array_background rightarrow"
						title="next one"></div>
					<div style="clear: both"></div>
				</div>
				<span class="hiddenid">${item.id}</span> <img id="bigpic" width=600
					src="${item.url1 }" />
				<p id="desc">${item.desc }</p>
			</div>

			<div id="comment">
				<img width=50 height=50
					src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
				
					<textarea class="cmtContex" id="comment-box" name="comment"
						placeholder="say something..." selectionstart="0" selectionend="0"></textarea>

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
									url:'<%=path%>/detail/{item.id}',
									title:'${item.desc} (from picfalls)',
							};
						</script>
						<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5258fdaa3b761a84"></script>
						<!-- AddThis Button END -->
					</div>
					
					<div id="submit">					
						<button id="submitBtn" type="submit">submit</button>
					</div>
					
				
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

		<div id="right"></div>

		<div style="clear: both"></div>
	</div>
</body>
</html>