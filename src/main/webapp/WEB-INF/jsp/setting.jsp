<%-- 
    Document   : setting
    Created on : Apr 14, 2017, 12:52:06 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="includes/bslibraries.jsp"/>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Setting</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <a class="navbar-brand">Drivesafe Setting</a>
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
        <br/><br/><br/><br/><br/><br/>
    <center>
        <div id="spec-location-section">
            <div class="container">

                <div class="row">
                    <div class="col-sm-12"><i id="callback-msg"></i></div>
                </div>
                <div class="row well">
                    <div class="col-sm-8">
                        <div id="spec-location-map" style="width: 100%; height: 600px; padding: 30px;" ></div>
                    </div>
                    <div class="col-sm-4">
                        <fieldset>
                            <input placeholder="Enter Somewhere (ex. สถานีรถไฟหัวลำโพง)" id="spec-location-input" placeholder="Enter the place" class="form-control"/>
                            <hr/>
                            <input type="number" id="spec-location-lat-input" placeholder="Enter the Latitude" required="" value="${param.lat}" class="form-control"/><br/>
                            <input type="number" id="spec-location-lng-input" placeholder="Enter the Longitude" required="" value="${param.lng}" class="form-control"/><br/>
                            <input type="number" id="spec-location-boundrds-input" placeholder="Enter the Bound Radius" required="" value="${param.bound}" class="form-control"/><br/>
                            <div class="row">
                                <!--<div class="col-sm-3"><input type="button" id="spec-location-submit" value="Submit" class="btn btn-success" style="width: 100%" /><br/><br/></div>-->
                                <div class="col-sm-6"><input type="button" id="update-location-submit" value="Update" class="btn btn-primary" style="width: 100%"/><br/><br/></div>
                                <div class="col-sm-6"><input type="button" id="getcur-location-submit" value="Get Current Location" class="btn btn-default" style="width: 100%"/><br/><br/></div>
                            </div>
                            <hr/>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </center>

    <script>
        <jsp:include page="monitor_js/setting.js" flush="true"/>
    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&libraries=places&callback=initMap">
    </script>
</body>
</html>
