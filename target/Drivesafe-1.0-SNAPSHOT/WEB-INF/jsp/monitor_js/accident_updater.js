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
        "language": {
            "loadingRecords": "Pending ... ",
            "zeroRecords": "No Accident Rescue Request (For Now)" //<- Not Work?
        },
        "drawCallback": ""
    });
});

setInterval(function () {
    dataTable.ajax.reload(null, false);
    $.fn.dataTable.ext.errMode = 'none'; //Disable the Error Dialog Only.
}, 3000);

$('#acctable tbody').on('click', 'tr', function () {
    var accRow = dataTable.row(this).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    $("#callback-msg").html("Selected Accident Location is on "+lat + " : " + lng);
    crashLatLng = {lat: lat, lng: lng};
    navigate(crashLatLng);
});

