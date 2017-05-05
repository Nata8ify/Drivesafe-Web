/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* Option Constants */
var OPT_STORE_OL = "storeOpLocation";

var opMap;
var opMapTemp;
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
        if (opMapTemp !== undefined) {
            opMap = opMapTemp;
        }
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
                opBound = opLocationJSON['neutralBound'];
                mainOpBound = opLocationJSON['mainBound'];
                settingMap(opMap, opLatLng, opBound, mainOpBound);
            })
            .fail(function () {
                setDefaultOpProperties(KMUTT_LATLNG.lat, KMUTT_LATLNG.lng, 10, 0);
                return;
            });
}

function settingMap(opMap, opLatLng, bound, mainBound) {
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
    if (mainBound !== 0) {
        new google.maps.Circle({
            fillColor: '#FAA',
            map: opMap,
            center: opLatLng,
            radius: mainBound * 1000
        });
    }
    setSettingRefProperties(opLatLng, bound, mainBound);
}


function setSettingRefProperties(opLatLng, bound, mainBound) {
    $('a[href*=sett]').attr('href', 'To?opt=sett&lat=' + opLatLng['lat'] + "&lng=" + opLatLng['lng'] + "&bound=" + bound+"&mainBound=" + mainBound);
}

function setDefaultOpProperties(lat, lng, boundRadius) {
    $.ajax({
        url: "Setting?opt=" + OPT_STORE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function () {
        initMap();
    });
}

$('#acctable tbody').on('<disabled>', 'tr .btnNearHospital', function () {
    //2 บรรทัดนี้ไม่เกี่ยวไส่ไว้ก่อน เผื่อเปน reference อันอื่น
    var accRow = dataTable.row($(this).parents('tr')).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    console.log(parseFloat(lat), parseFloat(lng));
    var req = {
        location: new google.maps.LatLng(lat, lng),
        radius: '5000',
        types: ['hospital']
    };
//    var domOpMap = $('#map')[0];
    opMapTemp = opMap;
    opMap = new google.maps.Map(document.getElementById('map'), {
        center: new google.maps.LatLng(lat, lng),
        zoom: 15
    });
    var pService = new google.maps.places.PlacesService(opMap); //map_navigate.js's opMap.
    pService.nearbySearch(req, function (results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
            for (var i = 0; i < results.length; i++) {
                console.log(results[i]);
                var place = results[i];
//                var marker = new google.maps.Marker({
//                    map: opMap,
//                    position: place.geometry.location
//                });
            }
        } else {
            console.log("not ok" + status);

        }
    });
});

/* Event Listener */
$('#btn-go-top').click(function () {
    document.location = '#demo-navbar';
});

/* Other */

function callbackMessage(str) {
    $('#callback-msg').html(str);
}