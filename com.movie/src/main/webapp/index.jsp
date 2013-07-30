<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>show movie</title>
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<style type="text/css">
.types
{
	background:gray;
}
.typeselect
{
	border-left:1px solid gray;
	border-right:1px solid gray;
	
}
.movietype
{	
	display:inline;
	border-bottom:1px solid gray;
}

.url
{
	margin-right:5px;
	font-size:14
}

.resoruce
{
	border-bottom : dashed  1px gray;
	padding:2px;
	margin-top:2px;
	background:rgb(240,255,240);
	
}
.resoruces
{
	background:green;
	
}
;
a,span
{
	margin-left:10px;
	
}
.misclass
{}

.des
{
	font-size:12
}
</style>

<script>
	$(
		function ()
		{
			// color chagne
			$(".movietype").mouseover( function(event){
				$(this).css("backgroundColor","blue");
				$("#resoruces").show();
				$("#other").show();
				getResource();
				return false;
				
			}).mouseout(
			function (event){
				$(this).css("backgroundColor","white");
				return false;
			});
			
			$(".misclass").click(function (event)
			{
				var $div =$("#copySelect");
				var x = event.pageX+5;
				var y = event.pageY+5;
				
				var $typeSelect  = $div.children("select");
				
				var types = ['qvod',"baidu",'vedio'];
				$typeSelect.attr("size",types.length);
				$typeSelect.empty();
				for(var i = 0;i < types.length; i++)
				{
					var $option =  $("<option value='"+types[i]+"'>"+types[i]+"</option>");
					$typeSelect.append($option);
				}
				
				$div.offset().left=x;
				$div.offset().top=y;
				
			});
		}
	);
	
	// store the resource data;
	var json = null;
	
	//get resources and  show on page
	function getResource()
	{
		if(json == null)
		{
			var url = "http://localhost:8080/movie/resource";
			$.get(url,
					{
					url:"http://movie.douban.com/subject/10743974",
					magicNum:-1,
					random:1
					},
					function (data,status){
						//alert(status);
						if(status=="success")
						{		
							//alert(data);
							
							json=eval('('+data+')');
							showResources();
						}					
					});
		}
		else
		{
			showResources();
		}
	}
	
	// show json data on page.
	function showResources()
	{
		if(json == null || json == "")
			return;
		$("#resoruces").empty();
		for(var i = 0; i< json.length; i++)
		{
			var $resource = $("#copyResource").clone(true);
			
			var $des = $resource.children("span.des");
			$des.text(json[i].movieDescription);
			
			var $url = $resource.children("a.url");
			$url.text(json[i].resourceURL);
			$url.attr('href',json[i].resourceURL);
			$("#resoruces").append($resource);
			$resource.show();
		}		

	}

</script>
</head>
<body>

<div id="main" >
	<div id ="types" class="types">
		<span class="typeselect  " >qvod</span>
		<span class="movietype  " >baidu</span>
		<span class="movietype  " >bt</span>
	</div>
	<div id="resoruces" style="display:none;float:left;width:60%" ></div>
	<div id="other" style="display:none;float:left;width:36%">	
		<p>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa<br>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa<br></p>
	</div>
</div>
<div id="copyResource" class="resoruce" style="display:none">
	<a class="url" target="_black" href=""></a>
	<a class="good" href="#">good</a>
	<a class="bad" href="#">bad</a>
	<a class="misclass" href="#">mis-classified</a>
	<br>
	<span class="des">des</span>
</div>

<div id="copySelect" style="position:absolute;display:none">
	<select name='movietype' id='movietype'></select>
</div>
</body>
</html>