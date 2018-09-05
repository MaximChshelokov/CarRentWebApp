<%-- 
    Document   : add_user
    Created on : Jul 16, 2018, 7:43:40 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="admin-user-add.caption" var="caption"/>
    <fmt:message key="application.form.submit" var="submit"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-user-add.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <div class="row">
                <t:validation-message/>
                <form method="post" action="<c:url value="action/save_user"/>">
                    <div class="row uniform">
                        <div class="field half first">
                            <label for="login"><fmt:message key="email"/></label>
                            <input type="text" name="login" placeholder="<fmt:message key="email"/>" value="${user_edit.login}"/>
                        </div>
                        <div class="field half">
                            <label for="role"><fmt:message key="role"/></label>
                            <div class="select-wrapper">
                                <select name="role" id="role">
                                    <option value="">- <fmt:message key="role"/> -</option>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.id}" ${role.id == user_edit.role.id ? 'selected' : ''}>
                                            <c:out value="${role.roleName}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="field half first">
                            <label for="password"><fmt:message key="password"/></label>
                            <input type="password" name="password" placeholder="<fmt:message key="password"/>" value="${user_edit.password}"/>
                        </div>
                        <div class="field half first">
                            <label for="repeat"><fmt:message key="repeat"/></label>
                            <input type="password" name="repeat" placeholder="<fmt:message key="repeat"/>"/>
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

