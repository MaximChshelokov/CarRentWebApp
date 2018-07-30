<%-- 
    Document   : user-panel
    Created on : Jul 18, 2018, 1:46:47 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@tag description="User panel" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n" prefix="user.">
    <h3><fmt:message key="logged"/></h3>
<div class="12u">
    <p>
        <c:out value="${user.login}"/>
    </p>
</div>
<ul class="actions vertical">
    <c:choose>
        <c:when test="${user.role.id == 1}">

            <li>
                <a href="<c:url value="action/admin_actions"/>" class="button special"><fmt:message key="admin-actions"/></a>
            </li>


        </c:when>
        <c:otherwise>
            <li>
                <a href="<c:url value="action/invoice"/>" class="button special"><fmt:message key="invoices"/></a>
            </li>
            <li>
                <a href="<c:url value="action/edit_profile"/>" class="button special"><fmt:message key="account"/></a>
            </li>
        </c:otherwise>
    </c:choose>
    <li>
        <a href="<c:url value="action/log_out"/>" class="button"><fmt:message key="logout"/></a>
    </li>
</ul>
</fmt:bundle>