
var OPT_STORE_OL = "storeOpLocation";

var opMap;
var opMapTemp;
var KMUTT_LATLNG = {lat: 13.651553, lng: 100.495030};
var directionsDisplay;
var directionsService;
function initMap() {
    getOpLatLng(opMap);
}

var accidents = [];
var crashLatLng; // Crash LatLng
var opLatLng; // Operating Center LatLng

function navigate(crashLatLng) {
    $.getJSON("RescuerIn?opt=getaccs").done(function () {
        if (opMapTemp !== undefined) {
            opMap = opMapTemp;
        }
        directionsService.route({
            origin: opLatLng,
            destination: crashLatLng,
            travelMode: 'DRIVING'
        }, function (response, status) {
            if (status == 'OK') {
                directionsDisplay.setDirections(response);
            } else {
                window.alert('การนำทางล้มเหลวเนื่องจาก ' + status);
            }
        });
    });
}

function setMapCenter(latLng) {
    window.opMap.setCenter(latLng);
    window.opMap.setZoom(20);
}

/* Event Listener */

/* Function */
var OPT_GET_OL = "getOpLocation";
var opBound;
function getOpLatLng(opMap) {
    $.when($.ajax({
        url: "Setting?mode=n&opt=" + OPT_GET_OL}))
            .done(function (json) {
                var opLocationJSON = $.parseJSON(json);
                opLatLng = {lat: opLocationJSON['latLng']['latitude'], lng: opLocationJSON['latLng']['longitude']};
                opBound = opLocationJSON['neutralBound'];
                mainOpBound = opLocationJSON['mainBound'];
                settingMap(opMap, opLatLng, opBound, mainOpBound);
            })
            .fail(function () {
                setDefaultOpProperties(KMUTT_LATLNG.lat, KMUTT_LATLNG.lng, 10);
                return;
            });
}

function settingMap(opMap, opLatLng, bound, mainBound) {
    opMap = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: opLatLng
    });
    window.opMap = opMap;
//    var bound = new google.maps.LatLngBounds();
//    bound.extend(opLatLng);
//    var extendBoundLatLng = opLatLng.lat +bound/111;
//    bound.extend(extendBoundLatLng);
//    opMap.fitBounds(bound);
    directionsDisplay = new google.maps.DirectionsRenderer;
    directionsService = new google.maps.DirectionsService;
    directionsDisplay.setMap(opMap);
    updateIncidentMarkers(opMap);
    setInterval(function () {
        updateIncidentMarkers(opMap);
    }, 6000);
    new google.maps.Marker({
        position: opLatLng,
        map: opMap,
        title: "Operting Place"
    });
    new google.maps.Circle({
        fillColor: '#AAA',
        map: opMap,
        center: opLatLng,
        radius: bound * 1000
    });
    if (mainBound !== 0) {
        new google.maps.Circle({
            fillColor: '#FAA',
            map: opMap,
            center: opLatLng,
            radius: mainBound * 1000
        });
    }
    setSettingRefProperties(opLatLng, bound, mainBound);
}


function setSettingRefProperties(opLatLng, bound, mainBound) {
    $('a[href*=sett]').attr('href', 'To?opt=sett&lat=' + opLatLng['lat'] + "&lng=" + opLatLng['lng'] + "&bound=" + bound + "&mainBound=" + mainBound);
}

function setDefaultOpProperties(lat, lng, boundRadius) {
    $.ajax({
        url: "Setting?opt=" + OPT_STORE_OL,
        data: {
            lat: lat,
            lng: lng,
            boundRds: boundRadius
        }}).done(function () {
        initMap();
    });
}

/* Event Listener */
$('#btn-go-top').click(function () {
    document.location = '#demo-navbar';
});

/* Other */
function callbackMessage(str) {
    $('#callback-msg').html(str);
}

//----------------------------------------------------------------------------------------------------------------------
 var incidentTable;
(function ($) {
    "use strict"; // Start of use strict

    // Configure tooltips for collapsed side navigation
    $('.navbar-sidenav [data-toggle="tooltip"]').tooltip({
        template: '<div class="tooltip navbar-sidenav-tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>'
    });

    // Toggle the side navigation
    $("#sidenavToggler").click(function (e) {
        e.preventDefault();
        $("body").toggleClass("sidenav-toggled");
        $(".navbar-sidenav .nav-link-collapse").addClass("collapsed");
        $(".navbar-sidenav .sidenav-second-level, .navbar-sidenav .sidenav-third-level").removeClass("show");
    });

    // Force the toggled class to be removed when a collapsible nav link is clicked
    $(".navbar-sidenav .nav-link-collapse").click(function (e) {
        e.preventDefault();
        $("body").removeClass("sidenav-toggled");
    });

    // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
    $('body.fixed-nav .navbar-sidenav, body.fixed-nav .sidenav-toggler, body.fixed-nav .navbar-collapse').on('mousewheel DOMMouseScroll', function (e) {
        var e0 = e.originalEvent,
                delta = e0.wheelDelta || -e0.detail;
        this.scrollTop += (delta < 0 ? 1 : -1) * 30;
        e.preventDefault();
    });

    // Scroll to top button appear
    $(document).scroll(function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

    // Configure tooltips globally
    $('[data-toggle="tooltip"]').tooltip()

    // Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        event.preventDefault();
    });

    // Call the dataTables jQuery plugin
   
    var accTableUpdateMsg = $("#acctable-lastest-update");
    $(document).ready(function () {
        incidentTable = $('#dataTable').DataTable({
            "ajax": {
                "url": "Monitor?opt=currentDateInBoundReq",
                "type": "GET",
                "dataSrc": ""
            },
            "columns": [
                {"data": "time", "width": "5%"},
                {"width": "90%"},
                {"width": "5%"}
            ],
            "order": [[1, "asc"]],
            "bInfo": false,
            "fnRowCallback": function (nRow, aData) {
                var accCodeText = aData.accCode; // ID is returned by the server as part of the data
                var $nRow = $(nRow); // cache the row wrapped up in jQuery
                // alert(accCodeText);
                if (accCodeText === "A") {
                    $nRow.css({"background-color": "#ff8080"});
                    $nRow.find("td").eq(1).css({"background-color": "#dc3545"});
                } else if (accCodeText === "G") {
                    $nRow.css({"background-color": "#ffdd55"});
                    $nRow.find("td").eq(1).css({"background-color": "#ffc107"});
                } else if (accCodeText === "R") {
                    $nRow.css({"background-color": "#80e5ff"});
                    $nRow.find("td").eq(1).css({"background-color": "#007bff"});
                } else if (accCodeText === "C") {
                    $nRow.css({"background-color": "#80ff80"});
                    $nRow.find("td").eq(1).css({"background-color": "#28a745"});
                }
                $("td", nRow).eq(1).empty();
                $("td", nRow).eq(1).prepend("<img class='img-acc-ico' src='image/acctype/" + aData.accType + ".png' width='40px' />");
                $(".img-acc-ico").css("text-align", "center");
                $.ajax({
                    "url": "http://maps.googleapis.com/maps/api/geocode/json",
                    "data": {"sensor": true, "latlng": (aData.latitude) + "," + (aData.longitude)},
                    "success": function (result) {
                        if (result.status == "OK") {
                            var addrComponent = result.results[0].address_components;
                            $("td", nRow).eq(2).html(addrComponent[0].long_name.concat(", ".concat(addrComponent[1].long_name)).concat(", ".concat(addrComponent[2].long_name)).concat(", ".concat(addrComponent[3].long_name)).concat(", ".concat(addrComponent[5].long_name)));
                        } else {
                            $("td", nRow).eq(2).html("Missing Place, (" + (aData.latitude) + "," + (aData.longitude) + ")");
                        }
                        $("td", nRow).eq(2).css("font-size", "14px");
                        $("td", nRow).eq(2).css("font-weight", "bolder");
                    }
                });
                return nRow;
            },
            "language": {
                "loadingRecords": "ไม่มีแจ้งเหตุเกิดขึ้น"
            },
            "searching": false,
            "bLengthChange": false,
            "pageLength": 5});
    });
    setInterval(function () {
        accTableUpdateMsg.html("Updated " + moment().format("LL HH:mm:ss"));
        incidentTable.ajax.reload(null, false);
    }, 6000);

    $('#dataTable tbody').on('click', 'tr', function () {
        var accRow = incidentTable.row($(this)).data();
        var lat = accRow.latitude;
        var lng = accRow.longitude;
        var latLng = {lat: lat, lng: lng};
        setMapCenter(latLng);
    });

})(jQuery); // End of use strict

// Chart.js scripts
// -- Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// -- Bar Chart Example
var barCtx = document.getElementById("myBarChart");
var myLineChart;
var reportFreqSeries = [];
var isGetFreqreportFirst = true;
var historyReportUpdateMsg = $("#history-report-lastest-update");
setTimeout(function () {
    buildReportFreqChart();
    setInterval(buildReportFreqChart, 10000);
}, 3500);
function buildReportFreqChart() {
    $.ajax({
        url: "Dashboard?opt=getReportFreqTimeSeries",
        success: function (result) {
            reportFreqSeries = JSON.parse(result);
            if (isGetFreqreportFirst) {
                myLineChart = new Chart(barCtx, {
                    type: 'bar',
                    data: {
                        labels: ["00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00", "04:00-05:00", "05:00-06:00", "07:00-08:00", "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"],
                        datasets: [{
                                label: "ความถี่ (ครั้ง)",
                                backgroundColor: "rgba(2,117,216,1)",
                                borderColor: "rgba(2,117,216,1)",
                                data: reportFreqSeries
                            }]
                    },
                    options: {
                        animation: false,
                        responsive : true,
                        scales: {
                            xAxes: [{
                                    time: {
                                        unit: 'ชั่วโมง'
                                    },
                                    gridLines: {
                                        display: false
                                    },
                                    ticks: {
                                        autoSkip : false,
                                        maxTicksLimit: 6,
                                        //For making bar chart only show the recent statistics incident
                                        /*callback: function(value) {
                                            return value
                                        }*/
                                    }
                                }],
                            yAxes: [{
                                    gridLines: {
                                        display: true
                                    },
                                    ticks: {
                                        min: 0,
                                        stepSize: 5
                                    }
                                }]
                        },
                        legend: {
                            display: false
                        }
                    }
                });
            } else {
                myLineChart.data.datasets[0].data = reportFreqSeries;
                myLineChart.update();
                historyReportUpdateMsg.html("Updated " + moment().format("LL HH:mm:ss"));
            }
        }
    });
}


// -- Pie Chart Example
var ctx = document.getElementById("myPieChart");
var myPieChart;
var codeSeries = [];
var isGetStatusFirst = true;
var accCodeUpdateMsg = $("#acccode-lastest-update");
setInterval(buildAccCodeChart, 3000);
function buildAccCodeChart() {
    $.ajax({
        url: "Dashboard?opt=getStatusPercentage",
        data: {date: moment().format("YYYY-MM-DD")},
        success: function (result) {
            codeSeries = JSON.parse(result);
            if (isGetStatusFirst) {
                myPieChart = new Chart(ctx, {
                    type: 'pie',
                    animation: false,
                    data: {
                        labels: ["รอการช่วยเหลือ", "กำลังเดินทางไป", "กำลังช่วยเหลือ", "การช่วยเหลือเสร็จสิ้น"],
                        datasets: [{
                                data: codeSeries,
                                backgroundColor: ['#dc3545', '#ffc107', '#007bff', '#28a745']
                            }] 
                    }
                });
                isGetStatusFirst = false;
            } else {
                accCodeUpdateMsg.html("Updated " + moment().format("LL HH:mm:ss"));
                myPieChart.data.datasets[0].data = codeSeries;
                myPieChart.update();
            }
        }
    });
}

/* Feed Here (Below)  */
var feeds;
setTimeout(function () {
    getFeeds();
    setInterval(getFeeds, 10000);
}, 10);
function getFeeds() {
    feeds = [];
    $.ajax({
        url: "DashboardFeed?opt=get",
        data: {limit: 5, date: getDateNow()},
        success: function (resultFeeds) {
            feeds = JSON.parse(resultFeeds);
            $("#append-feed").empty();
            if (feeds == null) {
                $("#append-feed").prepend("<div style='text-align:center'><i  style='padding: 35px; font-size:16px;' >No Feed Available</i></div>");
                return;
            }
            var feedBodyMessage;
            var feedContent;
            $.each(feeds, function (index, feed) {

                $.ajax({
                    "url": "http://maps.googleapis.com/maps/api/geocode/json",
                    "data": {"sensor": true, "latlng": (feed.accident.latitude) + "," + (feed.accident.longitude)},
                    "success": function (result) {
                        if (result.status == "OK") {
                            var addrComponent = result.results[0].address_components;
                            place = (addrComponent[0].long_name.concat(", ".concat(addrComponent[1].long_name)).concat(", ".concat(addrComponent[2].long_name)).concat(", ".concat(addrComponent[3].long_name)).concat(", ".concat(addrComponent[5].long_name)));
                            switch (feed.updatedAccCode) {
                                case "A" :
                                    feedBodyMessage = " ขอความช่วยเหลือที่ ".concat(place);
                                    break;
                                case "G" :
                                    feedBodyMessage = " เจ้าหน้าที่กำลังเดินทางไปช่วยที่ ".concat(place);
                                    break;
                                case "R" :
                                    feedBodyMessage = " เจ้าที่หน้าที่กำลังช่วยเหลือผู้ประสบภัยที่ ".concat(place);
                                    break;
                                case "C" :
                                    feedBodyMessage = " การช่วยเหลือเสร็จสิ้น";
                                    break;
                                case "U" :
                                    feedBodyMessage = " ยกเลิกการขอความช่วยเหลือ ";
                                    break;
                                case "S" :
                                    feedBodyMessage = " รายงานว่าเป็นอุบัติเหตุ/เหตุร้าย ที่ไม่ได้เกิดขึ้นจริง ";
                                    break;
                            }
                        } else {
                            place = "การเชื่อมต่อผิดพลาด";
                        }
                        feedContent = $("<span href='#' class='list-group-item list-group-item-action'><div class='media'><img class='d-flex mr-3 rounded-circle' src='image/color/" + feed.updatedAccCode.toLowerCase() + ".PNG' width='50px' alt=''><div class='media-body' style='font-size: 125%' ><strong>" + ((feed.updatedAccCode === 'A' || feed.updatedAccCode === 'U') ? feed.reporterName : feed.rscrName) + "</strong>" + feedBodyMessage + "<div class='text-muted smaller'>" + feed.timestamp + "</div></div></div> </span>")
                                .click(function () {
                                    window.location = "#map";
                                    setMapCenter({lat: feed.accident.latitude, lng: feed.accident.longitude});
                                });
                        $("#append-feed").append(feedContent);                       
                    }
                });

            });
        }
    });
}


//----------------------------------------------------------------------------------------------------------------------

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
        "order": [[1, "asc"]],
        "columnDefs": [{
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
                $nRow.css({"background-color": "#ff8080"});
                accCodeDesc = "รอการช่วยเหลือ";
            } else if (accCodeText === "G") {
                $nRow.css({"background-color": "#ffc107"});
                accCodeDesc = "กำลังเดินทางไป";
            } else if (accCodeText === "R") {
                $nRow.css({"background-color": "#80e5ff"});
                accCodeDesc = "กำลังช่วยเหลือ";
            } else if (accCodeText === "C") {
                $nRow.css({"background-color": "#80ff80"});
                accCodeDesc = "ช่วยเหลือสำเร็จ";
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
        }
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
            removeMarkers(incidentMarkers);
            incidentMarkers = [];
            try {
            $.each(JSON.parse(result), function (index, acc) {
                marker = new google.maps.Marker({
                    position: {lat: acc.latitude, lng: acc.longitude},
                    map: opMap,
                    title: "ID " + acc.accidentId + " : " + acc.accCode,
                    icon: "image/markers/".concat(acc.accCode.toLowerCase().concat(".png"))
                });
                marker.addListener('click', function(){breifIncidentInfo(acc.userId)});
                incidentMarkers.push(marker);
            });
        } catch(err){
            console.log(err);
            incidentTable.clear();
            incidentTable.draw();
        }
        }
    });
}

function breifIncidentInfo(reporterId){
    $.ajax({
        "url" : "RescuerIn?opt=get_userinfo",
        "data" : {userId : reporterId},
        "success" : function(info){
            var pfInfo = JSON.parse(info);
            alert("ผู้รายงาน : ".concat(pfInfo.firstName+" "+pfInfo.lastName).concat("\n")
                    .concat("รหัสประจำตัวประชาชน : ").concat(pfInfo.personalId).concat("\n")
                    .concat("เบอร์โทรศัพท์ติดต่อ : ".concat(pfInfo.phoneNumber)).concat("\n")
                    .concat("ที่อยู่ของผู้รายงาน : ".concat(pfInfo.address1).concat(" ").concat(pfInfo.address2 !== ""?pfInfo.address2:" ")));
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

function removeMarkers(incidentMarkers) {
    $.each(incidentMarkers, function (index, marker) {
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


function getDateNow(){
    return moment().format("YYYY-MM-DD");
}
