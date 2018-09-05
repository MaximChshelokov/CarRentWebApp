<%-- 
    Document   : car_select
    Created on : Jul 20, 2018, 1:13:42 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.form.submit" var="submit"/>
    <fmt:message key="user-select-car.start-date" var="start"/>
    <fmt:message key="user-select-car.end-date" var="end"/>
    <fmt:message key="user-select-car.caption" var="caption"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-car-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <t:validation-message/>
            <form method="post" action="<c:url value="action/create_order"/>">
                <div class="12u">
                    <div class="table-wrapper">
                        <table class="alt">
                            <thead>
                                <tr>
                                    <th><fmt:message key="make"/></th>
                                    <th><fmt:message key="model"/></th>
                                    <th><fmt:message key="year"/></th>
                                    <th><fmt:message key="plate"/></th>
                                    <th><fmt:message key="price"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${car_list}" var="car" varStatus="s">
                                    <tr>
                                        <td><c:out value="${car.carModel.carMake.name}"/></td>
                                        <td><c:out value="${car.carModel.name}"/></td>
                                        <td><c:out value="${car.yearOfMake}"/></td>
                                        <td><c:out value="${car.licensePlate}"/></td>
                                        <td><c:out value="${car.price}"/></td>
                                        <td><input type="radio" name="selected_car" value="${car.id}" id="check${s.index}" ${car.id==order.car.id?'checked':''}/>
                                            <label for="check${s.index}"/></td>
                                    </tr>                                   
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="6u 12u">
                        <div class="field half first">
                            <label for="start_date"><c:out value="${start}"/></label>
                            <input type="date" name="start_date" id="start_date" value="${start_date}">
                        </div>
                        <div class="field half">
                            <label for="end_date"><c:out value="${end}"/></label>
                            <input type="date" name="end_date" id="end_date" value="${end_date}">
                        </div>
                    </div>
                    <div class="align-center">
                        <input type="submit" value="${submit}"/>
                    </div>
                </div>
            </form>
        </jsp:attribute>
    </t:generic>
</fmt:bundle>

