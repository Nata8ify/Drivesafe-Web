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
        <!--<meta name="viewport" content="initial-scale=1.0, user-scalable=no">-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <script src="https://code.jquery.com/jquery-3.2.0.min.js" 
                integrity="sha256-JAW99MJVpJBGcbzEuXk4Az05s/XyDdBomFqNlM3ic+I="
        crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>
    </head>
    <body>
        <h1>Welcome! ${pf.firstName} ${pf.lastName}</h1>
        <p>This is your profile : ${pf}</p>
        <div>
            <table id="acctable" style="width: 100%;"> 
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Accident Code</th>
                        <th>Accident Id</th> 
                    </tr>
                </thead>
                <tbody style="text-align: center">
                    
                </tbody>
            </table>
        </div>
        <p id="out"></p>
        <a href="To?opt=stat">Open Statistics</a><br/><a href="Logout">Logout / Invalidate</a><br/><hr/>

        <div id="map" style="background-color: #ddd;width: 100%;height: 400px;padding-right: 30px;">

        </div>
        <br/><hr/>
        <script>
            <%@include file="monitor_js/map_navigate.js"  %>
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>
        <script>
            <%@include file="monitor_js/accident_updater.js"  %>
        </script>
    </body>
</html>
