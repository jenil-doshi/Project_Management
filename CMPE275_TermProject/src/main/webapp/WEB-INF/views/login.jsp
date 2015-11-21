<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>login</title>
    </head>
    <body>
    <form th:action="/login" method="post">
            <div><label> User Name : <input type="text" name="username"/> </label></div>
            <div><label> Password: <input type="password" name="password"/> </label></div>
            <div><input type="submit" value="Sign In"/></div>
       
        	<c:if test="${param.error}">
            <div>Invalid username and password.</div>
       		</c:if>
        <c:if test="${param.logout}">
            <div>You have been logged out.</div>
        </c:if>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
         </form>
    </body>
</html>