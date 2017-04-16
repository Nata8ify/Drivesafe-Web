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
        <title>Setting</title>
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
        <br/><br/><br/><br/><br/>
    <center>
        <div id="spec-location-section">
            <div id="spec-location-map" style="width: 90%; height: 600px; padding: 30px;" hidden=""></div>
            <i id="callback-msg"></i>
            <br/>
            <fieldset style="padding: 1%">
                <input type="text" id="spec-location-lat-input" placeholder="Enter the Latitude" required="" value="13.372186"/>
                <input type="text" id="spec-location-lng-input" placeholder="Enter the Longitude" required="" value="100.977893"/>
                <input type="number" id="spec-location-boundrds-input" placeholder="Enter the Bound Radius" required="" value="50"/>
                <input type="hidden" id="spec-location-input" placeholder="Enter the plcae" disabled=""/>
                <input type="button" id="spec-location-submit" value="Submit This Location"/>
                <input type="button" id="update-location-submit" value="Update This Location"/>
                <input type="button" id="getcur-location-submit" value="Get Current Location"/>
            </fieldset>
        </div>
    </center>
    <script>
        <jsp:include page="monitor_js/setting.js" flush="true"/>
    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
    </script>
</body>
</html>
