<%-- 
    Document   : welcome
    Created on : Jun 5, 2018, 9:11:44 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:generic>
    <jsp:attribute name="content">
        <h1>${user.login}</h1>
        <p>Your login: ${login}</p>
        <p>Your role: ${user.role.roleName}
        </jsp:attribute>
    </t:generic>
