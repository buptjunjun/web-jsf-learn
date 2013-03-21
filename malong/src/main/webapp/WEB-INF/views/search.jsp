<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" charset=GBK"  content="java c c++ python android 程序 员 码农 码龙  web html jsp servlet 开发">
<title>码龙搜索 -${criteria.query}</title>
<meta name="keywords" content="${criteria.query}" />
<meta name="description" content="${criteria.query}" /> 
<link rel="icon" href="../icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../icon/ico.ico" type="image/x-icon" />


<!--   color of em --> 
<style>
 em{   
    color: #CC0000;
    font-style: normal;
    }
body
{
padding-left:10px;
}
#content
{
	width:800px;
	padding-right:10px;	
	padding-left:1px;	
	font-family: arial;
	
}
</style>

</head>
<body>
	<div style="width:80%">
	<%@ include file="common/common.jsp" %>
	<%@ include file="common/header.jsp" %> 
	</div>
	
	<br>
		
	<%@ include file="common/searchBox.jsp" %>
	
	<br>
	<table width="70%">
	<tr>
		<td valign="top" width="65%" style="padding-right:10px">
		
			<div  style="text-align:left;font-size:14px;color:#1060f3;border: #CCCCCC;width:100%;float:left;height:100%" >
				<c:choose>
					<c:when test="${itemamount >0  }">
						<c:forEach items="${results}" var="result">
							<div>	
							<div><a href="<%=prefix%>page1/${result.reserve}"  target="_blank"> ${result.title}</a></div>
							<div style="font-size: 14px;color:#000000;line-height: 115%;">${result.content}</div>
							<div style='color:#008000; font-size:13px;padding-top:3px'> ${result.url}  |  ${result.date}</div>
							</div><br>
						</c:forEach> 
						
						<div>
							<c:forEach var="i" begin="0" end="${totalpage}" step="1" varStatus="cur" >
								<c:choose	>
									<c:when test="${page==i }">
										<a href="<%=prefix%>search?query=${ criteria.query}&page=${i}" style=" font-size:19px;color:blue">${i}</a>
									</c:when>
									<c:otherwise>
										<a href="<%=prefix%>search?query=${criteria.query}&page=${i}" style=" font-size:19px;color:green">${i}</a>
									</c:otherwise>
								</c:choose>						
							</c:forEach>
							
							<c:if test="${page > 0 }">
								<a href="<%=prefix%>search?query=${ criteria.query}&page=${page-1}" style=" font-size:19px;color:blue">&lt上一页</a>
							</c:if>
							
							<c:if test="${page < totalpage}">
								<a href="<%=prefix%>search?query=${ criteria.query}&page=${page+1}" style=" font-size:19px;color:blue">下一页&gt</a>
							</c:if>
						</div>
						<br>
					</c:when>
					
					<c:when test="${itemamount <= 0}">
						<p style="color:red">no items found!</p><br>
						<iframe id="frame" name="frame"   src="http://feifei.com/image/ppmm/" frameBorder="0" width="1000" scrolling="yes" height="760"></iframe>
					</c:when>
				</c:choose>
				</div>
				</td>
			
				<td valign="top" align="left" width="30%" style="padding-left:20px;border-left:dashed 1px blue">
					<%@ include file="common/hotBlogs.jsp" %>
			</td>
	</tr>
	<tr>
		<td>
		<div style="">
			<%@ include file="common/footer.jsp" %>
		</div>
		</td>
	
</table>
	
	
	
	
	
</body>