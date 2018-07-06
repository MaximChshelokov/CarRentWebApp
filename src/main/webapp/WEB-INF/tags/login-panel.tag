<%-- 
    Document   : login_panel
    Created on : Jul 3, 2018, 6:20:27 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@tag description="Login panel" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n" prefix="login.">
    <h3><fmt:message key="login"/></h3>
    <c:if test="${errorLogin==1}">
        <p style="color:#ff0000"><fmt:message key="err_login"/></p>
    </c:if>
    <form method="post" action="action/login">
        <div class="row uniform">
            <div class="12u">
                <input type="text" name="email" placeholder="<fmt:message key="email"/>" />
            </div>
            <div class="12u">
                <input type="password" name="pass" placeholder="<fmt:message key="password"/>" />
            </div>
            <div class="12u">
                <ul class="actions">
                    <input type="submit" value="<fmt:message key="login"/>" />
                </ul>
            </div>
            <div class="12u">
                <input type="checkbox" id="remember" name="remember">
                <label for="remember">
                    <fmt:message key="remember"/>
                </label>
            </div>
            <div class="12u">
                <a href="action/signup" class="button special">Sign up</a>
            </div>
        </div>
    </form>
</fmt:bundle>