<%@ page language="java" 
		 contentType="text/html; charset=GBK"
    	 pageEncoding="GBK"
    	 import="org.search.beans.*,java.util.*"
    	 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>码龙搜索 | 咱程序员自己的搜索引擎</title>
<link rel="icon" href="http://localhost:8080/searchTech/icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://localhost:8080/searchTech/icon/ico.ico" type="image/x-icon" />
</head>
<body>
	<%@ include file="/pages/header.jsp" %>
	<br>
	<form name= "form" action="/searchTech/search" method="post">
		<div>
			<input name="searchwords" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
			<input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		</div>
	</form>
	<br>
	<div id ="content" >
	  <%
	   int maxPage = 2;
	   ResultBean result = (ResultBean)session.getValue("result");
	   String pagestr = request.getParameter("page");
	   int pageNum = 0;
	   if (pagestr == null || pagestr.equals(""))
		   pageNum =0;
	   else 
	   {
		   pageNum = Integer.parseInt(pagestr);
		   if(pageNum < 0 )
			   pageNum = 0; 
	   }
		   
	   if(result != null)
	   {  
		   List<ResultItemBean> resultBeans = result.getResults();
		   int begin = pageNum*10;
		   boolean flag = false;
		   for(int i =begin ; i < resultBeans.size() && i < begin +10;i++)
		   {
			   ResultItemBean b = resultBeans.get(i);
			   
			   out.println("<div style='width:800px;'>");
			   out.println("<div><a href='"+b.getUrl()+"'  target='_blank'>"+b.getTitle()+"</a></div>");
			   out.println("<div>"+b.getContent()+"..."+"</div>");	
			   out.println("<div style='color:green'>"+b.getUrl() +"  |  "+ b.getDate().toLocaleString()+"</div>");	
			   out.println("</div><br>");
			   flag = true;
		   }
		   if(pageNum == 0)
			   out.println("<div><a href='search.jsp?page="+(pageNum+1)+"' >下一页</a></div>");
		   else if(pageNum == maxPage)
		 	  out.println("<div><a href='search.jsp?page="+(pageNum-1)+"' >上一页</a></div>");
		   else 
			   out.println("<div><a href='search.jsp?page="+(pageNum-1)+"' >上一页</a> <a href='search.jsp?page="+(pageNum+1)+"' >下一页</a></div>");
		   
	   }
	  %>
	</div>
	<%@ include file="/pages/footer.jsp" %>
</body>
</html>