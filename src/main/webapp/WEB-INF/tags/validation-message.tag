<%-- 
    Document   : validation_message
    Created on : Sep 5, 2018, 7:03:20 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@tag description="Output validation error message" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <div class="12u">
        <c:if test="${not empty error_message}">
            <p style="color:#ff0000"><fmt:message key="${error_message}"/>
            </c:if>
    </div>
</fmt:bundle>