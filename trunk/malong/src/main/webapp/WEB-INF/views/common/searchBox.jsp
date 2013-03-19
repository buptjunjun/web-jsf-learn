<%@ page language="java" contentType="text/html; charset=GBK" isErrorPage="true"
    pageEncoding="GBK"%>
<script type="text/javascript">


// reset the query String
function resetQueryStr()
{
	var queryStr = document.getElementById("queryStr");
	var queryStrInput = document.getElementById("query");
	var queryText = queryStr.innerText;
	var queryText = queryStr.innerHTML;
	
	if(queryText != null && queryText != "undefined")
		queryStrInput.value = queryText;
	else
		queryStrInput.value = "";
	
}
</script>
<form name= "form" action="" method="get">
		<div>
		  <input id="query" name="query" type="text" maxlength="100" style='height:23px;width:400px;margin-right:10px;font-size:18px;'/> 
		  <input name="submit" value=" GO " type="submit" style='color:white; border:0px; margin-right:10px;font-size:22px;background:#1060f3'/>
		  <a style="margin-right:5px" href="http://www.baidu.com/s?wd=${ criteria.query}" target="_blank">baidu</a>
		  <a style="margin-right:5px" href="http://www.google.com.hk/search?hl=zh-CN&newwindow=1&safe=strict&site=&source=hp&q=${ criteria.query}" target="_blank">google</a>
		</div>
	</form>
<div  style ='display:none' id="queryStr" >${criteria.query}</div>
<script type="text/javascript">
	
	 resetQueryStr();
</script>