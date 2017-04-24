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
var directionsDisplay;
var directionsService;
function initMap() {
    getOpLatLng(opMap);
}

var accidents = [];
var crashLatLng; // Crash LatLng
var opLatLng; // Operating Center LatLng

function navigate(crashLatLng) {
    $.getJSON("RescuerIn?opt=getaccs").done(function (json) {
        console.log("opLatLng: [" + opLatLng.lat + "," + opLatLng.lng + "] ||| crashLatLng: [" + crashLatLng.lat + "," + crashLatLng.lng + "]");
        directionsService.route({
            origin: opLatLng,
            destination: crashLatLng,
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
var OPT_GET_OL = "getOpLocation";
var opBound;
function getOpLatLng(opMap) {
    $.when($.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL})).done(function (json) {
        var opLocationJSON = $.parseJSON(json);
        opLatLng = {lat: opLocationJSON['latLng']['latitude'], lng: opLocationJSON['latLng']['longitude']};
        opBound = opLocationJSON['bound'];
        log("> LatLng : " + opLatLng['lat'] + " | " + opLatLng['lng']);
        log("> Bound : " + opBound);
        settingMap(opMap, opLatLng, opBound);
    });
}

function settingMap(opMap, opLatLng, bound) {
    opMap = new google.maps.Map(document.getElementById('map'), {
        zoom: 9,
        center: opLatLng
    });
    directionsDisplay = new google.maps.DirectionsRenderer;
    directionsService = new google.maps.DirectionsService;
    directionsDisplay.setMap(opMap);
    var opMarker = new google.maps.Marker({
        position: opLatLng,
        map: opMap,
        title: "Operting Place"
    });
    var opCircle = new google.maps.Circle({
        fillColor: '#AAA',
        map: opMap,
        center: opLatLng,
        radius: bound * 1000
    });
    setSettingRefProperties(opLatLng, bound);
}

function setSettingRefProperties(opLatLng, bound){
    $('a[href*=sett]').attr('href', 'To?opt=sett&lat='+opLatLng['lat']+"&lng="+opLatLng['lng']+"&bound="+bound);
}

/* Other */
function log(str) {
    console.log(str);
}

function callbackMessage(str) {
    $('#callback-msg').html(str);
}