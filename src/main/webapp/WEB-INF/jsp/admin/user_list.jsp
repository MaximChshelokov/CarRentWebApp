<%-- 
    Document   : user_list
    Created on : Jul 9, 2018, 8:53:01 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:generic>
    <jsp:attribute name="content">
        <div class="row">
            <div class="table-wrapper">
                <table class="alt">
                    <thead>
                        <th>Login</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th></th>
                    </thead>
                    <tbody>
                        <c:forEach items="${user_data}" var="data">
                            <tr>
                                <td><c:out value="${data.user.login}"/></td>
                                <td><c:out value="${data.name}"/></td>
                                <td><c:out value="${data.address}"/></td>
                                <td><c:out value="${data.phone}"/></td>
                                <td><c:out value="${data.user.role.roleName}"/></td>
                                <td><a href="action/delete_user?id=${data.user.id}&uid=${data.id}" onclick="return confirm('Are you sure you want to delete this item?');" class="button special small">Delete</a></td>
                                <td><a href="action/edit_user?id=${data.user.id}" class="button small">Edit</a></td>
                            </tr>                                   
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="align-center">
                <a href="#" class="button special">Add new user</a>
            </div>
        </div>
        
    </jsp:attribute>
</t:generic>

