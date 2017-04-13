//Do Number of Accident Chart.
var labelsDate = [];
var seriesAccTimes = [];
var beginDate;
var endDate;


var SEVLT_STATOPT_SPEC_PERIOD = "statSpecPeriodAcc";
var SEVLT_STATOPT_FLASE_USER = "statUserFalseAcc";
var SEVLT_STATOPT_FALSE_SYSTEM = "statSysFalseAcc";
var SEVLT_STATOPT_FALSE_ALL = "statFalseAcc";
var SEVLT_STATOPT_ACCGEO_WEEK = "statWeekAccGeo";
var SEVLT_STATOPT_ACCGEO_PERIOD = "statPeriodAccGeo";
var SEVLT_STATOPT_SPEC_WEEK_PERIOD = "statWeekendAcc";

/* Initialize Section */
var nAccidentChart;
var isFirstSetup = true;
$.getJSON({url: "Statistic?opt=" + SEVLT_STATOPT_SPEC_WEEK_PERIOD}).done(function (json) {
    $.each(json, function (index, element) {
        labelsDate.push(index);
        seriesAccTimes.push(element);
        if (beginDate === undefined) {
            beginDate = index;
        } else {
            endDate = index;
        }
    });
    $('#acc-period-title').html(" [" + beginDate + " To " + endDate + " (Week)]");
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

var opt = "";
function initMap() {
    nAccStatMap = new google.maps.Map(document.getElementById('acc-stat-map'), {
        zoom: 9,
        center: NEARSIT_LATLNG
    });
    postTotalAccidentGeoMap();
}

/* Event Listener */
$('#input-b-date, #input-e-date').change(function () {
    var now = moment(new Date()).format("YYYY-MM-DD");
    var beginInputDate = $('#input-b-date').val();
    var endInputDate = $('#input-e-date').val();
    if (beginInputDate < endInputDate & endInputDate <= now) {
        prepareNumAccidentData(SEVLT_STATOPT_SPEC_PERIOD, null);
    } else if (endInputDate < beginInputDate) {
        $('#alrt-ip-msg').html("Begin Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")");
        $('#alrt-invalid-period').fadeIn();
        $('#input-b-date').val(beginDate);
    } else {
        $('#alrt-ip-msg').html("End Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")");
        $('#alrt-invalid-period').fadeIn();
        $('#input-e-date').val(endDate);
    }
});

$('#acc-opt-normalacc').click(function () {
    if ($('#acc-opt-normalacc').is(':checked')) {
        prepareNumAccidentData(SEVLT_STATOPT_SPEC_PERIOD, null);
    } else {

    }
});

$('#acc-opt-false').click(function () {
    if ($('#acc-opt-false').is(':checked')) {
        prepareNumAccidentData(SEVLT_STATOPT_FALSE_ALL, null);
    } else {

    }

});

$('#acc-opt-sysfalse').click(function () {
    if ($('#acc-opt-sysfalse').is(':checked')) {
        prepareNumAccidentData(SEVLT_STATOPT_FALSE_SYSTEM, null);
    } else {

    }
});

$('#acc-opt-usrfalse').click(function () {
    if ($('#acc-opt-usrfalse').is(':checked')) {
        prepareNumAccidentData(SEVLT_STATOPT_FLASE_USER, null);
    } else {

    }
});

/** Function **/
/* Post to Page section */
function postAccidentStatChart(data) {
    setTimeout(function () {
        nAccidentChart = new Chartist.Line('.ct-chart', data, {
            axisY: {
                type: Chartist.AutoScaleAxis,
                onlyInteger: true
            },
            axisX: {
                labelInterpolationFnc: function (value) {
                    return moment(value).format('MMM D');
                }},
            lineSmooth: false
        });
        if (isFirstSetup === true) {
            animate(true, 500);
            isFirstSetup = false;
        } else {
            animate(false, 0);
        }
    }, 100);
}

var markers = [];
function postTotalAccidentGeoMap() {
    setTimeout(function () {
        $.getJSON("Statistic?opt=" + SEVLT_STATOPT_ACCGEO_WEEK).done(function (json) {
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

function postByDatePeriodAccidentGeoMap() {
    setTimeout(function () {
        $.getJSON("Statistic?opt=" + SEVLT_STATOPT_ACCGEO_PERIOD, {
            bDate: $('#input-b-date').val(),
            eDate: $('#input-e-date').val()}).done(function (json) {
            emptyMarker();
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

function prepareNumAccidentData(opt, params) {
    labelsDate = [];
    seriesAccTimes = [];
    $.getJSON({url: "Statistic?opt=" + opt}, {
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
            map: nAccStatMap,
            draggable: false
        });
        markers.push(marker);
    });
}

/* Chartist.js Properties*/

// Once the chart is fully created we reset the sequence
function  animate(isFromBegin, plotDaley) {
    var seq = 0, delays = 80, durations = 500;
    nAccidentChart.on('created', function () {
        seq = 0;
    });

// On each drawn element by Chartist we use the Chartist.Svg API to trigger SMIL animations
    nAccidentChart.on('draw', function (data) {
        seq++;
        if (!isFromBegin) {
            delays = 0;
        }
        if (data.type === 'line') {
            // If the drawn element is a line we do a simple opacity fade in. This could also be achieved using CSS3 animations.

            data.element.animate({

                opacity: {
                    // The delay when we like to start the animation
                    begin: seq * delays + plotDaley,
                    // Duration of the animation
                    dur: durations,
                    // The value where the animation should start
                    from: 0,
                    // The value where it should end
                    to: 1
                }
            });
        } else if (data.type === 'label' && data.axis === 'x') {
            data.element.animate({
                y: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.y + 100,
                    to: data.y,
                    // We can specify an easing function from Chartist.Svg.Easing
                    easing: 'easeOutQuart'
                }
            });
        } else if (data.type === 'label' && data.axis === 'y') {
            data.element.animate({
                x: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 100,
                    to: data.x,
                    easing: 'easeOutQuart'
                }
            });
        } else if (data.type === 'point' && isFromBegin) {
            data.element.animate({
                x1: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                x2: {
                    begin: seq * delays,
                    dur: durations,
                    from: data.x - 10,
                    to: data.x,
                    easing: 'easeOutQuart'
                },
                opacity: {
                    begin: seq * delays,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                }
            });
        } else if (data.type === 'grid' && isFromBegin) {
            // Using data.axis we get x or y which we can use to construct our animation definition objects
            var pos1Animation = {
                begin: seq * delays,
                dur: durations,
                from: data[data.axis.units.pos + '1'] - 30,
                to: data[data.axis.units.pos + '1'],
                easing: 'easeOutQuart'
            };

            var pos2Animation = {
                begin: seq * delays,
                dur: durations,
                from: data[data.axis.units.pos + '2'] - 100,
                to: data[data.axis.units.pos + '2'],
                easing: 'easeOutQuart'
            };

            var animations = {};
            animations[data.axis.units.pos + '1'] = pos1Animation;
            animations[data.axis.units.pos + '2'] = pos2Animation;
            animations['opacity'] = {
                begin: seq * delays,
                dur: durations,
                from: 0,
                to: 1,
                easing: 'easeOutQuart'
            };

            data.element.animate(animations);
        }
    });
}