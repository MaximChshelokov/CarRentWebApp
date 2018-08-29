<%-- 
    Document   : order_list
    Created on : Jul 17, 2018, 2:58:29 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.misc.no" var="no"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-order-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><fmt:message key="caption"/></h3>
            </header>
            <div class="12u">
                <c:choose>
                    <c:when test="${empty order_list}">
                        <p><fmt:message key="no-orders"/></p>
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
                                        <th><fmt:message key="status"/></th>
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
                                            <td><c:choose>
                                                    <c:when test="${empty order.approvedBy.login}"><fmt:message key="status-not-processed"/></c:when>
                                                    <c:when test="${empty order.rejectionReason.reason}"><fmt:message key="status-approved"/></c:when>
                                                    <c:otherwise><fmt:message key="status-rejected"/></c:otherwise>
                                            </c:choose></td>
                                            <td><c:out value="${order.approvedBy.login}" default="${no}"/></td>
                                            <td><a href="<c:url value="action/view_order">
                                                       <c:param name="id" value="${order.id}"/>
                                            </c:url>" class="table button special"><fmt:message key="view"/></a></td>
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
