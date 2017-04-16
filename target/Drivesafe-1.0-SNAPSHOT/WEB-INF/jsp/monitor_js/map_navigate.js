/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var opMap;
var WARANON_LATLNG = {lat: 13.647094, lng: 100.487458};
var SUANTHON_LATLNG = {lat: 13.652293, lng: 100.491173};
var SUKSAWADROAD_LATLNG = {lat: 13.652797, lng: 100.521451};
var NEARSIT_LATLNG = {lat: 13.652277, lng: 100.494457};
function initMap() {
    getOpLatLng(opMap);
//    map = new google.maps.Map(document.getElementById('map'), {
//        zoom: 14,
//        center: WARANON_LATLNG
//    });
    displayDestination(opMap);
}
var directionsDisplay;
var directionsService;
function displayDestination(opMap) {
    directionsDisplay = new google.maps.DirectionsRenderer;
    directionsService = new google.maps.DirectionsService;
    directionsDisplay.setMap(opMap);
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
    });
}

/* Event Listener */
$('document').ready(function () {
     
});


/* Function */
function buildOPMap() {

}

var OPT_GET_OL = "getOpLocation";
function getOpLatLng(opMap) {
    $.when($.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL})).done(function (aresult) {
        var opLocationRawJSON = $.parseJSON(aresult);
        var opLatLng = {lat: opLocationRawJSON['latLng']['latitude'], lng: opLocationRawJSON['latLng']['longitude']};
        var bound = opLocationRawJSON['bound'] * 1.609344;
        log("> LatLng : " + opLatLng['lat'] + " | " + opLatLng['lng']);
        log("> Bound : " + bound);
        opMap = new google.maps.Map($('#map')[0], {
            zoom: 14,
            center: opLatLng
        });
        var opMarker = new google.maps.Marker({
            position: opLatLng,
            map: opMap
        });
        var opCircle =  new google.maps.Circle({
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35,
            map: opMap,
            center: opLatLng,
            redius: bound
        });
    });
}



/* Other */
function log(str) {
    console.log(str);
}

function callbackMessage(str) {
    $('#callback-msg').html(str);
}