/* Option Constants */
var optStoreOL = "storeOpLocation";
/* Google Map Function*/
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var opLocationMap;
function initMap() {
    opLocationMap = new google.maps.Map(document.getElementById('spec-location-map'), {
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
//var inputLocation = $('#spec-location-input');
//inputLocation.keyup(function () {
//    log(inputLocation.val());
//});
$('#spec-location-submit').click(function(){
    var lat = $('#spec-location-lat-input').val();
    var lng = $('#spec-location-lng-input').val();
    var boundRadius = $('#spec-location-boundrds-input').val();
    var result = $.ajax({
        url: "Setting?opt="+optStoreOL,
        data: {
        lat: lat,
        lng: lng,
        boundRds: boundRadius
    }});
    log("lat: "+lat+" | "+" lng: "+lng+" | "+" boundRadius: "+boundRadius+"\n"+result);
    
});

/* Other */
function log(str) {
    console.log(str);
}