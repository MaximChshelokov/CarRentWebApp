<%-- 
    Document   : invoices
    Created on : Jul 23, 2018, 2:48:32 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="admin-bill-order.surcharge" var="surcharge"/>
    <fmt:message key="admin-bill-order.paid" var="paid"/>
    <fmt:message key="user-invoice.caption" var="caption"/>
    <fmt:message key="user-invoice.pay" var="pay"/>
    <fmt:message key="user-order-complete.return" var="home"/>
</fmt:bundle>
<t:generic>
    <jsp:attribute name="content">
        <header class="align-center">
            <h3><c:out value="${caption}"/></h3>
        </header>
        <div class="12u">
            <h4>Car</h4>
            <c:choose>
                <c:when test="${empty car}">
                    <p>There is no information about the car</p>
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
            <h4>Rent order details</h4>
            <c:choose>
                <c:when test="${empty invoice_lines or empty invoice}">
                    <p>There isn't any unpaid invoices</p>
                </c:when>
                <c:otherwise>
                    <fmt:bundle basename="i18n" prefix="admin-bill-order.">
                        <div class="table-wrapper">
                            <table>
                                <thead>
                                    <tr>
                                        <th><fmt:message key="details"/></th>
                                <th><fmt:message key="amount"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${invoice_lines}" var="invoice_line">
                                    <tr>
                                        <td><c:out value="${invoice_line.details}"/></td>
                                    <td><c:out value="${invoice_line.amount}"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td><fmt:message key="total"/></td><td><c:out value="${invoice.total}"/></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="paid"/></td><td><c:out value="${invoice.paid}"/></td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </fmt:bundle>
                </c:otherwise>
            </c:choose>
                    <div class="align-center">
                        <ul class="actions">
                            <c:choose>
                                <c:when test="${empty invoice}">
                                    <li>
                                        <a href="<c:url value="action/home"/>" class="button special"><c:out value="${home}"/></a>
                                    </li>                    
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="<c:url value="action/pay_check"/>" class="button special"><c:out value="${pay}"/></a>
                                    </li>
                                </ul>
                            </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
    </jsp:attribute>
</t:generic>

