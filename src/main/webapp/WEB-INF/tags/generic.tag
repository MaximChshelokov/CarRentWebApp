<%@tag description="Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="content" fragment="true" %>

<fmt:bundle basename="i18n" prefix="application.">
    <fmt:message key="title" var="title"/>
</fmt:bundle>


<!DOCTYPE HTML>
<html>
    <head>
        <title>${title}</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <c:set var="url">${pageContext.request.requestURL}</c:set>
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
        <link rel="stylesheet" href="css/main.css" />
    </head>
    <body class="subpage">
        <!-- Header -->
        <header id="header">
            <div class="inner">
                <a href="action/home" class="logo"><strong>${title}</strong></a>
                <nav id="nav">
                    <a href="<c:url value = "action/change_locale">
                           <c:param name="locale" value="ru"/>
                           <c:param name="url" value="${URL}"/>
                        </c:url>">Русский</a>
                    <a href="<c:url value = "action/change_locale">
                           <c:param name="locale" value="en"/>
                           <c:param name="url" value="${URL}"/>
                        </c:url>">English</a>
                </nav>
                <a href="#navPanel" class="navPanelToggle"><span class="fa fa-bars"></span></a>
            </div>
        </header>

        <!-- Three -->

        <section id="three" class="wrapper">
            <div class="inner">
                <div class="row 200%">
                    <section class="9u 12u(medium)">
                        <jsp:invoke fragment="content"/>
                    </section>

                    <!-- Sidebar -->

                    <section class="3u 12u(medium)">                                                    
                        <c:choose>
                            <c:when test="${empty user}">
                                <t:login-panel/>
                            </c:when>
                            <c:otherwise>
                                <t:user-panel/>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer id="footer">
            <div class="inner">
                <div class="copyright">
                    Educational project by Maxim Chshelokov: <a href="https://github.com/MaximChshelokov/CarRentWebApp">GitHub</a>.<br/>
                    &copy; CSS Design free template: <a href="https://templated.co">TEMPLATED</a>.
                </div>

            </div>
        </footer>

        <!-- Scripts -->
        <script src="js/jquery.min.js"></script>
        <script src="js/skel.min.js"></script>
        <script src="js/util.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>
