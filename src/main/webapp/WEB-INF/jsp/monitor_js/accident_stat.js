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
var SEVLT_STATOPT_CRASH_SPEED_ALL = "statSpeedDetected";
var SEVLT_STATOPT_FREQ_BY_TYPE = "Statistic?opt=statAccFreqByType";
/* Initialize Section */
var incidentSeries = [];
$.when($.getJSON({url: "Statistic?opt=" + SEVLT_STATOPT_SPEC_WEEK_PERIOD})).done(function (json) {
    $.each(json, function (index, element) {
        console.log(element);
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
    var data = {
        // A labels array that can contain any sort of values
        labels: labelsDate,
        // Our series array that contains series objects or in this case series data arrays
        series: [
            seriesAccTimes
        ]
    };
    postAccidentStatChart(data);
});
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

/* End-Initialize Section */

/* Event Listener */
$('document').ready(function(){
//    $('#input-b-date').val(moment(new Date()).subtract(7,'d').format("YYYY-MM-DD"));
//    $('#input-e-date').val(moment(new Date()).format("YYYY-MM-DD"));
//    prepareNumIncidentData(true,false,false,false,false);
});

$('#input-b-date, #input-e-date').change(function () {
    var now = moment(new Date()).format("YYYY-MM-DD");
    var beginInputDate = $('#input-b-date').val();
    var endInputDate = $('#input-e-date').val();
    if (beginInputDate < endInputDate && endInputDate <= now) {
        prepareNumAccidentData(SEVLT_STATOPT_SPEC_PERIOD, null);
    } else if (endInputDate < beginInputDate) {
        $('#alrt-ip-msg').html("Begin Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")");
        $('#alrt-invalid-period').prop("hidden", false);
        $('#input-b-date').val(beginDate);
    } else {
        $('#alrt-ip-msg').html("End Date shouldn't be after the present date. (" + moment(new Date()).format("YYYY-MM-DD") + ")");
        $('#alrt-invalid-period').prop("hidden", false);
        $('#input-e-date').val(endDate);
    }
});
/* By False Case */
$('#acc-opt-normalacc').click(function () {
    prepareNumAccidentData(SEVLT_STATOPT_SPEC_PERIOD, null);
});
$('#acc-opt-false').click(function () {
    prepareNumAccidentData(SEVLT_STATOPT_FALSE_ALL, null);
});
$('#acc-opt-sysfalse').click(function () {
    prepareNumAccidentData(SEVLT_STATOPT_FALSE_SYSTEM, null);
});
$('#acc-opt-usrfalse').click(function () {
    prepareNumAccidentData(SEVLT_STATOPT_FLASE_USER, null);
});
/* By Incicdent Type */

/** Function **/
/* Post to Page section */
var nAccidentChart;
var state = false;
function postAccidentStatChart(data) {
    setTimeout(function () {
        nAccidentChart = new Chartist.Line('#acc-stat-chart', data, {
            axisY: {
                type: Chartist.AutoScaleAxis,
                onlyInteger: true
            },
            axisX: {
                labelInterpolationFnc: function (value) {
                    return moment(value).format('MMM D');
                }}
        }, {
            low: 0
        });
        if (state === false) {
            animate(); //Just do animete only first time.
            state = true;
        }
    }, 500);
//    postCrashSpeedStatisticChart(); //<-- Post crash speed stat after postAccidentStatChart job is done.
}

var markers = [];
function postTotalAccidentGeoMap() {
    setTimeout(function () {
        $.when($.getJSON("Statistic?opt=" + SEVLT_STATOPT_ACCGEO_WEEK)).done(function (json) {
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

function postByDatePeriodAccidentGeoMap() {
    setTimeout(function () {// $.when..done() implemented. //14:05 16-04-2017
        $.when($.getJSON("Statistic?opt=" + SEVLT_STATOPT_ACCGEO_PERIOD, {
            bDate: $('#input-b-date').val(),
            eDate: $('#input-e-date').val()})).done(function (json) {
            emptyMarker();
            setMarker(json);
        });
    }, 500); //Wait by 500ms for the number of accident stat chart job is done to avoid illegalexception.  
}

var nCrashSpeedChart;
var CHART_SCATTER_SETTING = {
    low: 0,
    showLine: false
};
function postCrashSpeedStatisticChart() {
    $.when($.getJSON("Statistic?opt=" + SEVLT_STATOPT_CRASH_SPEED_ALL)).done(function (json) {
        var labelSpeed = [];
        var seriesAmount = [];
        $.each(json, function (index, element) {
            labelSpeed.push(element);
            seriesAmount.push(index);
        });
        nCrashSpeedChart = new Chartist.Line('#crashspeed-stat-chart', {
            labels: labelSpeed,
            series: [
                seriesAmount
            ]
        }, CHART_SCATTER_SETTING);
    });
}

function prepareNumAccidentData(opt, params) {
    labelsDate = [];
    seriesAccTimes = [];
    $.when($.getJSON({url: "Statistic?opt=" + opt}, {
        bDate: $('#input-b-date').val(),
        eDate: $('#input-e-date').val()
    })).done(function (json) {
        $.each(json, function (index, element) {
            labelsDate.push(index);
            seriesAccTimes.push(element);
            if (beginDate === undefined) {
                beginDate = index;
            } else {
                endDate = index;
            }
        });
        postByDatePeriodAccidentGeoMap();
        postAccidentStatChart({
            labels: labelsDate,
            series: [
                seriesAccTimes
            ]
        });
        //Hide Alert.
        $('#alrt-invalid-period').fadeOut();
    });
    $('#acc-period-title').html("(" + $('#input-b-date').val() + " To " + $('#input-e-date').val() + ")");
    setTimeout(function () {

    }
    , 500);
}

var NUM_STAT_TYPE_CRASH_SERV = "Statistic?opt=statSpecPeriodCrash";
var NUM_STAT_TYPE_FIRE_SERV = "Statistic?opt=statSpecPeriodFire";
var NUM_STAT_TYPE_ANIMAL_SERV = "Statistic?opt=statSpecPeriodAnimal";
var NUM_STAT_TYPE_PATIENT_SERV = "Statistic?opt=statSpecPeriodPatient";
var NUM_STAT_TYPE_OTHER_SERV = "Statistic?opt=statSpecPeriodOther";
var optionCount = 0;
function prepareNumIncidentData(isIncludeCrash, isIncludeFire, isIncludeAnimal, isIncludePatient, isIncludeOther) {
    console.log(isIncludeCrash+" : "+isIncludeFire+" : "+isIncludeAnimal+" : "+isIncludePatient+" : "+isIncludeOther);
    incidentSeries = [];
    if (isIncludeCrash) {
        addSeries(incidentSeries, NUM_STAT_TYPE_CRASH_SERV);
    }
    if (isIncludeFire) {
        addSeries(incidentSeries, NUM_STAT_TYPE_FIRE_SERV);
    }
    if (isIncludeAnimal) {
        addSeries(incidentSeries, NUM_STAT_TYPE_ANIMAL_SERV);
    }
    if (isIncludePatient) {
        addSeries(incidentSeries, NUM_STAT_TYPE_PATIENT_SERV);
    }
    if (isIncludeOther) {
        addSeries(incidentSeries, NUM_STAT_TYPE_OTHER_SERV);
    }
    var data = {
        labels: labelsDate,
        series: incidentSeries
        
    };
    postAccidentStatChart(data);
}

function addSeries(incidentSeries, url) {
    var tempSeries = [];
    labelsDate = [];
    $.when($.getJSON({url: url}, {
        bDate: $('#input-b-date').val(),
        eDate: $('#input-e-date').val()
    })).done(function (json) {
        $.each(json, function (index, element) {
            labelsDate.push(index);
            tempSeries.push(element);
            if (beginDate === undefined) {
                beginDate = index;
            } else {
                endDate = index;
            }
        });
        incidentSeries.push(tempSeries);
    });
}

/*Utilities Function*/
function emptyMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
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
            draggable: false,
            icon: markerIcon(element.accType)
        });
        markers.push(marker);
    });
}

function markerIcon(accType){
    switch(accType){
        case 1 :
            return "image/markeracctype/1.png";
        case 2 :
            return "image/markeracctype/2.png";
        case 3 :
            return "image/markeracctype/3.png";
        case 4 :
            return "image/markeracctype/4.png";
        case 5 :
            return "image/markeracctype/5.png";
        default :
            return "image/markeracctype/99.png";
    }
}

/* Chartist.js Properties*/
var seq = 0, delays = 80, durations = 500;
// Once the chart is fully created we reset the sequence
function  animate() {
    nAccidentChart.on('created', function () {
        seq = 0;
    });
// On each drawn element by Chartist we use the Chartist.Svg API to trigger SMIL animations
    nAccidentChart.on('draw', function (data) {
        seq++;
        if (data.type === 'line') {
            // If the drawn element is a line we do a simple opacity fade in. This could also be achieved using CSS3 animations.
            data.element.animate({
                opacity: {
                    // The delay when we like to start the animation
                    begin: seq * delays + 1000,
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
        } else if (data.type === 'point') {
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
        } else if (data.type === 'grid') {
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
    ;
}



//----- statAccFreqByType

//----- statAccFreqByType
//var crashSeries = [];
//var fireSeries = [];
//var animalSeries = [];
//var brawlSeries = [];
//var patientSeries = [];
//var otherSeries = [];
//$.ajax({
//    "url": SEVLT_STATOPT_FREQ_BY_TYPE,
//    "data": {bDate: "2017-10-31", eDate: "2017-11-05"},
//    "success": function (result) {
//        var bDate = moment("2017-10-31");
//        var eDate = moment("2017-11-05");
////        var bDate = moment($('#input-b-date').val());
////        var eDate = moment($('#input-e-date').val());
//        console.log(bDate);
//        console.log(eDate);
//        var series = JSON.parse(result);
//        for(var itrDate = moment(bDate) ; itrDate.diff(eDate, 'days') <= 0 ; itrDate.add(1, 'day')){
//            console.log("itrDate");
//        }
//        $.each(series, function (index, value) {
//            if (itrDate !== value.date || index === 0) {
//                itrDate = value.date;
//                crashSeries.push(0);
//                fireSeries.push(0);
//                animalSeries.push(0);
//                brawlSeries.push(0);
//                patientSeries.push(0);
//                otherSeries.push(0);
//            }
//            switch (value.accType) {
//                case 1 :
//                    crashSeries[index] = value.freqs;
//                    break;
//                case 2 :
//                    fireSeries[index] = value.freqs;
//                    break;
//                case 3 :
//                    brawlSeries[index] = value.freqs;
//                    break;
//                case 4 :
//                    animalSeries[index] = value.freqs;
//                    break;
//                case 5 :
//                    patientSeries[index] = value.freqs;
//                    break;
//                case 99 :
//                    otherSeries[index] = value.freqs;
//                    break;
//            }
//        });
//        console.log(crashSeries);
//        console.log(fireSeries);
//        console.log(animalSeries);
//        console.log(brawlSeries);
//        console.log(patientSeries);
//        console.log(otherSeries);
//    }
//});

/* Other */

/* Other */
//function log(str) {
//    console.log(str);
//}

//function callbackMessage(str) {
//    $('#callback-msg').html(str);
//}