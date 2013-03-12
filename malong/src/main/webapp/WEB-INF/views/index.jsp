<%@ page language="java" 
		 contentType="text/html; charset=GBK"
    	 pageEncoding="GBK"
    	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="icon/ico.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" charset=GBK"  content="java c c++ python android 程序 员 码农 码龙  web html jsp servlet 开发">
<title>码龙搜索|咱程序员自己的搜索引擎</title>

<style type="text/css">
		html{text-align:center;}
		body{text-align:center;}
		
	</style>
</head>
<body>
	<div style="">	
		<img  src="icon/LOGO2.png"><br>
	
			<div style="margin-bottom:20px">
			<form name= "form" action=<%= request.getContextPath()+"/search"%> method="get">
				<div>
					<input name="query" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
					<input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
				</div>
			</form>
			</div>
		
			<div  style="text-align:center;;font-size:14px;color:#1060f3" >
				<table style="margin:auto; width:80%">
				<tr>
				<c:forEach items="${hosts}" var ="hot"  varStatus="vstatus">			
						<c:if test="${vstatus.index % 8 == 0 && vstatus.index > 0}" >
							</tr><tr>
						</c:if>		
					<td><a target="_blank" href="search?query=${hot.name}" style="color:#1060f3">${hot.name}</a></td>
				</c:forEach>
				</tr>
				</table>
			</div>
			<br>
			<%@ include file="common/footer.jsp" %>
	</div>	
	
</body>
</html>