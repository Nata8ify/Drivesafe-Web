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
    </head>
    <body>
        <h1>Welcome! ${pf.firstName} ${pf.lastName}</h1>
        <p>This is your profile : ${pf}</p>
        <p id="out"></p>
        <a href="To?opt=stat">Open Statistics</a><br/><a href="Logout">Logout / Invalidate</a><br/><hr/>
        <div id="map" style="background-color: #ddd;width: 100%;height: 600px;padding-right: 30px;">

        </div>
        <br/><hr/>
        <script>
            var map;


            var WARANON_LATLNG = {lat: 13.647094, lng: 100.487458};
            var SUANTHON_LATLNG = {lat: 13.652293, lng: 100.491173};
            var SUKSAWADROAD_LATLNG = {lat: 13.652797, lng: 100.521451};
            var NEARSIT_LATLNG = {lat: 13.652277, lng: 100.494457};
            function initMap() {
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 14,
                    center: WARANON_LATLNG
                });
                displayDestination(map);
            }
            var directionsDisplay;
            var directionsService;
            function displayDestination(map) {
                directionsDisplay = new google.maps.DirectionsRenderer;
                directionsService = new google.maps.DirectionsService;
                directionsDisplay.setMap(map);
                calculateAndDisplayRoute(directionsService, directionsDisplay);
            }

            function calculateAndDisplayRoute(directionsService, directionsDisplay) {
//                var selectedMode = document.getElementById('mode').value;
                directionsService.route({
                    origin: NEARSIT_LATLNG,
                    destination: SUKSAWADROAD_LATLNG,
                    travelMode: 'DRIVING'
                }, function (response, status) {
                    if (status == 'OK') {
                        directionsDisplay.setDirections(response);
                    } else {
                        window.alert('Directions request failed due to ' + status);
                    }
                });
            }
            var centerPoint;
            var accidentPoint;
            var latitude;
            var longtitude;
            $('document').ready(function () {
                setInterval(function () {
                    $.getJSON("RescuerIn?opt=getaccs").done(function (json) {
                        console.log(json);
                        var somewhere;
                        $.each(json, function (index, data) {
                            latitude = data.latitude;
                            longtitude = data.longtitude;
                            somewhere = {lat: data.latitude, lng: data.longtitude};
                            $('#out').html(data[index]);
                        });
//                        ------------------------
                        directionsService.route({
                            origin: NEARSIT_LATLNG,
                            destination: somewhere,
                            travelMode: 'DRIVING'
                        }, function (response, status) {
                            if (status == 'OK') {
                                directionsDisplay.setDirections(response);
                            } else {
                                window.alert('Directions request failed due to ' + status);
                            }
                        });
//                        ------------------------
                    });
                }
                , 5000);
            });
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>
    </body>
</html>
