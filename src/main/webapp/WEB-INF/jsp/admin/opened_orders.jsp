<%-- 
    Document   : opened_orders
    Created on : Jul 22, 2018, 5:09:15 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.misc.no" var="no"/>
    <fmt:message key="admin-opened-view.close" var="close"/>
    <fmt:message key="admin-opened-view.caption" var="caption"/>
    <fmt:message key="admin-opened-view.no-orders" var="no_orders"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-order-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <div class="12u">
                <c:choose>
                    <c:when test="${empty order_list}">
                        <p><c:out value="${no_orders}"/></p>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrapper">
                            <table class="alt">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="start"/></th>
                                        <th><fmt:message key="end"/></th>
                                        <th><fmt:message key="car"/></th>
                                        <th><fmt:message key="user"/></th>
                                        <th><fmt:message key="sum"/></th>
                                        <th><fmt:message key="reviewer"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${order_list}" var="order">
                                        <tr>
                                            <td><fmt:formatDate value="${order.startDate}" dateStyle="SHORT"/></td>
                                            <td><fmt:formatDate value="${order.endDate}" dateStyle="SHORT"/></td>
                                            <td><c:out value="${order.car.carModel.carMake.name}"/> <c:out value="${order.car.carModel.name}"/> (<c:out value="${order.car.yearOfMake}"/>)</td>
                                            <td><c:out value="${order.user.login}"/></td>
                                            <td><c:out value="${order.sum}"/></td>
                                            <td><c:out value="${order.approvedBy.login}" default="${no}"/></td>
                                            <td><a href="<c:url value="action/bill_order">
                                                       <c:param name="id" value="${order.id}"/>
                                                   </c:url>" class="table button special"><c:out value="${close}"/></a></td>
                                        </tr>                                   
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </jsp:attribute>
    </t:generic>
</fmt:bundle>

