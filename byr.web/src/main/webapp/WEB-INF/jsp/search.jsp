<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${search_title}</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$("#advanced_btn").on("click", function(){
		alert("aaaa");
	});

</script>
<style>
#main
{
	width:1040px;	
	margin:auto;	
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
#content
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


</style>
</head>
<body>
<div id="main">
	<div id = "head">
		<span id="head_title">${search_head_title}</span>
		<span id="head_desc">${search_head_desc}</span>
	</div>
	<div id="content">
		<div id="search_input">
			<input type="text" id="key" ></input>
			<input id="btn" class="btn" value="${search_searchbtn}"></input>
			<input id="advanced_btn" class="btn" value="advanced"></input>
		</div>	
		<div id="advanced_option">
			test
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