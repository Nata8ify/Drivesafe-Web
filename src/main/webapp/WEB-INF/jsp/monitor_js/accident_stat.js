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
    $('#nAccStatTitle').append(" [" + beginDate + " to " + endDate + "]");
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
        zoom: 10,
        center: NEARSIT_LATLNG
    });
    postAccidentGeoMap();
}

$('#input-b-date, #input-e-date').change(function () {
    var now = new Date();
    var beginInputDate = new Date($('#input-b-date').val());
    var endInputDate = new Date($('#input-e-date').val());
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
            $('#nAccStatTitle').html(" [" + beginDate + " to " + endDate + "]");
        });

        setTimeout(function () {
            nAccidentChart.update({
                labels: labelsDate,
                series: [
                    seriesAccTimes
                ]
            });
        }, 500);
    } else if (endInputDate < beginInputDate) {
        alert("Begin Date shouldn't be after the present date. [" + new Date() + "]");
        $('#input-b-date').val(beginDate);
    } else {
        alert("End Date shouldn't be after the present date. [" + new Date() + "]");
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
                onlyInteger: true
            }
        });
    }, 500);
}
function postAccidentGeoMap() {
    setTimeout(function () {
        $.getJSON("Statistic?opt=statAccGeo").done(function (json) {
            $.each(json, function (index, element) {
                var lat = element.latitude;
                var lng = element.longitude;
                var marker = new google.maps.Marker({
                    position: {lat, lng},
                    map: nAccStatMap
                });
            })
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}