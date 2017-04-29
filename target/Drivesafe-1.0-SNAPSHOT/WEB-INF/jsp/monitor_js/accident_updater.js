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
            {"data": "time", "width": "15%"},
            {"data": "latitude", "width": "25%"},
            {"data": "longitude", "width": "25%"},
            {"data": "accStatus", "width": "15%"},
            {"data": "hospital", "width": "10%"}
        ],
        "columnDefs": [{ 
                            "targets": -1, 
                            "data": null, 
                            "defaultContent" : "<button>Go!</button>"//< ปุ่มกดไปโรงบาล โค้ดอันล่างสุด 
        }], 
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

$('#acctable tbody').on( 'click', 'button', function () {
    //2 บรรทัดนี้ไม่เกี่ยวไส่ไว้ก่อน เผื่อเปน reference อันอื่น
    //var buttonHosp = dataTable.row($(this).data('tr')).data();
    //alert("test!"); 
});

