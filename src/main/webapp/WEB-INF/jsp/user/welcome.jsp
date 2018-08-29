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
            <div class="12u">
                <p><fmt:message key="user-welcome.message"/></p>
                <c:if test="${not empty rent_order}">
                    <p>
                        <fmt:message key="user-welcome.rejected"/><br>
                    <c:out value="${rent_order.rejectionReason.reason}"/>
                    <p/>
                </c:if>
                <a href="<c:url value="action/user_data"/>" class="button"><fmt:message key="user-welcome.select"/></a>
            </div>
        </jsp:attribute>
    </t:generic>
</fmt:bundle>
