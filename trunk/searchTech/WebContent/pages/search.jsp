<%@ page language="java" 
		 contentType="text/html; charset=GBK"
    	 pageEncoding="GBK"
    	 import="org.search.beans.*,java.util.*"
    	 %>

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
		var queryStrInput = document.getElementById("searchwords");
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
</style>

</head>
<body>
	<%@ include file="/pages/header.jsp" %>
	<br>
	
	<form name= "form" action=<%= request.getContextPath()+"/search"%> method="post">
		<div>
			<input id="searchwords" name="searchwords" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
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
	   String queryStr = (String)session.getValue("queryStr");
	   out.println("<div  style ='display:none' id='queryStr' >"+queryStr+"</div>");
	  %>
	</div>
	<%@ include file="/pages/footer.jsp" %>
	
	<script type="text/javascript">
	
	 resetQueryStr();
	</script>
</body>
</html>