<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pic galaxy</title>

<style type="text/css">
	ul 
	{
		list-style:none;
		margin:0px;
		padding:0px;
	}
	ul li
	{
		margin:0px;
		padding:2px;
	}
	#nav
	{
		top:0px;
		width:100%;
		height:40px;
		border: 1px  #DFDFE0;
		z-index: 999;
		position:fixed;
		padding-bottom:10px;
	}
	#navdetail
	{
		padding-top:5px;
		width:100%;
		margin:auto;
		height:30px;
		border: 1px  black;
		z-index: 999;
	}
	
	#content
	{
		margin:auto;
		width:1024px;
		height:auto;
		text-align:center;
		padding-top:75px;
	}
	#left
	{
	 width:700px;
	 height:auto;
	 float:left;
	 border:1px solid #D9D9D6
	}
	#right
	{
	 width:300px;
	 height:auto;
	 float:left;
	 	border: 1px  black;
	}
	#comment
	{
		width:680px;
		margin:auto;
		
		
	}
	
	.cmtContex
	{
		height:50px;
		width:500px;
	}
	#submit
	{
		width:560px;
		height:60px;
		margin:auto;
		
	}
	
	#submitBtn
	{
	    
	 -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #334455;
    background-image: -moz-linear-gradient(center top , #334455, #334455);
    background-repeat: repeat-x;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    border-image: none;
    border-radius: 5px 5px 5px 5px;
    border-style: ;
    border-width: 1px;
    box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05);
    color: #FFFFFF;
    cursor: pointer;
    display: inline-block;
    float: right;
    font-size: 14px;
    height: 34px;
    line-height: 18px;
    margin-bottom: 0;
    padding: 4px 10px;
    text-align: center;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
    vertical-align: middle;
    width: 65px;
	}
	
	#comments
	{
		width:680px;
		margin:auto;
	
	}
	.oneComment
	{
		width:575px;
		height:auto;
		border-bottom: 1px dotted #D9D9D6;
		margin:auto;
		padding-top:10px;
	}
	#pic
	{
		padding-top:10px;
	}
	.userPic
	{	
		display:inline;
		width:60px;
		float:left;
		
	}
	.usercomment
	{
		height:55px;
		width:450px;
		float:left;
		text-align:left;
		
	}
	
</style>
</head>
<body>
	<div id="nav">
			<div id="navdetail">
			
			</div>
	</div>

	<div id="content">
		<div id="left">
			<div id="pic">
				<img width=600  src="http://p0.pstatp.com/large/319/4817198140"/>
				<p>黑色一如既往的显瘦加显气质~~</p>
			</div>
			
			<div id="comment">
				<img  src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
				<textarea class="cmtContex" id="comment-box" placeholder="say something..." selectionstart="0" selectionend="0"></textarea>
				<div id="submit">
					<button id="submitBtn">submit</button>
				</div>
				
			</div>
			
			<div id="comments">
				<div class="oneComment">
					<div class="userPic">
						<img  src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
						<div style="clear:both"></div>
					</div>
					<div class="usercomment">
						<ul>
							<li>
								<span>开心 :</span> 
								<span> 鞋子高跟的更好，黑色的裙子最好有点装饰</span>
							</li>
							<li>
								<span>09-13 21:26</span> 
							</li>
						</ul>
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
				</div>
				
								<div class="oneComment">
					<div class="userPic">
						<img  src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
						<div style="clear:both"></div>
					</div>
					<div class="usercomment">
						<ul>
							<li>
								<span>开心 :</span> 
								<span> 鞋子高跟的更好，黑色的裙子最好有点装饰</span>
							</li>
							<li>
								<span>09-13 21:26</span> 
							</li>
						</ul>
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
				</div>
				
				
								<div class="oneComment">
					<div class="userPic">
						<img  src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
						<div style="clear:both"></div>
					</div>
					<div class="usercomment">
						<ul>
							<li>
								<span>开心 :</span> 
								<span> 鞋子高跟的更好，黑色的裙子最好有点装饰</span>
							</li>
							<li>
								<span>09-13 21:26</span> 
							</li>
						</ul>
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
				</div>
				
				
								<div class="oneComment">
					<div class="userPic">
						<img  src="http://tp1.sinaimg.cn/1641153660/50/5627699277/1" />
						<div style="clear:both"></div>
					</div>
					<div class="usercomment">
						<ul>
							<li>
								<span>开心 :</span> 
								<span> 鞋子高跟的更好，黑色的裙子最好有点装饰</span>
							</li>
							<li>
								<span>09-13 21:26</span> 
							</li>
						</ul>
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>
			<div style="clear:both"></div>
		</div>

		<div id="right">
			
		</div>
	</div>
</body>
</html>