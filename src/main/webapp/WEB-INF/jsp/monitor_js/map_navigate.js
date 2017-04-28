/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* Option Constants */
var OPT_STORE_OL = "storeOpLocation";

var opMap;
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var directionsDisplay;
var directionsService;
function initMap() {
    getOpLatLng(opMap);
}

var accidents = [];
var crashLatLng; // Crash LatLng
var opLatLng; // Operating Center LatLng

function navigate(crashLatLng) {
    $.getJSON("RescuerIn?opt=getaccs").done(function () {
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

/* Function */
var OPT_GET_OL = "getOpLocation";
var opBound;
function getOpLatLng(opMap) {
    $.when($.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL}))
            .done(function (json) {
                var opLocationJSON = $.parseJSON(json);
                opLatLng = {lat: opLocationJSON['latLng']['latitude'], lng: opLocationJSON['latLng']['longitude']};
                opBound = opLocationJSON['bound'];

                settingMap(opMap, opLatLng, opBound);
            })
            .fail(function () {
                setDefaultOpProperties(KMUTT_LATLNG.lat, KMUTT_LATLNG.lng, 10);
                return;
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
    new google.maps.Marker({
        position: opLatLng,
        map: opMap,
        title: "Operting Place"
    });
    new google.maps.Circle({
        fillColor: '#AAA',
        map: opMap,
        center: opLatLng,
        radius: bound * 1000
    });
    setSettingRefProperties(opLatLng, bound);
}


function setSettingRefProperties(opLatLng, bound) {
    $('a[href*=sett]').attr('href', 'To?opt=sett&lat=' + opLatLng['lat'] + "&lng=" + opLatLng['lng'] + "&bound=" + bound);
}

function setDefaultOpProperties(lat, lng, boundRadius) {
    $.ajax({
        url: "Setting?opt=" + OPT_STORE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function () {initMap();
        });
}

/* Other */

function callbackMessage(str) {
    $('#callback-msg').html(str);
}