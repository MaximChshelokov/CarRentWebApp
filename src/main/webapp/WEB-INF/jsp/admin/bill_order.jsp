<%-- 
    Document   : bill_order
    Created on : Jul 22, 2018, 7:28:30 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n" prefix="admin-bill-order.">
    <fmt:message key="caption" var="caption"/>
    <fmt:message key="surcharge" var="surcharge"/>
    <fmt:message key="paid" var="paid"/>
    <fmt:message key="close" var="close"/>
    <fmt:message key="no-userdata" var="no_user_data"/>
    <fmt:message key="no-car" var="no_car"/>
    <fmt:message key="no-invoice" var="no_invoice"/>
</fmt:bundle>
<t:generic>
    <jsp:attribute name="content">
        <header class="align-center">
            <h3><c:out value="${caption}"/></h3>
        </header>
        <div class="12u">
            <h4>User</h4>
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
            <h4>Car</h4>
            <c:choose>
                <c:when test="${empty car}">
                    <p><c:out value="no_car"/></p>
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
                    <p><c:out value="no_invoice"/></p>
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
            <hr>
            <t:validation-message/>
            <form method="POST" action="<c:url value="action/close_order">
                      <c:param name="id" value="${invoice.id}"/>
                  </c:url>">
                <fmt:bundle basename="i18n" prefix="admin-bill-order.">
                    <div class="row uniform">
                        <div class="6u 12u">
                            <input type="checkbox" id="fine-check" name="fine-check" ${empty invoice_line ? "" : "checked"}>
                            <label for="fine-check"><fmt:message key="fine-check"/></label>
                        </div>
                        <div class="6u 12u">
                            <input type="text" name="reason" placeholder="<fmt:message key="reason"/>" value="${invoice_line.details}"/>
                        </div>
                        <div class="12u">
                            <input type="number" name="amount" step="100" min="100" max="200000" value="${invoice_line.amount}"/>
                            <label for="amount"><fmt:message key="fine-amount"/></label>
                        </div>
                        <div class="12u align-center">
                            <ul class="actions">
                                <input type="submit" value="${close}"/>
                            </ul>
                        </div>
                    </div>
                </fmt:bundle>
            </form>
        </div>
    </jsp:attribute>
</t:generic>
