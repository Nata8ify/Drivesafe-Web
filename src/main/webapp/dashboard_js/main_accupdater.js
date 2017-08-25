/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$.fn.dataTable.ext.errMode = 'none';

var dataTable;
$('document').ready(function () {
    
    var quickAccURL = "Monitor?opt=currentDateInBoundReq";
    dataTable = $('#acctable').DataTable({
        stateSave: true,
        "ajax": {
            "url": quickAccURL,
            "type": "GET",
            "dataSrc": ""
        },
        "columns": [
            {"data": "time", "width": "5%"},
            {"width": "5%"},
            {"width": "50%"},
            {"data": "accCode", "width": "10%"},
            {"data": "goToAcc", "width": "10%"},
            {"data": "hospital", "width": "10%"}
        ],
        "order": [[1, "asc"]],
        "columnDefs": [{
                "targets": -1,
                "data": null,
                "defaultContent": "<button class='btnNearHospital btn btn-default'> &nbsp;<i class='glyphicon glyphicon-search'></i></button>"//< ปุ่มกดไปโรงบาล โค้ดอันล่างสุด 
            }, {
                "targets": -2,
                "data": null,
                "defaultContent": "<button class='accident btn btn-default'> &nbsp;<i class='glyphicon glyphicon-map-marker'></i></button>"
            }

        ],
        "language": {
            "loadingRecords": "Pending ... ",
            "zeroRecords": "No Accident Rescue Request (For Now)" //<- Not Work?
        },
        "fnRowCallback": function (nRow, aData) {
            var accCodeText = aData.accCode; // ID is returned by the server as part of the data
            var $nRow = $(nRow); // cache the row wrapped up in jQuery
            var accCodeDesc;
            // alert(accCodeText);
            if (accCodeText === "A") {
                $nRow.css({"background-color": "#ff7575"});
                accCodeDesc = "Waiting for Rescue";
            } else if (accCodeText === "G") {
                $nRow.css({"background-color": "#ffb912"});
                accCodeDesc = "Going";
            } else if (accCodeText === "R") {
                $nRow.css({"background-color": "#ffed80"});
                accCodeDesc = "Rescuing";
            } else if (accCodeText === "C") {
                $nRow.css({"background-color": "#8cff8c"});
                accCodeDesc = "Closed";
            }
            $("td", nRow).eq(1).prepend("<img src='image/acctype/" + aData.accType + ".png' width='50px' class='img img-thumbnail'/>");
            $("td", nRow).eq(3).html(accCodeDesc);
            $.ajax({
                "url": "http://maps.googleapis.com/maps/api/geocode/json",
                "data": {"sensor": true, "latlng": (aData.latitude) + "," + (aData.longitude)},
                "success": function (result) {
                    if (result.status == "OK") {
                        $("td", nRow).eq(2).html(result.results[0].formatted_address);
                    } else {
                        $("td", nRow).eq(2).html("Missing Place, (" + (aData.latitude) + "," + (aData.longitude) + ")");
                    }
                }
            });
            return nRow
        },
        "order": [[0, "desc"]]


    });
});

var recCount;
var NEWCOMING_ALARM_URL = "http://abaaabcd.dl-one2up.com/onetwo/content/2017/5/7/d1218b20726722127d5bdf79a14706bb.mp3";
setInterval(function () {
    recCount = dataTable.data().count();
    dataTable.ajax.reload(function () {
        if (recCount < dataTable.data().count()) {
            new Audio(NEWCOMING_ALARM_URL).play();
        }
    }, false);

    $.fn.dataTable.ext.errMode = 'none'; //Disable the Error Dialog Only.
}, 3000);




$('#acctable tbody ').on('click', 'tr .btnNearHospital', function () {
    var accRow = dataTable.row($(this).parents('tr')).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    crashLatLng = {lat: lat, lng: lng};

    map = new google.maps.Map(document.getElementById('map'), {
        center: crashLatLng,
        zoom: 15,
    });

    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch({
        location: crashLatLng,
        types: ['hospital'],
        radius: 5000,
        rankBy: google.maps.places.RankBy.PROMINENCE
    }, callback);
});

var incidentMarkers = [];
function updateIncidentMarkers(opMap) {
    $.ajax({
        url: "Monitor?opt=currentDateInBoundReq",
        data: {userId: "${pf.userId}"},
        success: function (result) {
            var marker;
            console.log("++++++++++++++++++++");
                removeMarkers(incidentMarkers);
                incidentMarkers = [];
            $.each(JSON.parse(result), function (index, acc) {
                marker = new google.maps.Marker({
                    position: {lat: acc.latitude, lng : acc.longitude},
                    map: opMap,
                    title: "ID "+acc.accidentId+" : "+acc.accCode,
                    icon: "image/markers/".concat(acc.accCode.toLowerCase().concat(".png"))
                });
                incidentMarkers.push(marker);
            });
        }
    });
}

function callback(results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
        }
    }
}

function removeMarkers(incidentMarkers){
    $.each(incidentMarkers, function(index, marker){
        marker.setMap(null);
    });
}

function createMarker(place) {
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location
    });

    google.maps.event.addListener(marker, 'click', function () {
        infowindow.setContent(place.name);
        infowindow.open(map, this);
    });
}