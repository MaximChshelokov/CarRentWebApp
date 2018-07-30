<%-- 
    Document   : admin-actions
    Created on : Jul 5, 2018, 8:29:53 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:bundle basename="i18n" prefix="admin-actions.">
<t:generic>
    <jsp:attribute name="content">
        <div class="row">
            <ul class="actions vertical">
                <li>
                    <a href="<c:url value="action/car_list"/>" class="button" ><fmt:message key="car-list"/></a>
                </li>
                <li>
                    <a href="<c:url value="action/user_list"/>" class="button" ><fmt:message key="user-list"/></a>

                </li>
                <li>
                    <a href="<c:url value="action/opened_orders"/>" class="button" ><fmt:message key="opened"/></a>

                </li>
                <li>
                    <a href="<c:url value="action/order_list"/>" class="button" ><fmt:message key="orders"/></a>
                </li>
            </ul>
        </div>
    </jsp:attribute>
</t:generic>
</fmt:bundle>
