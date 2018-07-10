<%-- 
    Document   : edit_user
    Created on : Jul 9, 2018, 2:21:41 PM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:generic>
    <jsp:attribute name="content">
        <div class="row">
            <form method="post" action="action/update_user">
                <div class="row uniform">
                    <div class="field half first">
                        <label for="login">Login</label>
                        <input type="text" name="login" placeholder="email" value="${user_data.user.login}"/>
                    </div>
                    <div class="field half">
                        <label for="name">Name</label>
                        <input type="text" name="name" placeholder="name" value="${user_data.name}"/>
                    </div>
                    <div class="12u">
                        <label for="address">Address</label>
                        <input type="text" name="address" placeholder="address" value="${user_data.address}"/>
                    </div>
                    <div class="field half first">
                        <label for="phone">Phone</label>
                        <input type="text" name="phone" placeholder="phone" value="${user_data.phone}"/>
                    </div>
                    <div class="field half">
                        <label for="role">Role</label>
                        <input type="text" name="role" placeholder="role" value="${user_data.user.role.roleName}" disabled="true"/>
                    </div>
                    <div class="12u">
                        <ul class="actions">
                            <input type="submit" value="Submit"/>
                        </ul>
                    </div>
                </div>
            </form>
            <div class="12u">
                <c:if test="${errEmptyParam==1}">
                    <p style="color:#ff0000">Fill out empty fields!</p>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</t:generic>
