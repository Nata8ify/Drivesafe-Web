<%-- 
    Document   : feed
    Created on : Aug 25, 2017, 11:14:07 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="includes/bslibraries.jsp"/>
        <title>Feed</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <a class="navbar-brand" href="To?opt=main">WeeWorh System</a>
                <div class="collapse navbar-collapse" id="navigation-example-2">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="To?opt=main">Main</a>
                        </li>
                        <li>
                            <a href="Logout">Logout / Invalidate</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav><br/><br/><br/><br/>
        <!--Feed Content-->
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <!-- Example Notifications Card -->
                    <div class="card mb-3">
                        <div class="list-group list-group-flush small">
                            <div id='append-feed'></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <nav class="navbar navbar-default navbar-fixed-bottom" role="navigation-demo" id="demo-navbar-bottom">
            <div class="container">

            </div>
        </nav>
        <script><jsp:include page="monitor_js/feed.js"/></script>
    </body>
</html>
