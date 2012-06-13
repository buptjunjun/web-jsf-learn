<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="jquery-1.7.1.js"></script>
<script type="text/javaScript">

	function selectAllModule()
	{
		var moduleCheckBox= document.getElementsByTagName("input")
		var patrn=  /^logForm:moduleTable:/;
		var all = document.getElementById("logForm:moduleTable:selectAll");
	//   var all = document.getElementById(allid);
	
		for(var i = 0; i < moduleCheckBox.length; i++ )
		{
			if(moduleCheckBox[i].type=="checkbox" )
			{
				var s = moduleCheckBox[i].id;
				if(patrn.exec(s) != null)
				{	
			       //	  alert(moduleCheckBox[i].checked +"  " + moduleCheckBox[i].type) ;					
					if(all.checked == true)
					{
						moduleCheckBox[i].checked = true;
					}
					else 
					{
						moduleCheckBox[i].checked = false;
					}
				}
			}
		}
	}

	
	function selectAllLog(alllog ,pattern)
	{
		var all = alllog;
		//alert(all);
		var moduleCheckBox= document.getElementsByTagName("input")
		var patrn=  /^logForm:logTable:/;
		//var all = document.getElementById("logForm:logTable:selectAll");
	//   var all = document.getElementById(allid);
	
		for(var i = 0; i < moduleCheckBox.length; i++ )
		{
			if(moduleCheckBox[i].type=="checkbox" )
			{
				var s = moduleCheckBox[i].id;
			//	alert(s);
				if(patrn.exec(s) != null)
				{	
			       //	  alert(moduleCheckBox[i].checked +"  " + moduleCheckBox[i].type) ;					
					if(all.checked == true)
					{
						moduleCheckBox[i].checked = true;
					}
					else 
					{
						moduleCheckBox[i].checked = false;
					}
				}
			}
		}
	}

	
	function selectOneLog( onelog )
	{
		var thislog = onelog;
		//alert(thislog.checked);
		
		var moduleCheckBox= document.getElementsByTagName("input")
		var patrn=  /^logForm:logTable:/;
		var all = document.getElementById("logForm:logTable:selectAll");
	//   var all = document.getElementById(allid);
	
	  
	    if(thislog.checked == false)
	    { 
	    	var flag  = false;
			for(var i = 0; i < moduleCheckBox.length; i++ )
			{		
				var s = moduleCheckBox[i].id;
				//alert(s);
				if(patrn.exec(s) != null &&  s != "logForm:logTable:selectAll")
				{	
			       //	  alert(moduleCheckBox[i].checked +"  " + moduleCheckBox[i].type) ;					
					if( moduleCheckBox[i].checked == true)
					{
					    flag = true;
					}			
				}
				
				if(flag == false ) all.checked = false;
			
			}
		}
	    else if(thislog.checked == true)
	    { 
	    	var flag  = false;
			for(var i = 0; i < moduleCheckBox.length; i++ )
			{		
				var s = moduleCheckBox[i].id;
				//alert(s);
				if(patrn.exec(s) != null && s != "logForm:logTable:selectAll")
				{	
			       	 // alert(moduleCheckBox[i].checked +"  " + moduleCheckBox[i].type) ;					
					if( moduleCheckBox[i].checked == false)
					{
					    flag = true;
					}			
				}
				
				if(flag == false) all.checked = true;
				else all.checked =false;
			}
		}
	}
	
	
	function testReg()
	{
		var patrn=  /^logForm:/;
		var ret = patrn.exec("logForm:sdafasdf");
		alert(ret+"   ");
	}
	
	function testSelect()
	{
		var select = document.getElementsById("^logForm:logTable:*");
		for(var i = 0; i < select.length; i++)
		{
			alert(select[i].type);
		}
	}
	
	function testAlert( v)
	{
		var a= "adg";
		var b = "adg";
		alert(a==b);
		alert(v);
	}
</script>


<body>
<f:view>
<h:form id="logForm">
	  	<h:dataTable id="moduleTable"   value="#{BBLog.modules}"  var="row"  binding="#{BBLog.dataTable}" >
				<h:column id="moduleCheckbox">
					<f:facet name="header" >
							<h:selectBooleanCheckbox id="selectAll"  value=""  onclick="selectAllModule()"   ></h:selectBooleanCheckbox>
					 </f:facet>
					 <h:selectBooleanCheckbox  value="#{row.selected }"  ></h:selectBooleanCheckbox>
				  </h:column>	
			
				<h:column >
				<f:facet name="header">
					<h:outputText value="Selecte All"></h:outputText>
				</f:facet>
				<h:outputText  value="#{row.name }"  />
			</h:column>
			</h:dataTable>
			
			<h:dataTable id="logTable"   value="#{BBLog.modules}"  var="row" >
				<h:column id="moduleCheckbox">
					<f:facet name="header" >
							<h:selectBooleanCheckbox id="selectAll"  value=""  onclick="selectAllLog(this)"   ></h:selectBooleanCheckbox>
					 </f:facet>
					 <h:selectBooleanCheckbox  value="#{row.selected }"  onclick="selectOneLog(this)"></h:selectBooleanCheckbox>
				  </h:column>	
			
				<h:column >
				<f:facet name="header">
					<h:outputText value="Selecte All"></h:outputText>
				</f:facet>
				<h:outputText  value="#{row.name }"  />
			</h:column>
			</h:dataTable>
			
			
			
			<h:commandButton  value="submit"  action ="#{BBLog.submit}"  onclick="testAlert(this)"></h:commandButton>
</h:form>
</f:view>
</body>
</html>