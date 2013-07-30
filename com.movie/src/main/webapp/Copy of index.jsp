<html>
<head>
<script type="text/javascript" src="js/movie.js"></script>
	
<style> 
	#movie
	{ 
	 border:1px solid red;
	 margin-top:5px;
	}; 
	
	#resource
	{ 
	 border:1px solid red;
	 margin-top:5px;
	} 
</style> 
</head>
<body>
<input id="Button1" type="button" value="getMovie"  onclick="getMovieDetail()"/>
<div>
	<div id="show"></div>
	<div id="tocopy" class="movie" style="display:none">
		<div id = "movie" >
			<input  type="button" value="Get Resoruces"  onclick ="getResourceDetail(this)"/>
			<input  type="button" value="Update"  onclick ="updateMovie(this)"/>
			<a id="url" href="" target="_blank"> url</a>
			<span id="id">url</span>
			<span id ="name">name</span>
			<br/>
			<span id="des">description</span>
			
		</div>
		<div id="resources" class="resource" style="display:block;margin-left:20px">
			<div style="display:none;margin-left:20px">
				<input type="button" value="delete Resource"  onclick ="deleteResourceDetail(this)" />
				<span id ="id">id</span>
				<a id ="url" href="" target="_blank">url</a>
				<span id="resourceType">resourceType</span>	
				<span id="movieDescription">movieDescription</span>					
			</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>
