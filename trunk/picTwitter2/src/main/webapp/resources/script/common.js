


function add(item)
{
	var $span = $(item).children()[0];
	var current = $($span).text();
	$($span).text(parseInt(current)+1);	
}

function rating(id,url1)
{
	$.ajax({
			type: "post",	
			contentType: "text/plain; charset=utf-8",  
			url: url1,
			data:id, 
			success:function (data)   // request success.
			{				
			}
	  });
}

var goodurl = host+"/api/good";
function good(id)
{
	
	rating(id,goodurl);
}

var badurl = host+"/api/bad";
function bad(id)
{	
	rating(id,badurl);
}

var collecturl = host+"/api/collect";
function collect(id,item)
{	
	if(testlogin())
	{
		
		alert(id);
		$.ajax({
				type: "post",		
				contentType: "text/plain; charset=utf-8",  
				url: collecturl,
				data:id, 
				success:function (data)   // request success.
				{				
					if(data=="true")
					{
						$(item).prop("class","img_background collect1");
						$(item).off();
						add(item);
					}
					else
					{
						alert("fail");	
					}
				}
		  });
	}
	else
	{
		alert("please login firstly!");	
	}
}
