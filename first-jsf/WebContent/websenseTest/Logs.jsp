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

		/**
		 *   @param allCheckbox: object of the "select-allcheckbox,"   
		 *   @param pattern: the pattern of the id String of  checkbox
		 */
		function selectAllCheckBox(allCheckbox ,pattern)
		{
			var all = allCheckbox;
			//alert(all);
			var moduleCheckBox= document.getElementsByTagName("input")
			//var patrn=  /^logForm:logTable:/;
			var patrn = new  RegExp(pattern);
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

	 
       /**
       *   @param oncheckbox: object of the selected checkbox,   
       *   @param pattern: the pattern of the id String of  checkbox
       *   @param pattern: the id of "select-all checkbox" 
       */
		function selectOneCheckbox( onecheckbox , pattern , allcheckbox )
		{
			var thislog = onecheckbox;
			//alert(thislog.checked);
			
			var moduleCheckBox= document.getElementsByTagName("input")
			var patrn=  new  RegExp(pattern);
			var all = document.getElementById(allcheckbox);
		  
		    if(thislog.checked == false)
		    { 
		    	var flag  = false;
				for(var i = 0; i < moduleCheckBox.length; i++ )
				{		
					var s = moduleCheckBox[i].id;
					//alert(s);
					if(patrn.exec(s) != null &&  s != allcheckbox)
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
					if(patrn.exec(s) != null && s != allcheckbox)
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
							<h:selectBooleanCheckbox id="selectAll"  value=""  onclick="selectAllCheckBox(this,'^logForm:moduleTable:'  )"   ></h:selectBooleanCheckbox>
					 </f:facet>
					 <h:selectBooleanCheckbox  value="#{row.selected }"    onclick="selectOneCheckbox(this , '^logForm:moduleTable:' ,'logForm:moduleTable:selectAll')"></h:selectBooleanCheckbox>
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
							<h:selectBooleanCheckbox id="selectAll"  value=""  onclick="selectAllCheckBox(this,'^logForm:logTable:')"   ></h:selectBooleanCheckbox>
					 </f:facet>
					 <h:selectBooleanCheckbox  value="#{row.selected }"  onclick="selectOneCheckbox(this , '^logForm:logTable:' ,'logForm:logTable:selectAll')"></h:selectBooleanCheckbox>
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