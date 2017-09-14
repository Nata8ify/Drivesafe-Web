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
        ],
        "columnDefs": [{
                "targets": -1,
                "data": null,
                "defaultContent": "<button class='accident btn btn-default'> &nbsp;<i class='glyphicon glyphicon-map-marker'></i></button>"
            }
        ],
        "order": [[4, "asc"]],
        "language": {
            "loadingRecords":"Pending",
            "zeroRecords": "No Accident" //<- Not Work?
        },
        "fnRowCallback": function (nRow, aData) {          
            var accCodeText = aData.accCode; // ID is returned by the server as part of the data
            var $nRow = $(nRow); // cache the row wrapped up in jQuery
            var accCodeDesc;
            // alert(accCodeText);
            if (accCodeText === "A") {
                $nRow.css({"background-color": "#ff8080"});             
                accCodeDesc = "Waiting for rescue";
            } else if (accCodeText === "G") {
                $nRow.css({"background-color": "#ffc107"});
                accCodeDesc = "Ongoing";
            } else if (accCodeText === "R") {
                $nRow.css({"background-color": "#80e5ff"});
                accCodeDesc = "Rescuing";
            } else if (accCodeText === "C") {
                $nRow.css({"background-color": "#80ff80"});
                accCodeDesc = "Rescue success";
            }
            $("td", nRow).eq(1).prepend("<img src='image/acctype/"+aData.accType+".png' width='50px' class='img img-thumbnail'/>");
            $("td", nRow).eq(3).html(accCodeDesc);
            $.ajax({
                "url": "http://maps.googleapis.com/maps/api/geocode/json",
                "data": {"sensor": true, "latlng": (aData.latitude) + "," + (aData.longitude)},
                "success": function (result) {
                    if (result.status == "OK") {
                        $("td", nRow).eq(2).html(result.results[0].formatted_address);
                    } else {
                        $("td", nRow).eq(2).html("Missing Place, ("+(aData.latitude) + "," + (aData.longitude)+")");
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


$('#acctable tbody ').on('click', 'tr .accident', function () {
    var accRow = dataTable.row($(this).parents('tr')).data();
    var lat = accRow.latitude;
    var lng = accRow.longitude;
    $("#callback-msg").html("Selected Accident Location is on " + lat + " : " + lng);
    document.location = "#map";
    crashLatLng = {lat: lat, lng: lng};
    navigate(crashLatLng);
});
