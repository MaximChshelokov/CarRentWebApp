<%@tag description="Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="content" fragment="true" %>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Rent A Car</title>
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
                <a href="${root}" class="logo"><strong>Rent A Car</strong></a>
                <nav id="nav">
                    <a href="index.html">Sign in</a>
                    <a href="generic.html">Login</a>
                    <a href="elements.html">Elements</a>
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
                        <c:if test="${errorLogin==1}">
                            <p>No such user!</p>
                        </c:if>
                        <c:if test="${empty user}">
                            <h3>Login</h3>
                            <form method="post" action="action/login">
                                <div class="row uniform">
                                    <div class="12u">
                                        <!--<label for="email">Email ID:</label>-->
                                        <input type="text" name="email" placeholder="Email" />
                                    </div>
                                    <div class="12u">
                                        <!--<label for="email">Password:</label>-->
                                        <input type="password" name="pass" placeholder="Password" />
                                    </div>
                                    <div class="12u">
                                        <ul class="actions">
                                            <input type="submit" value="login" />
                                        </ul>
                                    </div>
                                    <div class="12u">
                                        <input type="checkbox" id="remember" name="remember">
                                        <label for="remember">
                                            Remember me
                                        </label>
                                    </div>
                                </div>
                            </form>
                        </c:if>
                    </section>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer id="footer">
            <div class="inner">

                <h3>Get in touch</h3>

                <form action="#" method="post">

                    <div class="field half first">
                        <label for="name">Name</label>
                        <input name="name" id="name" type="text" placeholder="Name">
                    </div>
                    <div class="field half">
                        <label for="email">Email</label>
                        <input name="email" id="email" type="email" placeholder="Email">
                    </div>
                    <div class="field">
                        <label for="message">Message</label>
                        <textarea name="message" id="message" rows="6" placeholder="Message"></textarea>
                    </div>
                    <ul class="actions">
                        <li><input value="Send Message" class="button alt" type="submit"></li>
                    </ul>
                </form>

                <div class="copyright">
                    &copy; Untitled. Design: <a href="https://templated.co">TEMPLATED</a>. Images: <a href="https://unsplash.com">Unsplash</a>.
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
