<%@ page language="java" contentType="text/html; charset=GBK"
	isErrorPage="true" pageEncoding="GBK"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="icon" href="/icon/ico.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/icon/ico.ico" type="image/x-icon" />
<title>码龙搜索|帮程序员创造价值</title>
<script language="javascript"	src="/js/jquery-1.7.1.js"></script>
<style type="text/css">
	html{text-align: center;}
	body {text-align: center;}
</style>

</head>
<body>

	<div style="text-align: center; margin-bottom: 10px">
		<div style="color: #286BA7">
			<a href=<%=request.getContextPath() + "/"%>><img
				src=<%=request.getContextPath() + "/icon/LOGO3.png"%> border="0"></a>
		</div>
		<div style="text-align: center">
			<iframe id="frame" name="frame"
				src="http://my.oschina.net/zhxm/blog/42454"
				frameBorder="0" width="80%" scrolling="yes" height="760"></iframe>
		</div>
	</div>
	<%@ include file="footer.jsp"%>


<script type="text/javascript">  
	$("iframe#frame").load(function (){
		var height=$("#frame").contents().height();
		$("iframe#frame").attr("height",height)
	});
</script>

</body>
</html>