<%-- 
    Document   : login_panel
    Created on : Jul 3, 2018, 6:20:27 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@tag description="Login panel" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <c:if test="${not empty error_message}">
        <fmt:message key="${error_message}" var="error_msg"/>
    </c:if>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="login.">
    <div id="login" style="${sign?'display:none':''}">
        <h3><fmt:message key="login"/></h3>
        <c:if test="${not empty error_msg and not sign}">
            <p style="color:#ff0000"><c:out value="${error_msg}"/></p>
        </c:if>
            <form method="post" action="<c:url value="action/login"/>">
            <div class="row uniform">
                <div class="12u">
                    <input type="text" name="login" placeholder="<fmt:message key="email"/>" value="${user_edit.login}"/>
                </div>
                <div class="12u">
                    <input type="password" name="password" placeholder="<fmt:message key="password"/>" />
                </div>
                <div class="12u">
                    <ul class="actions">
                        <input type="submit" value="<fmt:message key="login"/>" />
                    </ul>
                </div>
                <div class="12u">
                    <a href="${URL}#" class="button special" onclick="$('#login').fadeOut(300, function(){
    $('#signup').fadeIn(300);})"><fmt:message key="signup"/></a>
                </div>
            </div>
        </form>
    </div>
    <div id="signup" style="${sign?'':'display:none'}">
        <h3><fmt:message key="signup"/></h3>
        <c:if test="${not empty error_msg and sign}">
            <p style="color:#ff0000"><c:out value="${error_msg}"/></p>
        </c:if>
            <form method="post" action="<c:url value="action/sign_up"/>">
            <div class="row uniform">
                <div class="12u">
                    <input type="text" name="login" placeholder="<fmt:message key="email"/>" value="${user_edit.login}"/>
                </div>
                <div class="12u">
                    <input type="password" name="password" placeholder="<fmt:message key="password"/>" />
                </div>
                <div class="12u">
                    <input type="password" name="repeat" placeholder="<fmt:message key="repeat"/>" />
                </div>
                <div class="12u">
                    <ul class="actions">
                        <input type="submit" value="<fmt:message key="signup"/>" />
                    </ul>
                </div>
                <div class="12u">
                    <a href="${URL}#" class="button special" onclick=" $('#signup').fadeOut(300, function(){
    $('#login').fadeIn(300);});"><fmt:message key="login"/></a>
                </div>
            </div>
        </form>
    </div>                     
</div>
</fmt:bundle>