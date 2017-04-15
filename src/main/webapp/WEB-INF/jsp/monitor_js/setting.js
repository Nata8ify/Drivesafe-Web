/* Option Constants */
var OPT_STORE_OL = "storeOpLocation";
var OPT_UPDATE_OL = "updateOpLocation";
var OPT_GET_OL = "getOpLocation";
/* Google Map Function*/
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var opLocationMap;
function initMap() {
    opLocationMap = new google.maps.Map(document.getElementById('spec-location-map'), {
        zoom: 5,
        center: KMUTT_LATLNG,
        mapTypeId: 'hybrid'
    });
    var initMarker = new google.maps.Marker({position: KMUTT_LATLNG,
        map: opLocationMap,
        draggable: true,
        title: "Drag into new Operting Location",
        icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"});
}

/* Event Listener */
//var inputLocation = $('#spec-location-input');
//inputLocation.keyup(function () {
//    log(inputLocation.val());
//});
$('#spec-location-submit').click(function () {
    var lat = $('#spec-location-lat-input').val();
    var lng = $('#spec-location-lng-input').val();
    var boundRadius = $('#spec-location-boundrds-input').val();
    $.ajax({
        url: "Setting?opt=" + OPT_STORE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function (aresult) {
        log("lat: " + lat + " | " + " lng: " + lng + " | " + " boundRadius: " + boundRadius + "\n> Result is: " + aresult);
        callbackMessage(aresult);
    });

});

$('#update-location-submit').click(function () {
    var lat = $('#spec-location-lat-input').val();
    var lng = $('#spec-location-lng-input').val();
    var boundRadius = $('#spec-location-boundrds-input').val();
    $.ajax({
        url: "Setting?opt=" + OPT_UPDATE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function (aresult) {
        log("lat: " + lat + " | " + " lng: " + lng + " | " + " boundRadius: " + boundRadius + "\n> Result is: " + aresult);
        callbackMessage(aresult);
    });
});

$('#getcur-location-submit').click(function () {
    $.ajax({
        url: "Setting?opt=" + OPT_GET_OL}).done(function (aresult) {
        log("> Result is: " + aresult);
        callbackMessage(aresult);
    });
});

/* Other */
function log(str) {
    console.log(str);
}

function callbackMessage(str) {
    $('#callback-msg').html(str);
}