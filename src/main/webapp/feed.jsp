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

        <title>Feed</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
            <a class="navbar-brand" href="To?opt=main">Weeworh</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav navbar-sidenav">
                    <li class="nav-item active" data-toggle="tooltip" data-placement="right" title="Dashboard">
                        <a class="nav-link" href="To?opt=main">
                            <i class="fa fa-fw fa-dashboard"></i>
                            <span class="nav-link-text">
                                Dashboard</span>
                        </a>
                    </li> 
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
                        <a class="nav-link" href="To?opt=dayReport">
                            <i class="fa fa-fw fa-table"></i>
                            <span class="nav-link-text">
                                Day Report</span>
                        </a>
                    </li>
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
                        <a class="nav-link" href="To?opt=stat">
                            <i class="fa fa-fw fa-area-chart"></i>
                            <span class="nav-link-text">
                                Charts</span>
                        </a>
                    </li>
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
                        <a class="nav-link" href="To?opt=sett">
                            <i class="fa fa-fw fa-cog"></i>
                            <span class="nav-link-text">
                                Setting</span>
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">

                    <li class="nav-item">
                        <a class="nav-link" href="Logout">
                            <i class="fa fa-fw fa-sign-out"></i>
                            Logout</a>
                    </li>
                </ul>
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

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/popper/popper.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
        <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
        <script><jsp:include page="dashboard_js/feed.js"/></script>
    </body>
</html>
