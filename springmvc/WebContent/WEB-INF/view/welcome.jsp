<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'hello.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>
<body>
	<
	<div>
		<h2>A global community of friends and strangers spitting out
			their inner-most and personal thoughts on the web for everyone else
			to see.</h2>
		<h3>Look at what these people are spitting right now...</h3>
		<ol class="spittle-list">
			<c:forEach var="spittle" items="${spittles}">
				<s:url value="/spitters/{spitterName}" var="spitter_url">
					<s:param name="spitterName" value="${spittle.spitter.username}" />
				</s:url>
				<li><span class="spittleListImage"> <img
						src="http://s3.amazonaws.com/spitterImages/${spittle.spitter.id}.jpg"
						width="48" border="0" align="middle"
						onError="this.src='<s:url value="/resources/images"/>/spitter_avatar.png';" />
				</span> <span class="spittleListText"> <a href="${spitter_url}">
							<c:out value="${spittle.spitter.username}" />
					</a> - <c:out value="${spittle.text}" /><br /> <small><s:eval
								expression="spittle.when" /></small>
				</span></li>
			</c:forEach>
		</ol>
</body>
</html>
