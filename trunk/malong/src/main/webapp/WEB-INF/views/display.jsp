<%@ page language="java" contentType="text/html; charset=GBK"
	isErrorPage="true" pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="icon" href="/icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/icon/ico.ico" type="image/x-icon" />
<title>码龙搜索-${blog.title}</title>
<script language="javascript"	src="/js/jquery-1.7.1.js"></script>
<style type="text/css">
	html{text-align: center;}
	body {text-align: center;}
</style>

</head>
<body>
	<div style="text-align: center">
		<div style="color: #286BA7 ; border:1px solid">
				<a href=<%=request.getContextPath() + "/"%>><img src=<%=request.getContextPath() + "/icon/LOGO3.png"%> border="0"></a>
				<%@ include file="common/searchBox.jsp" %>
		</div>
	
		
		<div style="border:1px solid; width:1024px">
			<div style="float:left;width:50%;border:1px solid">${blog.content}<br>	
			
			</div>
			
					
				
		
			<div style="float:right;border:1px solid">
				<%@ include file="common/hotBlogs.jsp" %>
			</div>
		</div>
		<div>
			<jsp:include  page="common/footer.jsp" >
		</div>
	</div>
	
	<br>
	
</body>
</html>