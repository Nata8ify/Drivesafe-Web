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
        <script src="https://code.jquery.com/jquery-3.2.0.min.js" integrity="sha256-JAW99MJVpJBGcbzEuXk4Az05s/XyDdBomFqNlM3ic+I=" crossorigin="anonymous"></script>
        <title>Setting</title>
    </head>
    <body>
        <h1>Setting Page</h1>
        <div id="spec-location-section">
            <div id="spec-location-map" style="width: 100%; height: 600px; padding: 30px;"></div>
            <fieldset>
                <input type="text" id="spec-location-lat-input" placeholder="Enter the Latitude" required="" value="13.372186"/>
                <input type="text" id="spec-location-lng-input" placeholder="Enter the Longitude" required="" value="100.977893"/>
                <input type="number" id="spec-location-boundrds-input" placeholder="Enter the Bound Radius" required="" value="50"/>
                <input type="hidden" id="spec-location-input" placeholder="Enter the plcae" disabled=""/>
                <input type="button" id="spec-location-submit" value="Submit This Location"/>
            </fieldset>
        </div>
        <script>
            <jsp:include page="monitor_js/setting.js" flush="true"/>
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>
    </body>
</html>
