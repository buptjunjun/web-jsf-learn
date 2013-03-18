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
	<div style="text-align: center; margin-bottom: 10px">
		<div style="color: #286BA7">
			<a href=<%=request.getContextPath() + "/"%>><img
				src=<%=request.getContextPath() + "/icon/LOGO3.png"%> border="0"></a>
		</div>
		<div style="text-align: center">
		 <%-- <div id="load" style="margin-top:200px"><img src="http://sysimages.tq.cn/images/analysis_images/ajax-loader.gif" />&nbsp;loading.</div> 
			<iframe id="frame" name="frame" style="visibility:hidden"
					 onreadystatechange="stateChangeIE(this)" onload="stateChangeFirefox(this)"  src="${url}"
				frameBorder="0" width="90%" scrolling="yes" height="400"></iframe>
		</div> --%>
		
		"${blog.content}"
	</div>
	<%@ include file="common/footer.jsp"%>
<!-- 
     <script>     
        function stateChangeIE(_frame){  
            if (_frame.readyState=="interactive"){ //state: loading ,interactive, complete  
                var loader = document.getElementById("load");  
                loader.innerHTML  = "";  
                loader.style.display = "none";    
                _frame.style.visibility = "visible";    
                _frame.style.height="720px";
            }     
        }  
  
        function stateChangeFirefox(_frame){  
            var loader = document.getElementById("load");  
            loader.innerHTML  = "";  
            loader.style.display = "none";    
            _frame.style.visibility = "visible";
            _frame.style.height="720px";
        }  
  
        //callframe.location.href="http://deographics.com/";  
    </script>   
 -->
</body>
</html>