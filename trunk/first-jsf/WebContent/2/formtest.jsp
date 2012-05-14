<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>
<h:form   id="helloForm">
    <table>
        <tr>
        <td>Name:</td>
        <td>
           <h:inputText id="userName" 
           				value="#{form.userName}" 
           				required="true" 
           				size="10"
           	>
        
           </h:inputText>
        </td>
        <h:message for="userName"></h:message>
        </tr>
    </table>
    <h:commandButton id="formCommand"
                     type="submit" 
                     value="Submit" 
                     action="#{form.submit}" 
                     >
    </h:commandButton>
</h:form>
</f:view>
</body>
</html>