/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
//    calculateAndDisplayRoute(directionsService, directionsDisplay); // Dummy Destination
}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
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
var accidents = [];
var centerPoint;
var accidentPoint;
var latitude;
var longtitude;
var somewhere;

function navigate() {
    $.getJSON("RescuerIn?opt=getaccs").done(function (json) {
        console.log(json);
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
