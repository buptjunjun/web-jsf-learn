<%@ page language="java" contentType="text/html; charset=GBK"
	isErrorPage="true" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��������-${blog.title}</title>
<meta name="keywords" content="${blog.title}" />
<meta name="description" content="${blog.title}" /> 
<link rel="icon" href="../icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../icon/ico.ico" type="image/x-icon" />
<script language="javascript"	src="/js/jquery-1.7.1.js"></script>
<style type="text/css">
	html{text-align: center;}
	body {text-align: center;}
</style>

</head>
<body>
<%@ include file="common/common.jsp" %>
	<div style="color: #286BA7 ; border-bottom:1px dashed ; padding-bottom:20px">
				<a href=<%=request.getContextPath() + "/"%>><img src=<%=request.getContextPath() + "/icon/LOGO3.png"%> border="0"></a>
				<%@ include file="common/searchBox.jsp" %>
		</div>

		
	
		<table width="100%"   height="100%" border="0"   cellspacing="0"   cellpadding="0" style="padding-left:6%; padding-right:6%">
			<tr>
				<td  align="center" >
					<table border= "0">
						<tr>
							<td width="70%" align="center"   valign="top" valign="middle">
							<br>
							<div style="padding-right:10px; text-align: left">
								<div style="font-size:20px;color:red;text-align:center">
									${ blog.title}
								</div>
								<br>
								<div style="font-size:14px;color:green;text-align:center">
									

									<c:forEach items="${blog.tags}" var="tag">
										${tag }&nbsp
									</c:forEach>
									| ${blog.crawledDate.year+1990}-${blog.crawledDate.month+1}-${blog.crawledDate.date}
									
								</div>
								<br>
								${content}
								<br>
								
								<div>
								<c:if test="${totalPage > 0 }">
									<c:forEach var="i" begin="0" end="${totalPage}" step="1" varStatus="cur" >
										<c:choose>
											<c:when test="${page==i }">
												<a href="<%=prefix%>page/${blog.id}?page=${i}" style=" font-size:19px;color:blue">${i}</a>
											</c:when>
											<c:otherwise>
												<a href="<%=prefix%>page/${blog.id}?page=${i}"  style=" font-size:19px;color:green">${i}</a>
											</c:otherwise>
										</c:choose>						
									</c:forEach>
									
									<c:if test="${page > 0 }">
										<a href="<%=prefix%>page/${blog.id}?page=${page-1}"  style=" font-size:19px;color:blue">&lt��һҳ</a>
									</c:if>
									
									<c:if test="${page < totalPage }">
										<a href="<%=prefix%>page/${blog.id}?page=${page+1}"  style=" font-size:19px;color:blue">��һҳ&gt</a>
									</c:if>
								</c:if>
								</div>
							</div>	
							</td>
							<td valign="top">
							<div style="padding-left:10px;  border-left:  1px dashed;text-align: left">
								<br>
								<%@ include file="common/hotBlogs.jsp" %>
							</div>
							</td>
						</tr>
					</table>
				<td>
			</tr>
			<tr>
				<td>
					
				<td>
			</tr>
		</table>
		
			
			

	</div>
	
<%@ include file="common/footer.jsp" %>
	
</body>
</html>