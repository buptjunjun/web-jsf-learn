<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" charset=GBK"  content="java c c++ python android ���� Ա ��ũ ����  web html jsp servlet ����">
<title>�������� | �۳���Ա�Լ�����������</title>
<link rel="icon" href="../icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="../icon/ico.ico" type="image/x-icon" />

<script type="text/javascript">

 
</script>

<!--   color of em --> 
<style>
 em{   
    color: #CC0000;
    font-style: normal;
    }
body
{
padding-left:20px;
}
#content
{
	width:800px;
	padding-right:10px;	
}
</style>

</head>
<body>
	<%@ include file="common/header.jsp" %>
	<br>
	<form name= "form" action="" method="post">
		<div>		   
			<input  name="query" value="" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 	
			<input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		</div>
	</form>

	
	<br>
	<div id ="content" >
	<ul>
		<c:forEach items="${results}" var="result">
			<li>${result.title}</li>
			
		</c:forEach>
	</ul>
	</div>
	<%@ include file="common/footer.jsp" %>
	
	<script type="text/javascript">
	
	// resetQueryStr();
	</script>
</body>