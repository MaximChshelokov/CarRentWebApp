<%-- 
    Document   : edit_car
    Created on : Jul 12, 2018, 2:41:48 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <c:choose>
        <c:when test="${car.id==0}">
            <fmt:message key="admin-car-create.caption" var="caption"/>
        </c:when>
        <c:otherwise>
            <fmt:message key="admin-car-edit.caption" var="caption"/>
        </c:otherwise>
    </c:choose>
    
    <fmt:message key="application.form.submit" var="submit"/>
    <c:choose>
        <c:when test="${errParam == 1}">
            <fmt:message key="admin-car-edit.error-year" var="error_msg"/>
        </c:when>
        <c:when test="${errParam == 2}">
            <fmt:message key="admin-car-edit.error-empty" var="error_msg"/>
        </c:when>
        <c:when test="${errParam == 3}">
            <fmt:message key="admin-car-edit.error-price" var="error_msg"/>
        </c:when>
    </c:choose>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-car-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <div class="row">
                <div class="12u">
                    <c:if test="${errParam!=0}">
                        <p style="color:#ff0000"><c:out value="${error_msg}"/>
                        </c:if>
                </div>
                <form method="post" action="action/update_car">
                    <div class="row uniform">
                        <div class="field half first">
                            <label for="make"><fmt:message key="make"/></label>
                            <input type="text" name="make" placeholder="<fmt:message key="make"/>" value="${car.model.make.name}"/>
                        </div>
                        <div class="field half">
                            <label for="model"><fmt:message key="model"/></label>
                            <input type="text" name="model" placeholder="<fmt:message key="model"/>" value="${car.model.name}"/>
                        </div>
                        <div class="field half first">
                            <label for="year"><fmt:message key="year"/></label>
                            <input type="text" name="year" placeholder="<fmt:message key="year"/>" value="${car.yearOfMake==0?"":car.yearOfMake}"/>
                        </div>
                        <div class="field half">
                            <label for="plate"><fmt:message key="plate"/></label>
                            <input type="text" name="plate" placeholder="<fmt:message key="plate"/>" value="${car.licensePlate}"/>
                        </div>
                        <div class="field half first">
                            <label for="price"><fmt:message key="price"/></label>
                            <input type="text" name="price" placeholder="<fmt:message key="price"/>" value="${car.price==0?"":car.price}"/>
                        </div>
                        <div class="12u">
                            <ul class="actions">
                                <input type="submit" value="${submit}"/>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>

        </jsp:attribute>
    </t:generic>
</fmt:bundle>
