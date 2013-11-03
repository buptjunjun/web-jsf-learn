<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC>

<%
	String path =  "http://" + request.getServerName() + ":" + request.getServerPort()+""+request.getContextPath();
%>


<html>
<head>
<style type="text/css">
.img_background {
	background-image: url("<%=path%>/resources/img/main_icon.png.gif");	
	background: url("<%=path%>/resources/img/main_icon.png.gif")  no-repeat 	scroll 0 0 rgba(0, 0, 0, 0);
}

.img_array_background {
	background-image: url("<%=path%>/resources/img/page_button.gif");
	background: url("<%=path%>/resources/img/page_button.gif")  no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}
</style>
</head>
</html>
