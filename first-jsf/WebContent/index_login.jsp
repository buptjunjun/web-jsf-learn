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
   <h:form>
     <h3>please input your name and password</h3>
     <table>
         <tr>
             <td>Name:</td>
             <td><h:inputText id="userName" value="#{bbUserBean.name}" required = "true"  /> </td>
             <h:message for="userName"></h:message>          
         </tr>
         
         <tr>
             <td>Password:</td>
             <td><h:inputSecret id="password" value="#{bbUserBean.password}" required = "true"  /> </td>
             <h:message for="password"></h:message>           
         </tr>
     </table>
     
     <h:commandButton value="login" action = "#{bbUserBean.loginAction}"></h:commandButton>
   </h:form>
   <h:messages globalOnly = "true"> </h:messages>
</f:view>
</body>
</html>