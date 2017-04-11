<%-- 
    Document   : stat
    Created on : Mar 11, 2017, 12:45:22 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
        <script src="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.js"></script>


        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

        <link href="css/bootstrap.css" rel="stylesheet" />
        <link href="css/ct-paper.css" rel="stylesheet"/>
        <link href="css/demo.css" rel="stylesheet" /> 
        <!-- jQuery library -->
        <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
        <!--moment.js-->
        <script src="https://momentjs.com/downloads/moment.js"></script>
        <title>Statistic Page</title>
    </head>
    <body>

        <nav class="navbar navbar-default navbar-fixed-top" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <a class="navbar-brand">Drivesafe System</a>
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
        </nav>
        <div class="container">
            <!--<h3><a href="#acc-stat-chart">Number of Accident Statistic</a> <a href="#acc-stat-map">Visualizing Accident Location</a></h3><br/>-->
            <br/><br/><br/><br/>
            <div class="well">
                <legend>Please Specify a Period <b id="acc-period-title"></b></legend>
                <form action="#">
                    <div class="row">
                        <div class="alert alert-danger" id="alrt-invalid-period" hidden="">
                            <strong>Invalid Date Period! : </strong><span id="alrt-ip-msg"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3"><b>Begin  from : </b><input type="date" name="bDate" id="input-b-date" class="form-control"/></div>
                        <div class="col-sm-3"><b>To </b><input type="date" name="bDate" id="input-e-date" class="form-control"/></div>
                        <div class="col-sm-6">
                            <nav class="navbar navbar-collapse navbar-default" style="z-index: 1">
                                <div class="container-fluid">
                                    <ul class="nav navbar-nav ">
                                        <li class="active"><a href="#" ><input type="checkbox" id="acc-opt-normalacc" checked=""/> Normal</a></li>
                                        <li><a href="#"><input type="checkbox" id="acc-opt-false"/> False</a></li>
                                        <li><a href="#" ><input type="checkbox" id="acc-opt-sysfalse"/> System False</a></li>
                                        <li><a href="#" ><input type="checkbox" id="acc-opt-usrfalse"/> User False</a></li>
                                    </ul>
                                </div>
                            </nav>
                        </div>
                    </div>

                </form>
                </fieldset>
                <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
                <hr/>
                <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>
            </div>

            <script>
                <jsp:include page="monitor_js/accident_stat.js"/>
            </script>
            <script async defer
                    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
            </script>

    </body>
</html>
