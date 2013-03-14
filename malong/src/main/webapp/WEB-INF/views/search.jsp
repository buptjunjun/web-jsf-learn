<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" charset=GBK"  content="java c c++ python android 程序 员 码农 码龙  web html jsp servlet 开发">
<title>码龙搜索 | 咱程序员自己的搜索引擎</title>
<link rel="icon" href="../icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../icon/ico.ico" type="image/x-icon" />

<script type="text/javascript">

// reset the query String
function resetQueryStr()
{
	var queryStr = document.getElementById("queryStr");
	var queryStrInput = document.getElementById("query");
	var queryText = queryStr.innerText;
	var queryText = queryStr.innerHTML;
	
	if(queryText != null && queryText != "undefined")
		queryStrInput.value = queryText;
	else
		queryStrInput.value = "";
	
}
</script>

<!--   color of em --> 
<style>
 em{   
    color: #CC0000;
    font-style: normal;
    }
body
{
padding-left:10px;
}
#content
{
	width:800px;
	padding-right:10px;	
	padding-left:1px;	
	font-family: arial;
	
}
</style>

</head>
<body>
	<div style="width:50%">
	<%@ include file="common/header.jsp" %> 
	</div>
	
	<br>
		
	<form name= "form" action="" method="get">
		<div>
		  <input id="query" name="query" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
		  <input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		  <a style="margin-right:5px" href="http://www.baidu.com/s?wd=${ criteria.query}" target="_blank">百度</a>
		  <a style="margin-right:5px" href="http://www.google.com.hk/search?hl=zh-CN&newwindow=1&safe=strict&site=&source=hp&q=${ criteria.query}" target="_blank">google</a>
		</div>
	</form>
	
	<br>
	<div id ="content" style="" >
	<c:choose>
		<c:when test="${itemamount >0  }">
			<c:forEach items="${results}" var="result">
				<div>	
				<div><a href=display?url=${result.url}  target="_blank"> ${result.title}</a></div>
				<div style="font-size: 14px;color:#000000;line-height: 115%;">${result.content}</div>
				<div style='color:#008000; font-size:13px;padding-top:3px'> ${result.url}  |  ${result.date}</div>
				</div><br>
			</c:forEach> 
		</c:when>
		
		<c:when test="${itemamount <= 0}">
			<p style="color:red">靠！没找到答案喔!! 给点福利，消消气</p><br>
			<iframe id="frame" name="frame"   src="http://feifei.com/image/ppmm/" frameBorder="0" width="1000" scrolling="yes" height="760"></iframe>
		</c:when>
	</c:choose>
	</div>
	
	<div style="padding-bottom:10px">
	<c:if test="${page > 0}">
		<a href=search?query=${criteria.query}&page=${page-1}>上一页</a>
	</c:if>
	
	<c:if test="${page < 2}">
		<a href="search?query=${criteria.query}&page=${page+1}">下一页</a>
	</c:if>
	</div>
	<div  style ='display:none' id="queryStr" >${criteria.query}</div>
	<%@ include file="common/footer.jsp" %>
	
	<script type="text/javascript">
	
	 resetQueryStr();
	</script>
</body>