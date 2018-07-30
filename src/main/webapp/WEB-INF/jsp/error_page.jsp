<%-- 
    Document   : error_page
    Created on : Jul 19, 2018, 3:55:33 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n" prefix="error.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><fmt:message key="message"/></h3>
            </header>
            <div class="12u">
                <a href="<c:url value="action/home"/>" class="button special"><fmt:message key="return"/></a>
            </div>
        </jsp:attribute>
    </t:generic>
</fmt:bundle>
