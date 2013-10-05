
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
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
		
	#tags ul li:hover /* 当有鼠标悬停在链接上 */
	{
		background:#0287CA;
		font-size:14px;
		color:white;
		cursor: pointer;
	}    
	#tags {
			height:200px;
			width:1280px;
			background:white;
			margin:auto;
			border-bottom: 1px solid #DFDFE0;
			font-size:13px;				
			}
			
	.tags
	{
		margin-left:2px;	
	}
	.tags ul
	{
		float:left;
	}
	.tags ul li
	{
		float:left;
		margin:2px;
		padding:4px;
		color: #444444;
		text-align:center;
		border: 1px solid #DFDFE0;
		text-align:center;
	}

	
	.tagKind
	{	
		width:300px;		
		float:left;
		margin-left:0px;
		border-left:1px solid #0287CA;
	}
	.tagHead
	{
		margin-top:5px;	
		margin-left:5px;
		background: url("<%=path%><c:url value='/resources/icon/bg.gif' />") repeat-x scroll;
		font-family: "Microsoft Yahei","宋体";
		color:red;
		font-size:18px;
		font-weight:bold;
		display:block;
		height:30px;
	}
	
	</style>
</head>

   <div id="tags" class="tags fontChinese14px">
			<div id="tagKind" class="tagKind">
				<div id="tagHead" class="tagHead">  按题材</div>
				<ul>
					<li>喜剧</li> <li>喜剧</li> <li>喜剧</li> <li>喜剧</li> <li>喜剧</li>
					<li>喜剧</li> <li>喜剧</li> <li>喜剧</li> <li>喜剧</li> <li>喜剧</li>
		
				</ul>
			</div>
			
			<div id="tagTime" class="tagKind">
				<div id="tagHead" class="tagHead">  按时间</div>
				<ul>
					<li>2013</li> <li>2013</li><li>2013</li><li>2013</li><li>2013</li>
					<li>2013</li>
				</ul>
			</div>
			
			<div id="tagPeople" class="tagKind">
				<div id="tagHead" class="tagHead">  按人物</div>
				<ul>
				<c:forEach items="${tags}" var="tag">  
				   <li>${tag}</li> 
				</c:forEach>  
				</ul>
			</div>
	</div>	 