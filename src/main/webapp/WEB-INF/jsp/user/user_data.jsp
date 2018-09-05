<%-- 
    Document   : user_data
    Created on : Aug 9, 2018, 10:53:14 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.form.submit" var="submit"/>
    <fmt:message key="user-input-data.caption" var="caption"/>
    <fmt:message key="login.password" var="password"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-user-list.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <t:validation-message/>
            <form method="post" action="<c:url value="action/proceed_to_order"/>">
                <div class="row uniform">
                    <div class="12u">
                        <label for="address"><fmt:message key="address"/></label>
                        <input type="text" name="address" placeholder="<fmt:message key="address"/>" value="${user_data.address}"/>
                    </div>
                    <div class="field half first">
                        <label for="name"><fmt:message key="name"/></label>
                        <input type="text" name="name" placeholder="<fmt:message key="name"/>" value="${user_data.name}"/>
                    </div>
                    <div class="field half">
                        <label for="phone"><fmt:message key="phone"/></label>
                        <input type="text" name="phone" placeholder="<fmt:message key="phone"/>" value="${user_data.phone}"/>
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
