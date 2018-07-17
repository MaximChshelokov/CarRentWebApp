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
    <fmt:message key="application.table.edit" var="edit"/>
    <fmt:message key="application.table.delete" var="delete"/>
    <fmt:message key="application.table.confirm" var="confirm"/>
    <fmt:message key="application.misc.yes" var="yes"/>
    <fmt:message key="application.misc.no" var="no"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-order-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><fmt:message key="caption"/></h3>
            </header>
            <div class="row">
                <div class="table-wrapper">
                    <table class="alt">
                        <thead>
                        <th><fmt:message key="start"/></th>
                        <th><fmt:message key="end"/></th>
                        <th><fmt:message key="car"/></th>
                        <th><fmt:message key="user"/></th>
                        <th><fmt:message key="sum"/></th>
                        <th><fmt:message key="approved"/></th>
                        </thead>
                        <tbody>
                            <c:forEach items="${order_list}" var="order">
                                <tr>
                                    <td><fmt:formatDate value="${order.startDate}" dateStyle="SHORT"/></td>
                                    <td><fmt:formatDate value="${order.endDate}" dateStyle="SHORT"/></td>
                                    <td><c:out value="${order.car.model.make.name}"/> <c:out value="${order.car.model.name}"/> (<c:out value="${order.car.yearOfMake}"/>)</td>
                                    <td><c:out value="${order.user.login}"/></td>
                                    <td><c:out value="${order.car.price}"/></td>
                                    <td><c:out value="${order.approvedBy.login}" default="${no}"/></td>
                                    <td><a href="action/delete_order?id=${order.id}"  onclick="return confirm('${confirm}');" class="table button special"><c:out value="${delete}"/></a></td>
                                    <td><a href="action/edit_order?id=${order.id}" class="table button"><c:out value="${edit}"/></a></td>
                                </tr>                                   
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="align-center">
                    <a href="action/create_car" class="button special"><fmt:message key="add"/></a>
                </div>
            </div>

        </jsp:attribute>
    </t:generic>
</fmt:bundle>
