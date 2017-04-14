
/* Google Map Function*/
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var opLocationMap;
function initMap() {
    opLocationMap = new google.maps.Map(document.getElementById('spec-oper-location-map'), {
        zoom: 5,
        center: KMUTT_LATLNG
    });
    var initMarker = new google.maps.Marker({position: KMUTT_LATLNG,
        map: opLocationMap,
        draggable: true,
        title: "Drag into new Operting Location",
        icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"});
}

/* Event Listener */
var inputLocation = $('#spec-oper-location-input');
inputLocation.keyup(function () {
    log(inputLocation.val());
});
$('#spec-oper-location-submit').click(function(){
    
});

/* Other */
function log(str) {
    console.log(str);
}