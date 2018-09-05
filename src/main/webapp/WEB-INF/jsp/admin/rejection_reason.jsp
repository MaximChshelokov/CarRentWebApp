<%-- 
    Document   : rejection_reason
    Created on : Aug 24, 2018, 11:36:06 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.form.submit" var="submit"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-reject-reason.">
    <t:generic>
        <jsp:attribute name="content">
            <header class="align-center">
                <h3><c:out value="${caption}"/></h3>
            </header>
            <div class="row">
                <t:validation-message/>
                <form method="post" action="<c:url value="action/update_reason">
                      <c:param name="id" value="${id}"/>
                    </c:url>">
                    <div class="row uniform">
                        <div class="12u">
                            <textarea name="rejection-reason" id="rejection-reason" placeholder="<fmt:message key="rejection-reason"/>" rows="3" value="${rejection_reason}"></textarea>
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
