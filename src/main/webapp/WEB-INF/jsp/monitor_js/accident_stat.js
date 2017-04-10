//Do Number of Accident Chart.
var labelsDate = [];
var seriesAccTimes = [];
var beginDate;
var endDate;

$.getJSON({url: "Statistic?opt=statTotalAcc"}).done(function (json) {
    $.each(json, function (index, element) {
        labelsDate.push(index);
        seriesAccTimes.push(element);
        if (beginDate === undefined) {
            beginDate = index;
        } else {
            endDate = index;
        }
    });
    $('#acc-period-title').html(" (" + beginDate + " To " + endDate + ")");
    $('#input-b-date').val(beginDate);
    $('#input-e-date').val(endDate);
});
var data = {
    // A labels array that can contain any sort of values
    labels: labelsDate,
    // Our series array that contains series objects or in this case series data arrays
    series: [
        seriesAccTimes
    ]
};
postAccidentStatChart(data);

//Do Geo Accident Map Stat
var nAccStatMap;
var NEARSIT_LATLNG = {lat: 13.652277, lng: 100.494457};
function initMap() {
    nAccStatMap = new google.maps.Map(document.getElementById('acc-stat-map'), {
        zoom: 9,
        center: NEARSIT_LATLNG
    });
    postTotalAccidentGeoMap();
}

$('#input-b-date, #input-e-date').change(function () {
    var now = moment(new Date()).format("YYYY-MM-DD");
    var beginInputDate = $('#input-b-date').val();
    var endInputDate = $('#input-e-date').val();
    if (beginInputDate < endInputDate & endInputDate <= now) {
        labelsDate = [];
        seriesAccTimes = [];
        $.getJSON({url: "Statistic?opt=statSpecPeriodAcc"}, {
            bDate: $('#input-b-date').val(),
            eDate: $('#input-e-date').val()
        }).done(function (json) {
            $.each(json, function (index, element) {
                labelsDate.push(index);
                seriesAccTimes.push(element);
                if (beginDate === undefined) {
                    beginDate = index;
                } else {
                    endDate = index;
                }
            });
        });
        $('#acc-period-title').html("(" + $('#input-b-date').val() + " To " + $('#input-e-date').val() + ")");
        setTimeout(function () {
            postByDatePeriodAccidentGeoMap();
            postAccidentStatChart({
                labels: labelsDate,
                series: [
                    seriesAccTimes
                ]
            });
            //Hide Alert.
            $('#alrt-invalid-period').fadeOut();
        }
        , 500);
    } else if (endInputDate < beginInputDate) {
        $('#alrt-ip-msg').html("Begin Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")")
        $('#alrt-invalid-period').fadeIn();
        $('#input-b-date').val(beginDate);
    } else {
        $('#alrt-ip-msg').html("End Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")")
        $('#alrt-invalid-period').fadeIn();
        $('#input-e-date').val(endDate);
    }
});




/* Post to Page section */
var nAccidentChart;
function postAccidentStatChart(data) {
    setTimeout(function () {
        nAccidentChart = new Chartist.Line('.ct-chart', data, {
            axisY: {
                type: Chartist.AutoScaleAxis,
                onlyInteger: true,
            },
            axisX: {
                labelInterpolationFnc: function (value) {
                    return moment(value).format('MMM D');
                }}
        });
    }, 500);
}

var markers = [];
function postTotalAccidentGeoMap() {
    setTimeout(function () {
        $.getJSON("Statistic?opt=statAccGeo").done(function (json) {
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

function postByDatePeriodAccidentGeoMap() {
    setTimeout(function () {
        $.getJSON("Statistic?opt=statPeriodAccGeo", {
            bDate: $('#input-b-date').val(),
            eDate: $('#input-e-date').val()}).done(function (json) {
            emptyMarker();
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

/*Utilities Function*/
function emptyMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
        console.log(markers[i].getPosition().lat() + " : " + markers[i].getPosition().lng() + " -delete " + i);
    }
    markers = [];
}

function setMarker(json) {
    $.each(json, function (index, element) {
        var lat = element.latitude;
        var lng = element.longitude;
        var marker = new google.maps.Marker({
            position: {lat, lng},
            map: nAccStatMap
        });
        markers.push(marker);
    });
}

/* Chartist.js Properties*/
