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

    google.maps.event.addListener(opMarker, 'dragend', function (evt) {
        $.ajax({
            url: "Setting?&opt=" + OPT_UPDATE_OL,
            data: {
                lat: this.getPosition().lat(),
                lng: this.getPosition().lng(),
                boundRds: $('#spec-location-boundrds-input').val()
            }}).done(function (aresult) {
            callbackMessage(aresult);
        });
    });
    
    initialSearrchBox(opLocationMap);
}
/* Initialize Function*/
function initialSearrchBox(opLocationMap){
     var searchBox = new google.maps.places.SearchBox($('#spec-location-input')[0]);
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
    OpLocation = {lat: parseFloat(lat), lng: parseFloat(lng)};
    var boundRadius = $('#spec-location-boundrds-input').val();
    $.ajax({
        url: "Setting?&opt=" + OPT_UPDATE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function (aresult) {
        log("lat: " + lat + " | " + " lng: " + lng + " | " + " boundRadius: " + boundRadius + "\n> Result is: " + aresult);
        opMarker.setMap(null);
        opMarker = new google.maps.Marker({position: OpLocation,
            map: opLocationMap,
            draggable: true,
            title: "Drag into new Operting Location",
            icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"});
        callbackMessage(aresult);
    });
});

$('#getcur-location-submit').click(function () {
    $.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL}).done(function (aresult) {
        var opProperties = $.parseJSON(aresult);
        log(opProperties);
        $('#spec-location-lat-input').val(opProperties['latLng']['latitude']);
        $('#spec-location-lng-input').val(opProperties['latLng']['longitude']);
        $('#spec-location-boundrds-input').val(opProperties['bound']);
        callbackMessage("Current Operating Location is " + aresult);
    });
});

$('#spec-location-input').change(function(){
});

/* Other */
function log(str) {
    console.log(str);
}

function callbackMessage(str) {
    $('#callback-msg').html(str);
}