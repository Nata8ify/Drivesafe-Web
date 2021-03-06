/* Option Constants */
var OPT_STORE_OL = "storeOpLocation";
var OPT_UPDATE_OL = "updateOpLocation";
var OPT_GET_OL = "getOpLocation";
/* Google Map Function*/
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var opLocationMap;
var opMarker;
var OpLocation;
function initMap() {
    OpLocation = {lat: parseFloat($('#spec-location-lat-input').val()), lng: parseFloat($('#spec-location-lng-input').val())};

    opLocationMap = new google.maps.Map(document.getElementById('spec-location-map'), {
        zoom: 5,
        center: OpLocation,
        mapTypeId: 'hybrid'
    });
    opMarker = new google.maps.Marker({position: OpLocation,
        map: opLocationMap,
        draggable: true,
        title: "Drag into new Operting Location",
        icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"});

//    google.maps.event.addListener(opMarker, 'dragend', function (evt) {
//        $.ajax({
//            url: "Setting?&opt=" + OPT_UPDATE_OL,
//            data: {
//                lat: this.getPosition().lat(),
//                lng: this.getPosition().lng(),
//                boundRds: $('#spec-location-boundrds-input').val(),
//                mBoundRds: $('#spec-location-mboundrds-input').val()
//            }}).done(function (aresult) {
//            callbackMessage(aresult);
//        });
//    });
    
    google.maps.event.addListener(opMarker, 'dragend', function (evt) {
        setNewOpMarker(opLocationMap, {lat: this.getPosition().lat() ,lng: this.getPosition().lng()});
    });
    
    google.maps.event.addListener(opLocationMap, 'dblclick', function (evt) {
        var dblLatLng = {lat: evt.latLng.lat(), lng: evt.latLng.lng()};
        setNewOpMarker(opLocationMap, dblLatLng);
        $.ajax({
            url: "Setting?&opt=" + OPT_UPDATE_OL,
            data: {
                lat: evt.latLng.lat(),
                lng: evt.latLng.lng(),
                boundRds: $('#spec-location-boundrds-input').val(),
                mBoundRds: $('#spec-location-mboundrds-input').val()
            }}).done(function (aresult) {
            callbackMessage(aresult);
        });
    });

    initialSearchBox(opLocationMap, opMarker);
}

function setNewOpMarker(opLocationMap, OpLocation) {
    opMarker.setMap(null);
    opMarker = new google.maps.Marker({position: OpLocation,
        map: opLocationMap,
        draggable: true,
        title: "Drag into new Operting Location",
        icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"});
    google.maps.event.addListener(opMarker, 'dragend', function (evt) {
        setNewOpMarker(opLocationMap, {lat: this.getPosition().lat() ,lng: this.getPosition().lng()});
    });
    opLocationMap.setCenter(OpLocation);
    setInputLatLng(OpLocation.lat, OpLocation.lng);
}

/* Initialize Function*/
function initialSearchBox(opLocationMap, OpLocation) {
    var placeInput = $('#spec-location-input')[0];
    var autocomplete = new google.maps.places.Autocomplete(placeInput);
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        var searchLat = autocomplete.getPlace().geometry.location.lat();
        var searchLng = autocomplete.getPlace().geometry.location.lng();
        setInputLatLng(searchLat, searchLng);
        OpLocation = {lat: searchLat, lng: searchLng};
        setNewOpMarker(opLocationMap, OpLocation);
    });
}


/* Setting Function */
function setInputLatLng(lat, lng) {
    $('#spec-location-lat-input').val(lat);
    $('#spec-location-lng-input').val(lng);
}

/* Event Listener */

$('#spec-location-submit').click(function () {
    var lat = $('#spec-location-lat-input').val();
    var lng = $('#spec-location-lng-input').val();
    var boundRadius = $('#spec-location-boundrds-input').val();
    $.ajax({
        url: "Setting?opt=" + OPT_STORE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius,
            mBoundRds: $('#spec-location-mboundrds-input').val()
        }}).done(function (aresult) {
        callbackMessage(aresult);
    });
});

$('#update-location-submit').click(function () {
    var lat = $('#spec-location-lat-input').val();
    var lng = $('#spec-location-lng-input').val();
    OpLocation = {lat: parseFloat(lat), lng: parseFloat(lng)};
    var boundRadius = $('#spec-location-boundrds-input').val();
    $.ajax({
        url: "Setting?&opt=" + OPT_UPDATE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius,
            mBoundRds: $('#spec-location-mboundrds-input').val()
        }}).done(function (aresult) {
        setNewOpMarker(opLocationMap, OpLocation);
        callbackMessage(aresult);
    });
});

$('#getcur-location-submit').click(function () {
    $.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL}).done(function (aresult) {
        var opProperties = $.parseJSON(aresult);
        $('#spec-location-lat-input').val(opProperties['latLng']['latitude']);
        $('#spec-location-lng-input').val(opProperties['latLng']['longitude']);
        $('#spec-location-boundrds-input').val(opProperties['neutralBound']);
        setNewOpMarker(opLocationMap, {lat: opProperties['latLng']['latitude'] ,lng: opProperties['latLng']['longitude']});
    callbackMessage("Current Operating Location is " + aresult);
    });
});

$('#spec-location-input').change(function () {
});

/* Other */
//function log(str) {
//    console.log(str);
//}

function callbackMessage(str) {
    $('#callback-msg').html(str);
}