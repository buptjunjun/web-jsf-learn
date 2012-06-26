<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javaScript">

		/**
		 *   @param allCheckbox: object of the "select-allcheckbox,"   
		 *   @param pattern: the pattern of the id String of  checkbox
		 */
		function selectAllCheckBox(allCheckbox ,parentTable)
		{
		//	 alert("select all");
			var parent = document.getElementById(parentTable);		
			var all = parent.getElementsByTagName("input");
			
			//alert(all.length);
			for ( var i = 0; i < all.length; i++)
			{
				if (allCheckbox.checked == true)
					all[i].checked = true;
				else 
					all[i].checked = false;
			}
			
		}

	 
       /**
       *   @param oncheckbox: object of the selected checkbox,   
       *   @param pattern: the pattern of the id String of  checkbox
       *   @param pattern: the id of "select-all checkbox" 
       */
		function selectOneCheckbox( onecheckbox , selectAllckbox , parentTable )
		{
    	//   alert("heihei");
    	    var parent = document.getElementById(parentTable);
    	    var selectAll = document.getElementById(selectAllckbox);
			
			var all = parent.getElementsByTagName("input");
		//	alert(all.length);
			var count = 0;
			for ( var i = 0; i < all.length; i++)
			{
					if(all[i] == selectAll)
						continue;
					
					if(all[i].checked == true)
						count++;
			}
			
			if (count == all.length - 1)
				selectAll.checked = true;
			else 
				selectAll.checked = false;
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
							<h:selectBooleanCheckbox id="selectAll"  value=""  onclick="selectAllCheckBox(this,'logForm:moduleTable' )"   ></h:selectBooleanCheckbox>
					 </f:facet>
					 <h:selectBooleanCheckbox  value="#{row.selected }"    onclick="selectOneCheckbox(this , 'logForm:moduleTable:selectAll', 'logForm:moduleTable' )"></h:selectBooleanCheckbox>
				  </h:column>	
			
				<h:column >
				<f:facet name="header">
					<h:outputText value="Selecte All"></h:outputText>
				</f:facet>
				<h:outputText  value="#{row.name }"  />
			</h:column>
			</h:dataTable>
			
			<h:dataTable id="logTable"    border="1"  rows ="1"  value="#{BBLog.modules}"  var="row" >
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
			<br>
			<br>
			
		<h:commandButton  value="submit"  action ="#{BBLog.submit}"  onclick="testAlert(this)"></h:commandButton>
</h:form>
</f:view>
</body>
</html>