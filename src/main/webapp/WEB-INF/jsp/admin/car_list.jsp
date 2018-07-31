<%-- 
    Document   : car_list
    Created on : Jul 12, 2018, 10:28:30 AM
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
<fmt:bundle basename="i18n" prefix="admin-car-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><fmt:message key="caption"/></h3>
            </header>
            <div class="12u">
                <c:choose>
                    <c:when test="${empty car_list}">
                        <p><fmt:message key="no-cars"/></p>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrapper">
                            <table class="alt">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="make"/></th>
                                        <th><fmt:message key="model"/></th>
                                        <th><fmt:message key="year"/></th>
                                        <th><fmt:message key="plate"/></th>
                                        <th><fmt:message key="price"/></th>
                                        <th><fmt:message key="available"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${car_list}" var="car">
                                        <tr>
                                            <td><c:out value="${car.carModel.carMake.name}"/></td>
                                            <td><c:out value="${car.carModel.name}"/></td>
                                            <td><c:out value="${car.yearOfMake}"/></td>
                                            <td><c:out value="${car.licensePlate}"/></td>
                                            <td><c:out value="${car.price}"/></td>
                                            <td><c:out value="${car.available ? yes : no}"/></td>
                                            <td><a href="<c:url value="action/delete_car">
                                                       <c:param name="id" value="${car.id}"/>
                                                   </c:url>"  onclick="return confirm('${confirm}');" class="table button special"><c:out value="${delete}"/></a></td>
                                            <td><a href="<c:url value="action/edit_car">
                                                       <c:param name="id" value="${car.id}"/>
                                                   </c:url>" class="table button"><c:out value="${edit}"/></a></td>
                                        </tr>                                   
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="align-center">
                    <a href="<c:url value="action/create_car"/>" class="button special"><fmt:message key="add"/></a>
                </div>
            </div>

        </jsp:attribute>
    </t:generic>
</fmt:bundle>
