<%-- 
    Document   : stat
    Created on : Mar 11, 2017, 12:45:22 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="includes/bslibraries.jsp"/>
        <!--moment.js-->
        <script src="https://momentjs.com/downloads/moment.min.js"></script>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
        <script src="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.js"></script>
        <title>Statistic Page</title>
    </head>
    <body>

        <nav class="navbar navbar-default navbar-fixed-top" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <a class="navbar-brand">WeeWorh System</a>
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
            <div class="well" >
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
                            <!--                            <nav class="navbar navbar-collapse navbar-default" style="z-index: 1">
                                                            <div class="container-fluid">
                                                                <ul class="nav navbar-nav ">
                                                                    <li class="active"><a href="#" ><input type="checkbox" id="acc-opt-normalacc" checked=""/> Normal</a></li>
                                                                    <li><a href="#"><input type="checkbox" id="acc-opt-false"/> False</a></li>
                                                                    <li><a href="#" ><input type="checkbox" id="acc-opt-sysfalse"/> System False</a></li>
                                                                    <li><a href="#" ><input type="checkbox" id="acc-opt-usrfalse"/> User False</a></li>
                                                                </ul>
                                                            </div>
                                                        </nav>-->
                        </div>
                    </div>

                </form>
                </fieldset>
                <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
                <hr/>
                <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>
                <br/>
                <div id="crashspeed-stat-section">
                     <legend>Statistic of Crash Speed</legend>
                     <div id="crashspeed-stat-chart" class="ct-chart ct-major-twelfth"></div>
                </div>
            </div>
            <script>
                <jsp:include page="monitor_js/accident_stat.js"/>
            </script>
            <script async defer
                    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
            </script>

    </body>
</html>