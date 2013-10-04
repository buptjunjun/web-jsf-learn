
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort();
%>
<html>
<head>
    <title>片儿网</title>
	<meta content="text/html;" http-equiv="Content-Type">
	<meta content="乐视网,电影天堂,电视剧排行榜,最新电影,动漫,综艺,视频网站" name="keywords">
	<meta content="name=最新动漫;action-uri=http://comic.letv.com/?ie9;icon-uri=http://www.letv.com/favicon.ico" name="msapplication-task">
    <script type="text/javascript">
        function showTip(){
            document.getElementById("Tip").style.display="inline";
        }
    </script>
	<link type="text/css" rel="stylesheet" href="<%=path%><c:url value='/resources/styles/common.css'/>" />   

</head>
<body>
    <jsp:include page="top.jsp" flush="true" />
	<jsp:include page="nav.jsp" flush="true" />
	<jsp:include page="tags.jsp" flush="true" />

	 <div id="middle" >
		
		<div id="hot" class="movieFrame">			
			<div class="movieLeft">
				<div class="movieHead">
					<span class="catName">
						最新电影
					</span>
					<span class="moreMovie">
						<a href="#">更多 </a>
					</span>
				</div>
				<ul >
					
					<li class="movieItem">	
						<p><img src="<%=path%><c:url value='/resources/picture/movie.jpg' />"  class="moviePic" width="150" height="210"></img></p>
						<span class="movieName">低俗小说</span><br/>
						<span>阿娇特辑衣袂飘飘舞仙境</span>
					</li>
					
					<li class="movieItem">	
						<p><img src="<%=path%><c:url value='/resources/picture/movie.jpg' />"  class="moviePic" width="150" height="210"></img></p>
						<span class="movieName">低俗小说</span><br/>
						<span>阿娇特辑衣袂飘飘舞仙境</span>
					</li>
					<li class="movieItem">	
						<p><img src="<%=path%><c:url value='/resources/picture/movie.jpg' />"  class="moviePic" width="150" height="210"></img></p>
						<span class="movieName">低俗小说</span><br/>
						<span>阿娇特辑衣袂飘飘舞仙境</span>
					</li>
					<li class="movieItem">	
						<p><img src="<%=path%><c:url value='/resources/picture/movie.jpg' />"  class="moviePic" width="150" height="210"></img></p>
						<span class="movieName">低俗小说</span><br/>
						<span>阿娇特辑衣袂飘飘舞仙境</span>
					</li>
					<li class="movieItem">	
						<p><img src="<%=path%><c:url value='/resources/picture/movie.jpg' />"  class="moviePic" width="150" height="210"></img></p>
						<span class="movieName">低俗小说</span><br/>
						<span>阿娇特辑衣袂飘飘舞仙境</span>
					</li>
				</ul>
			</div>
			
			<div class="movieRight">
				<div class="movieHead">
					<span class="catName">
						最热
					</span>
				</div>
				<ul >
					<li class="movieItemSimpleHot">	
						<span>1</span>
						<span class="movieName">低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimpleHot">	
						<span>2</span>
						<span class="movieName">低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimpleHot">	
						<span>3</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>4</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>5</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>6</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>7</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>8</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>9</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
					<li class="movieItemSimple">	
						<span>10</span>
						<span >低俗小说</span>
						<span>4.5</span>
					</li>
				</ul>
			</div>
		</div>
		
		
	</div>
	

	<div class="searchBottom">
				<form>
					<input id="query" class="height20 width400" type="text" style="" autocomplete="off" value="全网搜索影片" name="q">
					<span class="height20"> <input id="qbtn" class="sicon" type="button" onclick="" name=""></span>
				</form>
	</div>

</body>
</html>