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
    <fmt:message key="admin-order-view.caption" var="caption"/>
</fmt:bundle>
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <div class="12u">
                <h4>User</h4>
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
                <h4>Car</h4>
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
                                <td><c:out value="${car.model.make.name}"/></td>
                                <td><c:out value="${car.model.name}"/></td>
                                <td><c:out value="${car.yearOfMake}"/></td>
                                <td><c:out value="${car.licensePlate}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <h4>Rent order details</h4>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <fmt:bundle basename="i18n" prefix="admin-order-list.">
                                    <th><fmt:message key="start"/></th>
                                    <th><fmt:message key="end"/></th>
                                    <th><fmt:message key="sum"/></th>
                                    <th><fmt:message key="approved"/></th>
                                </fmt:bundle>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${order.startDate}"/></td>
                                <td><c:out value="${order.endDate}"/></td>
                                <td><c:out value="${order.sum}"/></td>
                                <td><c:out value="${order.approvedBy.login}" default="None"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="align-center">
                    
                    <ul class="actions">
                        <li>
                            <a href="action/edit_order&id=${order.id}" class="button special"><c:out value="${edit}"/></a>
                        </li>
                        <li>
                            <a href="action/delete_order&id=${order.id}" class="button special" onclick="return confirm('${confirm}');"><c:out value="${delete}"/></a>
                        </li>
                        <li>
                            <a href="action/approve_order?id=${order.id}" class="button"><c:out value="${approve}"/></a>
                        </li>
                    </ul>
                </div>
            </div>
        </jsp:attribute>
    </t:generic>
