/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
setInterval(updateAccident, 2000);

var records = 0;
function updateAccident() {
//    $.each(accidents, function (index, data) {
//        $('#accData').html("<tr><td>" + data.accCode + "</td>"
//                + "<td>" + data.latitude + "</td>"
//                + "<td>" + data.longtitude + "</td>"
//                + "<td>" + data.forceDetect + "</td>"
//                + "<td>" + data.speedDetect + "</td>"
//                + "<td>" + data.date + " : " + data.time + "</td></tr>");
//    });
}

var dataTable;
$('document').ready(function () {
    var quickAccURL = "Monitor?opt=quickacc";
    dataTable = $('#acctable').DataTable({
        "ajax": {
            "url": quickAccURL,
            "type": "GET",
            "dataSrc": ""
        },
        "columns": [
            {"data": "date", "width": "10%"},
            {"data": "time", "width": "10%"},
            {"data": "latitude", "width": "25%"},
            {"data": "longitude", "width": "25%"},
            {"data": "accCode", "width": "10%"},
            {"data": "accidentId", "width": "10%"}
        ],

        "drawCallback": ""
    });
});

setInterval( function () {
    dataTable.ajax.reload( null, false );
}, 3000 );

$('#acctable tbody').on( 'click', 'tr', function () {
    var accRow = dataTable.row( this ).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    console.log(lat);
    $("#out").html(lat+" : "+lng);
    somewhere = {lat: lat, lng: lng};
    navigate();
} );