<%-- 
    Document   : setting
    Created on : Apr 14, 2017, 12:52:06 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.2.0.min.js" integrity="sha256-JAW99MJVpJBGcbzEuXk4Az05s/XyDdBomFqNlM3ic+I=" crossorigin="anonymous"></script>
        <title>Setting</title>
    </head>
    <body>
        <h1>Setting Page</h1>
        <div id="spec-oper-location-section">
            <div id="spec-oper-location-map" style="width: 100%; height: 600px; padding: 30px;"></div>
            <input type="text" id="spec-oper-location-input" placeholder="Enter the plcae"/>
            <input type="text" id="spec-oper-location-submit" type="button"/>
        </div>
        <script>
            <jsp:include page="monitor_js/setting.js" flush="true"/>
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>
    </body>
</html>
