<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${search_title}</title>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/base/jquery-ui.css">
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/smoothness/jquery-ui.css">

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.js"></script>
<style>
#main
{
	width:1040px;	
	margin:auto;
	border:1px solid;	
}

#head
{
	margin-top:20px;
	width:1024px;
	height:60px;
	background: 0 0 white;
	text-align:center;	
}

#head_title
{
	font-size:40px;
	margin-left:40px;
}

#head_desc
{
	font-size:15px;
}
#content_div
{
	width:1024px;
	background:none repeat scroll 0 0 white;	
	text-align: center;
	display:inline-block;
	min-height:400px;
	
}
#search_input
{
	width:800px;
	height:60px;
	margin:auto;
	vertical-align:center;
	margin-top:20px;
}


#key
{
	font-size:20px;
	width:600px;
	height:40px;
	
}
.btn
{
	width:80px;
	height:40px;
	border:1px solid;
 	border-radius:5px;
 	display:inline;
 	text-align:center;
 	font-size:18px;
 	cursor:pointer;
}

#footer
{
	border-top:1px solid;
	width:1024px;
	background:none repeat scroll 0 0 white;	
	text-align: center;
}
#contact
{
	width:1000px;	
	margin:auto;
}

#advanced_option_div
{
	border:1px solid;
	width:780px;
	margin:auto;
}

#result_div
{
	border: solid 1px;
	width:900px;
	margin:auto;
}

.result_item
{
	float:left;
	text-align:left;
	width:780px;
	margin-top:10px;
	border-bottom:1px dashed;
}
.time_span
{

}
a,p
{
	line-height:15px;
	padding-top:1px;
	margin-bottom:5px;
	margin-top:5px;
}

</style>
<script type="text/javascript">
	$(function()
	{

		$("#advanced_btn").toggle(function(){
			$("#advanced_option").hide();
		},
		function(){
			$("#advanced_option_div").show();
		});
		
		$("#from").datepicker({ dateFormat: "yy-mm-dd" }).val();
        $("#to").datepicker({ dateFormat: "yy-mm-dd" }).val();
        
        $("#btn").bind("click",function(){submit();});
	
	});

	function submit() {
		var url1 = "/api/comment";
		var keywords = $("#key").val();
		var date1 = $("#from").val();
		var date2 = $("#to").val();
		var sort = $("#sort_time_select option:selected").val();
		var page = "";
		var search_position =$("#search_position_select option:selected").val();
		var mydata = '{"keywords":"' + keywords + '","date1":"' + date1
				+ '","date2":"' + date2 + '","sort":"' + sort + '","page":"'
				+ page + '","pagesearch_position":"' + search_position + '"}';
		$.ajax({
			type : "post",
			url : url1,
			dataType : "json",
			data : mydata,
			contentType : "application/json; charset=utf-8",
			success : function(data) // request success.
			{
				alert(data);
			}
		});
	}
</script>

</head>
<body>
<div id="main">
	<div id = "head">
		<span id="head_title">${search_head_title}</span>
		<span id="head_desc">${search_head_desc}</span>
	</div>
	<div id="content_div">
		<div id="search_input">
			<input id="page" style="display:none" value="0"></input>
			<input type="text" id="key" ></input>
			<input id="btn" class="btn" value="${search_searchbtn}"></input>
			<input id="advanced_btn" class="btn" value="advanced"></input>
		</div>	
		<div id="advanced_option_div">
			<div id="timespan_div" style="float">
				<label for="from">From</label>
				<input type="text" id="from" name="from">
				<label for="to">to</label>
				<input type="text" id="to" name="to">
			</div>
			<div id="sort_time_div">
				 <label for="sort">sort:</label>
				 <select id="sort_time_select">
					 <option value="0">ASC</option>
					 <option value="1" >DSE</option>
				 </select>
			</div>	
			<div id="search_position">
				 <label for="sort">I only search:</label>
				 <select id="search_position_select">
					 <option value="0">title</option>
					 <option value="1">content</option>
				 </select>
			</div>
	</div>
	<div id="result_div">
		<div class="result_item">
			<a class="url_a" href="http://bbs.csdn.net/topics/370134496">Starting ProtocolHandler ["http-bio-8080"]</a> <br/>
			<p class="content_p" name="content">NFO: HTMLManager: init: Global resources are available
二月 16, 2014 3:50:45 下午 org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directorva\software\apache-tomcat-7.0.34\webapps\ROOT
二月 16, 2014 3:50:45 下午 org.apache.coyote.AbstractProtocol start
INFO: Starting ProtocolHandler ["http-bio-8080"]
			</p>
			<a class="url_a" href="http://bbs.csdn.net/topics/370134496">http://bbs.csdn.net/topics/370134496</a>
			<span sytle="time_span">2014-2-14 12:00</span>
		</div>
	</div>
</div>
<div id="footer">
		<div id="contact">
			<span>${contact}</span>
		</div>
	</div>
</div>
</body>
</html>