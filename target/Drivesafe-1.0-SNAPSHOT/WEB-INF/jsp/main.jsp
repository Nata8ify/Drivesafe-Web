<%-- 
    Document   : main
    Created on : Mar 10, 2017, 12:21:22 PM
    Author     : PNattawut
--%>

<%@page import="com.senior.g40.model.Profile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Profile pf = (Profile) request.getSession(true).getAttribute("pf");
    if (pf != null) {
        pageContext.setAttribute("pf", pf);
    } else {
        response.sendRedirect("To?opt=index");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
    <body>
        <h1>Welcome! ${pf.firstName} ${pf.lastName}</h1>
        <p>This is your profile : ${pf}</p>
        <a href="To?opt=stat">Open Statistics</a><br/><a href="Logout">Logout / Invalidate</a><br/><hr/>
        <div id="mornitorMap" style="background-color: #ddd;width: 100%;height: 600px;padding-right: 30px;">

        </div>
        <br/><hr/>
        <script>
            var map;
            var waranon = {lat: 13.647094, lng: 100.487458};
            function initMap() {
                map = new google.maps.Map(document.getElementById('mornitorMap'), {
                    center: waranon,
                    zoom: 20});
                var marker = new google.maps.Marker({
                    position: waranon,
                    map: map
                });
            }

        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>
    </body>
</html>
