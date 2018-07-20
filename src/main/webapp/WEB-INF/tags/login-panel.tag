<%-- 
    Document   : login_panel
    Created on : Jul 3, 2018, 6:20:27 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@tag description="Login panel" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <c:choose>
        <c:when test="${errorSignup==1}">
            <fmt:message key="editor.error-empty" var="sign_error_msg"/>
        </c:when>
        <c:when test="${errorSignup==2}">
            <fmt:message key="admin-user-add.error" var="sign_error_msg"/>
        </c:when>
        <c:when test="${errorSignup==3}">
            <fmt:message key="admin-user-add.error-login" var="sign_error_msg"/>
        </c:when>
        <c:when test="${errorSignup==4}">
            <fmt:message key="editor.error-email" var="sign_error_msg"/>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${errorLogin==1}">
            <fmt:message key="editor.error-empty" var="logn_error_msg"/>
        </c:when>
        <c:when test="${errorLogin==2}">
            <fmt:message key="login.err-login" var="logn_error_msg"/>
        </c:when>
    </c:choose>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="login.">
    <div id="login" style="${sign?'display:none':''}">
        <h3><fmt:message key="login"/></h3>
        <c:if test="${errorLogin!=0}">
            <p style="color:#ff0000"><c:out value="${logn_error_msg}"/></p>
        </c:if>
        <form method="post" action="action/login">
            <div class="row uniform">
                <div class="12u">
                    <input type="text" name="email" placeholder="<fmt:message key="email"/>" value="${email}"/>
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
                    <a href="#" class="button special" onclick="$('#login').fadeOut(300, function(){
    $('#signup').fadeIn(300);})"><fmt:message key="signup"/></a>
                </div>
            </div>
        </form>
    </div>
    <div id="signup" style="${sign?'':'display:none'}">
        <h3><fmt:message key="signup"/></h3>
        <c:if test="${errorSignup!=0}">
            <p style="color:#ff0000"><c:out value="${sign_error_msg}"/></p>
        </c:if>
        <form method="post" action="action/sign_up">
            <div class="row uniform">
                <div class="12u">
                    <input type="text" name="email" placeholder="<fmt:message key="email"/>" value="${email}"/>
                </div>
                <div class="12u">
                    <input type="password" name="pass" placeholder="<fmt:message key="password"/>" />
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
                    <a href="#" class="button special" onclick=" $('#signup').fadeOut(300, function(){
    $('#login').fadeIn(300);});"><fmt:message key="login"/></a>
                </div>
            </div>
        </form>
    </div>                     
</div>
</fmt:bundle>