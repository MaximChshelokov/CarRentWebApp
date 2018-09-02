<%-- 
    Document   : order_view
    Created on : Jul 19, 2018, 10:12:41 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.table.edit" var="edit"/>
    <fmt:message key="application.table.delete" var="delete"/>
    <fmt:message key="application.table.confirm" var="confirm"/>
    <fmt:message key="admin-order-view.approve" var="approve"/>
    <fmt:message key="admin-order-view.reject" var="reject"/>
    <fmt:message key="admin-order-view.caption" var="caption"/>
    <fmt:message key="admin-bill-order.no-userdata" var="no_user_data"/>
    <fmt:message key="admin-bill-order.no-car" var="no_car_data"/>
    <fmt:message key="admin-order-view.no-order-data" var="no_order_data"/>
    <fmt:message key="admin-order-list.car" var="car_title"/>
    <fmt:message key="admin-order-view.user" var="user_title"/>
    <fmt:message key="admin-order-view.rent-order" var="rent_order_details"/>
</fmt:bundle>
<t:generic>
    <jsp:attribute name="content">
        <header class="align-center">
            <h3><c:out value="${caption}"/></h3>
        </header>
        <div class="12u">
            <h4><c:out value="${user_title}"/></h4>
            <c:choose>
                <c:when test="${empty user_data}">
                    <p><c:out value="${no_user_data}"/></p>
                </c:when>
                <c:otherwise>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <fmt:bundle basename="i18n" prefix="admin-user-list.">
                                        <th><fmt:message key="login"/></th>
                                        <th><fmt:message key="name"/></th>
                                        <th><fmt:message key="address"/></th>
                                        <th><fmt:message key="phone"/></th>
                                        </fmt:bundle>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><c:out value="${user_data.user.login}"/></td>
                                    <td><c:out value="${user_data.name}"/></td>
                                    <td><c:out value="${user_data.address}"/></td>
                                    <td><c:out value="${user_data.phone}"/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
            <h4><c:out value="${car_title}"/></h4>
            <c:choose>
                <c:when test="${empty car}">
                    <p><c:out value="${no_car_data}"/></p>
                </c:when>
                <c:otherwise>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <fmt:bundle basename="i18n" prefix="admin-car-list.">
                                        <th><fmt:message key="make"/></th>
                                        <th><fmt:message key="model"/></th>
                                        <th><fmt:message key="year"/></th>
                                        <th><fmt:message key="plate"/></th>
                                        </fmt:bundle>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><c:out value="${car.carModel.carMake.name}"/></td>
                                    <td><c:out value="${car.carModel.name}"/></td>
                                    <td><c:out value="${car.yearOfMake}"/></td>
                                    <td><c:out value="${car.licensePlate}"/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
            <h4><c:out value="${rent_order_details}"/></h4>
            <c:choose>
                <c:when test="${empty order}">
                    <p><c:out value="${no_order_data}"/></p>
                </c:when>
                <c:otherwise>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <fmt:bundle basename="i18n" prefix="admin-order-list.">
                                        <th><fmt:message key="start"/></th>
                                        <th><fmt:message key="end"/></th>
                                        <th><fmt:message key="sum"/></th>
                                        <th><fmt:message key="reviewer"/></th>
                                        </fmt:bundle>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <fmt:bundle basename="i18n">
                                    <td><fmt:formatDate value="${order.startDate}" dateStyle="MEDIUM"/></td>
                                    <td><fmt:formatDate value="${order.endDate}" dateStyle="MEDIUM"/></td>
                                    <td><c:out value="${order.sum}"/></td>
                                    <td><c:out value="${order.approvedBy.login}" default="None"/></td>
                                    </fmt:bundle>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="align-center">

                <ul class="actions">
                    <li>
                        <a href="<c:url value="action/edit_order">
                               <c:param name="id" value="${order.id}"/>
                           </c:url>" class="button special"><c:out value="${edit}"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="action/delete_order">
                               <c:param name="id" value="${order.id}"/>
                           </c:url>" class="button special" onclick="return confirm('${confirm}');"><c:out value="${delete}"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="action/approve_order">
                               <c:param name="id" value="${order.id}"/>
                           </c:url>" class="button"><c:out value="${approve}"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="action/rejection_reason">
                               <c:param name="id" value="${order.id}"/>
                           </c:url>" class="button"><c:out value="${reject}"/></a>
                    </li>   
                </ul>
            </div>
        </div>
    </jsp:attribute>
</t:generic>
