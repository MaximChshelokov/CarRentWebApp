<%-- 
    Document   : already_rented
    Created on : Aug 19, 2018, 4:59:21 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="error.return" var="return_msg"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="user-already-rented.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><fmt:message key="caption"/></h3>
            </header>
            <div class="12u">
                <p><fmt:message key="message"/></p>
                <a href="<c:url value="action/home"/>" class="button special"><c:out value="${return_msg}"/></a>
            </div>
        </jsp:attribute>
    </t:generic>
</fmt:bundle>
