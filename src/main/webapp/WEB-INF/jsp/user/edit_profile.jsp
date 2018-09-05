<%-- 
    Document   : edit_profile
    Created on : Jul 20, 2018, 10:57:28 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.form.submit" var="submit"/>
    <fmt:message key="user-edit-profile.caption" var="caption"/>
    <fmt:message key="login.password" var="password"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-user-list.">
<t:generic>
    <jsp:attribute name="content">
        <header class="align-center">
            <h3><c:out value="${caption}"/></h3>
        </header>
        <t:validation-message/>
        <div class="12u">
            <form method="post" action="<c:url value="action/update_profile"/>">
                <div class="row uniform">
                    <div class="field half first">
                        <label for="login"><fmt:message key="login"/></label>
                        <input type="text" name="login" placeholder="<fmt:message key="login"/>" value="${user_data.user.login}" disabled/>
                    </div>
                    <div class="field half">
                        <label for="password"><c:out value="${password}"/></label>
                        <input type="password" name="password" placeholder="${password}" value="password"/>
                    </div>
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