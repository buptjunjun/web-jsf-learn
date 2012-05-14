<%@page contentType="text/html; charst=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>  hello application</title>
</head>
  <body>
      <f:view>
        <h:form id = "helloForm">
           <h3>please input your name</h3>
        <table>
          <tr>
	          <td> name</td> 
	          <td>
	              <h:inputText  id="userName" value="#{hello.userName}" required="true"></h:inputText>
	              <h:message for="userName"></h:message> 
	          </td>
          </tr>
        </table>
        <br />
        <h:commandButton id ="helloCommand"
						 type = "submit"
						 value="submit"    
						 action="#{hello.sayHelloAction}"    
        >
        </h:commandButton>
        </h:form>
        <h:messages globalOnly="true"></h:messages>
      </f:view>
  </body>
</html>