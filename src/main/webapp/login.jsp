<%-- 
    Document   : login
    Created on : Jun 4, 2018, 5:24:11 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <c:if test="${errorLogin==1}">
            <p>No such user!</p>
        </c:if>
        <form method="post" action="login">
        Email ID:<input type="text" name="email" /><br/>
        Password:<input type="text" name="pass" /><br/>
        <input type="submit" value="login" />
        </form>
    </body>
</html>
