<%@ page language="java" 
		 contentType="text/html; charset=GBK"
    	 pageEncoding="GBK"
    	 import="org.search.beans.*,java.util.*"
    	 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="http://localhost:8080/searchTech/icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://localhost:8080/searchTech/icon/ico.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>码龙搜索|咱程序员自己的搜索引擎</title>

<style type="text/css">
		html{text-align:center;}
		body{text-align:center;}
	</style>
</head>
<body>
<div style="margin-top:50px">	
	<img  src="http://localhost:8080/searchTech/icon/LOGO2.png"><br>

	<div style="margin-bottom:200px">
	<form action="/searchTech/search" method="post">
		<div>
		<input name="searchwords" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
		<input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		</div>
	</form>
	</div>
</div>
	<%@ include file="/pages/footer.jsp" %>
</body>
</html>