<%-- 
    Document   : welcome
    Created on : Jul 23, 2018, 7:40:21 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
<t:generic>
    <jsp:attribute name="content">
        <p><fmt:message key="user-welcome.message"/></p>
        <a href="action/select_car" class="button"><fmt:message key="user-welcome.select"/></a>
    </jsp:attribute>
</t:generic>
</fmt:bundle>
