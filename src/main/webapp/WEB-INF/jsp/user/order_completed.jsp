<%-- 
    Document   : order_completed
    Created on : Jul 20, 2018, 5:31:08 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n" prefix="user-order-complete.">
    <div class="align-center">
        <fmt:message key="message"/>
        <a href="action/home" class="button"><fmt:message key="return"/></a>
    </div>
</fmt:bundle>

