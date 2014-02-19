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
a:link { text-decoration: none}
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
#advanced_btn
{
	font-size:13px;
	border-bottom:solid 1px;;
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
	
	width:780px;
	margin:auto;
	padding:4px;
	text-align: left;
	display:none;
}

#timespan_div
{
	
	display:block;
	margin:2px;
}

#sort_time_div
{	
		display:block;
		margin:2px;
}

#search_position_div
{
		margin:2px;
		display:block;
}



#result_div
{
	border-top:solid 1px;
	width:800px;
	margin:auto;
}

.result_item
{
	padding-top:10px;
	margin:auto;
	text-align:left;
	width:780px;
	margin-top:10px;
	border-bottom:1px dashed;
}
a,p
{
	line-height:15px;
	padding-top:1px;
	margin-bottom:5px;
	margin-top:5px;
}
em
{
	color:red;
	font-weight:bold;
}
.result_item_hidden
{
	display:none;
}

#paging
{
	width:780px;
	margin:auto;
	padding-bottom:5px;
	padding-top:5px;
	
}
#paging  span:hover
{
	background:blue;
}
#paging span
{
	cursor:pointer;
	marging:5px;
	padding-left:5px;
	padding-right:5px;
	border:1px solid;
	display:none;
}

.title_a
{
	font-size:20px;
}

.content_p
{
	font-size:16px;
}

.url_a
{
	color:#008000;
	font-size:13px;
}
</style>
<script type="text/javascript">
	$(function()
	{

		$("#advanced_btn").toggle(function(){
			$("#advanced_option_div").hide();
		},
		function(){
			$("#advanced_option_div").show();
		});
		
		$("#from").datepicker({ dateFormat: "yy-mm-dd" }).val();
        $("#to").datepicker({ dateFormat: "yy-mm-dd" }).val();
        
        $("#btn").bind("click",function(){default_submit();});
		$("#paging span").bind("click",function(){submit($(this).text());});
	});

	function default_submit()
	{
		submit(0);
	}
	
	function submit( page ) {
		var url1 = "http://localhost:8080/byrweb/searchapi";
		
		if(page == "undefined" || page == null)
			page = 0;
		
		var keywords = $("#key").val();
		var date1 = $("#from").val();
		var date2 = $("#to").val();
		var sort = $("#sort_time_select option:selected").val();
		var search_position =$("#search_position_select option:selected").val();
		var $advanced = $("#advanced_option_div");
		if($advanced.is(":visible"))
		{	
			var mydata = '{"keywords":"' + keywords +'","page":"'+page;
			
			if(date1 !=null && date1 != "undefined" && date1 != "")
				mydata += '","date1":"' + date1;
			if(date2 !=null && date2 != "undefined" && date2 != "")
				mydata += '","date2":"' + date2;
			if(sort !=null && sort != "undefined" && sort != "")
				mydata += '","sort":"' + sort ;
			if(search_position !=null && search_position != "undefined" && search_position != "")
				mydata += '","search_position":"' + search_position; 
			
			mydata += '"}';
		}
		else
		{
			var mydata = '{"keywords":"' + keywords +'","page":"'+page+'"}';
		}
		$.ajax({
			type : "post",
			url : url1,
			dataType : "json",
			data : mydata,
			contentType : "application/json; charset=utf-8",
			beforeSend:function(){ShowProgressBar();},
			complete:function(){HideProgressBar();},
			success : function(data) // request success.
			{
				if(data == null || data == "undefined")
				{
					alert("error1");
					return;
				}
				
				if(data.success=="false")
				{
					alert("error:"+data.errorMessge);
				}
				else
				{
					var rets = data.data;
					var count = null;
					if( data.count != null &&  data.count != "" &&  data.count != "undefined")
					  count = data.count/10;
					
					if(data.count%10==0 && data.count > 0)
						count=count-1;
					
					$("#paging span").each(function(i){
						
						if(count != null )
						{
							if(i<=count)
								$(this).show();
							else
								$(this).hide();
						}
						if(i == page)
							$(this).css("background","blue");
						else
							$(this).css("background","");
						});
					
					if(rets == null || rets == "undefined")
					{
						alert("empty data");	
					}
					else
					{
						$("#result_div").empty();
						for(var i = 0; i < rets.length; i++)
						{
							 var item = rets[i];
							 var $copy =$(".result_item_hidden").clone(true);
							 $copy.find(".title_a").html(item.title);
							 $copy.find(".title_a").prop("href",item.url);
							 
							 $copy.find(".content_p").html(item.content);
							 
							 $copy.find(".url_a").html(item.url);
							 $copy.find(".url_a").prop("href",item.url);
							 $copy.prop("class","result_item");
							 $("#result_div").append($copy);
						}
					}
				}
				
			}
		});
	}
	
	
	// 顯示讀取遮罩
	function ShowProgressBar() {
	    displayProgress();
	    displayMaskFrame();
	}

	// 隱藏讀取遮罩
	function HideProgressBar() {
	    var progress = $('#divProgress');
	    var maskFrame = $("#divMaskFrame");
	    progress.hide();
	    maskFrame.hide();
	}
	// 顯示讀取畫面
	function displayProgress() {
	    var w = $(document).width();
	    var h = $(window).height();
	    var progress = $('#divProgress');
	    progress.css({ "z-index": 999999, "top": (h / 2) - (progress.height() / 2), "left": (w / 2) - (progress.width() / 2) });
	    progress.show();
	}
	// 顯示遮罩畫面
	function displayMaskFrame() {
	    var w = $(window).width();
	    var h = $(document).height();
	    var maskFrame = $("#divMaskFrame");
	    maskFrame.css({ "z-index": 999998, "opacity": 0.7, "width": w, "height": h });
	    maskFrame.show();
	}
</script>

</head>
<body>
<div id="main">
	
	<!-- mask frame and loading -->
	<div id="divProgress" style="text-align:center; display: none; position: fixed; top: 50%;  left: 50%;" >
    <br />
    	<font color="#1B3563" size="3px">loading ....</font>
    </div>    
    <div id="divMaskFrame" style="background-color: #F2F4F7; display: none; left: 0px; position: absolute; top: 0px;">
	</div>

	<div id = "head">
		<span id="head_title">${search_head_title}</span>
		<span id="head_desc">${search_head_desc}</span>
	</div>
	<div id="content_div">
		<div id="search_input">
			<input id="page" style="display:none" value="0"></input>
			<input type="text" id="key" ></input>
			<input id="btn" class="btn" value="${search_searchbtn}"></input>
			<span id="advanced_btn" >高级搜索</span>
		</div>	
		<div id="advanced_option_div">
			
			<div id="timespan_div" style="float">
				<label >搜索时间段:</label>
				<label for="from">From</label>
				<input type="text" id="from" name="from">
				<label for="to">to</label>
				<input type="text" id="to" name="to">
			</div>
			<div id="sort_time_div">
				 <label for="sort">搜索结果排序方式:</label>
				 <select id="sort_time_select" >
				 	 <option value="-1" selected>relevance</option>
					 <option value="0">DATE ASC</option>
					 <option value="1" >DATE DSE</option>
				 </select>
			</div>	
			<div id="search_position_div">
				 <label for="sort">搜索的字段</label>
				 <select id="search_position_select" >
				 	 <option value="-1" selected>title and content</option>
					 <option value="0" >title</option>
					 <option value="1">content</option>
				 </select>
			</div>
	</div>
		<div class="result_item_hidden">
			<a class="title_a" name="title" href="http://bbs.csdn.net/topics/370134496">aaaaaaaaaaaaaaaaa</a> <br/>
			<p class="content_p">
				bbbbbbbbbbbbbbbbbb<em>bbbbbb</em>bb
			</p>
			<a class="url_a" name="url" href="http://bbs.csdn.net/topics/370134496">http://bbs.csdn.net/topics/370134496</a>
			<span class="time_span">2014-2-14 12:00</span>
		</div>
	<div id="result_div">
	</div>
</div>
<div id="paging">
		<span>0</span> 
		<span>1</span> 
		<span>2</span>
		<span>3</span>
		<span>4</span>
		<span>5</span>
		<span>6</span> 
		<span>7</span>
		<span>8</span>
		<span>9</span>
		<span>10</span> 
		<span>11</span> 
		<span>12</span>
		<span>13</span>
		<span>14</span>
		<span>15</span>
		<span>16</span> 
		<span>17</span>
		<span>18</span>
		<span>19</span>

	</div>
<div id="footer">
		<div id="contact">
			<span>${contact}</span>
		</div>
	</div>
</div>
</body>
</html>