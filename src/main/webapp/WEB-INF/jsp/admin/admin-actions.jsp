<%-- 
    Document   : admin-actions
    Created on : Jul 5, 2018, 8:29:53 AM
    Author     : Maxim Chshelokov <schelokov.mv@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:generic>
    <jsp:attribute name="content">
        <div class="3u 12u medium">
            <a href="action/car_list" class="button" >Car list</a>
        </div>
        <div class="3u 12u medium">
            <a href="action/user_list" class="button" >User list</a>
        </div>
        <div class="3u 12u medium">
            <a href="action/overdue" class="button" >Overdue payments</a>
        </div>
        <div class="3u 12u medium">
            <a href="action/application" class="button" >Rent orders</a>
        </div>
    </jsp:attribute>
</t:generic>
