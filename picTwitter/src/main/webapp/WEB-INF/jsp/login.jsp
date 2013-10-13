<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
	
<script type="text/javascript">
var FACEBOOK = "3";
// user face book data to fill the form
function fillFormFB(info)
{
	$("#idSource").prop("value","100001666481139");
	$("#source").prop("value",FACEBOOK);
	$("#gender").prop("value","m");
	$("#pic").prop("value","http://graph.facebook.com/100001666481139/picture" );
	$("#url").prop("value","http://www.baidu.com");
	$("#otherInfo").prop("value",info);
	$("#user").submit();
}
function fillFormFBReal(info)
{
	$("#idSource").prop("value",info.id);
	$("#source").prop("value",FACEBOOK);
	
	if(info.gender == "male")		
		$("#gender").prop("value","m");
	else if(info.gender == "female")
		$("#gender").prop("value","f");
	else
		$("#gender").prop("value","o");
	
	$("#pic").prop("value","http://graph.facebook.com/100001666481139/picture" );
	
	$("#url").prop("value","http://www.baidu.com");
	$("#otherInfo").prop("value",info);
	$("#user").submit();
}
</script>
<form name="user" id="user" class="login" action="/picture/login" method="post">
	<input id="idSource" name="idSource"></input><br>
	<input id="source" name="source"></input><br>
	<input id="gender" name="gender"></input><br>
	<input id="pic" name="pic"></input><br>
	<input id="url" name="url"></input><br>
	<input id="otherInfo" name="otherInfo"></input><br>
	<input type="submit" name="testSubmit" value="注册">
</form>
<input type="button" name="testSubmit" value="test" onclick="fillFormFB()">

<script type="text/javascript">

</script>
