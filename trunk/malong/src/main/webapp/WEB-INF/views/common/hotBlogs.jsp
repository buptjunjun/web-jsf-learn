<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="common.jsp" %>
<div style="text-align:left">
	<c:forEach items="${hotblogs}" var="blog">
		<div>
			<div style="font-color:blue"><a href="<%=prefix%>page/${blog.id}"  target="_blank"> ${blog.title}</a></div><br>
		</div>
	</c:forEach>	
</div>