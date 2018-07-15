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
                    <a href="action/car_list" class="button" ><fmt:message key="car-list"/></a>
                    <label>12 cars available, 10 cars in rent</label>
                </li>
                <li>
                    <a href="action/user_list" class="button" ><fmt:message key="user-list"/></a>
                    <label>8 user accounts, 2 administrator accounts</label>

                </li>
                <li>
                    <a href="action/overdue" class="button" ><fmt:message key="overdue"/></a>
                    <label>1 rent is overdue</label>

                </li>
                <li>
                    <a href="action/application" class="button" ><fmt:message key="orders"/></a>
                    <label>3 orders awaiting to approve</label>
                </li>
            </ul>
        </div>
    </jsp:attribute>
</t:generic>
</fmt:bundle>
