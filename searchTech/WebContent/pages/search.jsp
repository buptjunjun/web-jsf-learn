<%@ page language="java" 
		 contentType="text/html; charset=GBK"
    	 pageEncoding="GBK"
    	 import="org.search.beans.*"
    	 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>码龙搜索|咱程序员自己的搜索引擎</title>
</head>
<body>
	<%@ include file="/pages/header.jsp" %>
	<br>
	<form action="/searchTech/search" method="post">
		<input name="searchwords" type="text" maxlength="100" style='height:30px;width:400px;margin-right:10px'/>
		<input name="submit" value="  GO  " type="submit" style="height:30px" />
	</form>
	<br>
	<div id ="content" >
	  <%
	   ResultBean result = (ResultBean)session.getValue("result");
	   if(result != null)
	   {
		   for(ResultItemBean b:result.getResults())
		   {
			   out.println("<div style='width:1024px;border-bottom:1px dashed #286BA7'>");
			   out.println("<div><a href='"+b.getUrl()+"'>"+b.getTitle()+"</a></div>");
			   out.println("<div>"+b.getContent()+"..."+"</div>");	
			   out.println("<div style='color:green'>"+b.getUrl() +"  |  "+ b.getDate().toLocaleString()+"</div>");	
			   out.println("</div><br>");
		   }
	   }
	  %>
	</div>
	<%@ include file="/pages/footer.jsp" %>
</body>
</html>