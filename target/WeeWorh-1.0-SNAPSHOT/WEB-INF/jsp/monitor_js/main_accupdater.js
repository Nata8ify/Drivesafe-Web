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
            {"data": "time", "width": "10%"},
            {"data": "latitude", "width": "25%"},
            {"data": "longitude", "width": "25%"},
            {"data": "accCode", "width": "10%"},
            {"data": "goToAcc", "width": "10%"},
            {"data": "hospital", "width": "10%"}
        ],
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
            // alert(accCodeText);
            if (accCodeText === "A") {
                $nRow.css({"background-color": "#ff7575"});
            } else if (accCodeText === "G") {
                $nRow.css({"background-color": "#ffb912"});
            } else if (accCodeText === "R") {
                $nRow.css({"background-color": "#ffed80"});
            } else if (accCodeText === "C") {
                $nRow.css({"background-color": "#8cff8c"});
            }
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


$('#acctable tbody ').on('click', 'tr .accident', function () {

    var accRow = dataTable.row($(this).parents('tr')).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    $("#callback-msg").html("Selected Accident Location is on " + lat + " : " + lng);
    document.location = "#map";
    crashLatLng = {lat: lat, lng: lng};
    navigate(crashLatLng);
});




