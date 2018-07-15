<%-- 
    Document   : user_list
    Created on : Jul 9, 2018, 8:53:01 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="application.table.edit" var="edit"/>
    <fmt:message key="application.table.delete" var="delete"/>
    <fmt:message key="application.table.confirm" var="confirm"/>
</fmt:bundle>
<fmt:bundle basename="i18n" prefix="admin-user-list.">
<t:generic>
    <jsp:attribute name="content">
        <div class="row">
            <div class="table-wrapper">
                <table class="alt">
                    <thead>
                    <th><fmt:message key="login"/></th>
                    <th><fmt:message key="name"/></th>
                    <th><fmt:message key="address"/></th>
                    <th><fmt:message key="phone"/></th>
                    <th><fmt:message key="role"/></th>
                    </thead>
                    <tbody>
                        <c:forEach items="${user_data}" var="data">
                            <tr>
                                <td><c:out value="${data.user.login}"/></td>
                                <td><c:out value="${data.name}"/></td>
                                <td><c:out value="${data.address}"/></td>
                                <td><c:out value="${data.phone}"/></td>
                                <td><c:out value="${data.user.role.roleName}"/></td>
                                <td><a href="action/delete_user?id=${data.user.id}&uid=${data.id}" onclick="return confirm('${confirm}');" class="table button special"><c:out value="${delete}"/></a></td>
                                <td><a href="action/edit_user?id=${data.user.id}" class="table button"><c:out value="${edit}"/></a></td>
                            </tr>                                   
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="align-center">
                <a href="#" class="button special"><fmt:message key="add"/></a>
            </div>
        </div>
        
    </jsp:attribute>
</t:generic>
</fmt:bundle>

