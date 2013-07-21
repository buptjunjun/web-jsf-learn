	var urlGetMovie = "http://127.0.0.1:8080/movie/movie?type=get&magicNum=1&limit=3";
	var urlUpdateMovie = "http://127.0.0.1:8080/movie/movie?type=update&magicNum=1000&id=";
	var urlGetResource = "http://127.0.0.1:8080/movie/resource?type=get&magicNum=-1&url=";
	var urlDeleteResource = "http://127.0.0.1:8080/movie/resource?type=update&magicNum=1000&id=";
	function loadXMLDoc(method,url,callback,vars)  
	{  
		xmlhttp=null;  
		if (window.XMLHttpRequest)  
		{// code for IE7, Firefox, Opera, etc.   
		  xmlhttp=new XMLHttpRequest();  
		}  
		else if (window.ActiveXObject)  
		{// code for IE6, IE5   
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");  
		}  
		if (xmlhttp!=null)  
		{  
			if(vars!=null)
			{
			    xmlhttp.onreadystatechange=function(){callback(vars)};
			}
			else 
			{
				xmlhttp.onreadystatechange=callback;  
			}
			
			xmlhttp.open(method,url,true);  
			xmlhttp.send(null);  
		}  
		else  
		{  
		  alert("Your browser does not support XMLHTTP.");  
		}  
	}  
	
	/**
	* show contnet of htmlrequest
	*/
	function showResult()  
	{  
		if (xmlhttp.readyState==4)  
		{// 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			alert(xmlhttp.responseText);  
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		}  
    }
	
	/**
	* show contnet of htmlrequest
	*/
	function showResult()  
	{  
		if (xmlhttp.readyState==4)  
		{// 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			alert( xmlhttp.responseText);  
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		}  
    }


	function addMovie()
	{
		if (xmlhttp.readyState==4)  
		{
		  unloading();
		  // 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			var jsonstr =  xmlhttp.responseText;  
			var json=eval('('+jsonstr+')');
			//alert(json.length);
			var ss = document.getElementById("tocopy");
			for( var i = 0;i < json.length;i++)
			{
				var sscopy = ss.cloneNode(true);
				sscopy.style.display = 'block'; 
				var jsonObj = json[i];
				sscopy.children[0].children[2].innerHTML = jsonObj.url;
				sscopy.children[0].children[2].href = jsonObj.url;
				sscopy.children[0].children[3].innerHTML = jsonObj.id;
				sscopy.children[0].children[4].innerHTML = jsonObj.name;
				sscopy.children[0].children[6].innerHTML = jsonObj.description;
				ss.parentNode.appendChild(sscopy);
			}
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		} 
		else
		{
			loading();
		}
		
	}
	
	function addResource(element)
	{
		if (xmlhttp.readyState==4)  
		{
		  unloading();
			// 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			var jsonstr =  xmlhttp.responseText;  
			var json=eval('('+jsonstr+')');
			//alert(element);
			if(json.length == 0)
			{
				alert("no data");
				return;
			}
			
			var resource = element.parentNode.nextElementSibling.firstElementChild;
			for( var i = 0;i < json.length;i++)
			{
				var jsonObj = json[i];
				var rcopy = resource.cloneNode(true);
				rcopy.style.display = 'block'; 
				rcopy.children[1].innerHTML = jsonObj.id;
				rcopy.children[2].href = jsonObj.resourceURL;
				rcopy.children[2].innerHTML = jsonObj.resourceURL;
				
				rcopy.children[3].innerHTML = jsonObj.resourceType;
				rcopy.children[3].innerHTML = jsonObj.movieDescription;
				resource.parentNode.appendChild(rcopy);
			}
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		}  
		else
		{
			loading();	
		}
		
	}

	function deleteResource(element)
	{
		if (xmlhttp.readyState==4)  
		{
		  unloading();
			// 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			var jsonstr =  xmlhttp.responseText;  
				alert(jsonstr);
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		}  
		else
		{
			loading();	
		}
		
	}
	
	function updateMovieResult()
	{
		if (xmlhttp.readyState==4)  
		{
		  unloading();
			// 4 = "loaded"   
		  if (xmlhttp.status==200)  
		  {// 200 = "OK"   
			var jsonstr =  xmlhttp.responseText;  
				alert(jsonstr);
		  }  
		  else  
		  {  
			alert("Problem retrieving XML data:" + xmlhttp.statusText);  
		  }  
		}  
		else
		{
			loading();	
		}
		
	}
	
	function test()
	{
		loadXMLDoc("GET",urlGetMovie,showResult);
	}
	
	function getMovieDetail()
	{
		loadXMLDoc("GET",urlGetMovie,addMovie);
	}
	
	function getResourceDetail(element)
	{
		var urle = element.nextElementSibling.nextElementSibling;
		var url = urle.innerHTML;
		//url="http://movie.douban.com/subject/4212172";
		url=urlGetResource+url;
		loadXMLDoc("GET",url,addResource,element);
	}
	
	function deleteResourceDetail(element)
	{
		element.disabled = true;
		var ide = element.nextElementSibling;
		var id = ide.innerHTML;
		//url="http://movie.douban.com/subject/4212172";
		url=urlDeleteResource+id;
		loadXMLDoc("POST",url,deleteResource,element);
		
	}
	
	function updateMovie(element)
	{
		element.disabled = true;
		var ide = element.nextElementSibling.nextElementSibling;
		var id = ide.innerHTML;
		//url="http://movie.douban.com/subject/4212172";
		url=urlUpdateMovie+id;
		loadXMLDoc("GET",url,updateMovieResult);
		
	}
	
	function loading()
	{
		 document.getElementById("show").innerHTML="<div align='center'><img src='img/loading.gif' /> Loading......</div>";
	}
	function unloading()
	{
		 document.getElementById("show").innerHTML="";
	}