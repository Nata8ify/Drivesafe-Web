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
    var incidentTable;
    $(document).ready(function () {
        incidentTable =$('#dataTable').DataTable({
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
            "fnRowCallback": function (nRow, aData) {
                console.log(aData);
                var accCodeText = aData.accCode; // ID is returned by the server as part of the data
                var $nRow = $(nRow); // cache the row wrapped up in jQuery
                // alert(accCodeText);
                if (accCodeText === "A") {
                    $nRow.css({"background-color": "#dc3545"});
                } else if (accCodeText === "G") {
                    $nRow.css({"background-color": "#ffc107"});
                } else if (accCodeText === "R") {
                    $nRow.css({"background-color": "#007bff"});
                } else if (accCodeText === "C") {
                    $nRow.css({"background-color": "#28a745"});
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
                        $("td", nRow).eq(2).css("font-size", "12px");
                    }
                });
                return nRow;
            },
            "language": {
                "loadingRecords": "No Incident In Record... "
            },
            "searching": false,
            "bLengthChange": false,
            "pageLength": 5});
    });
    setInterval(function(){
        incidentTable.ajax.reload(null, false);
    }, 6000);

})(jQuery); // End of use strict

// Chart.js scripts
// -- Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// -- Area Chart Example
var ctx = document.getElementById("myAreaChart");
var reportFreqSeries = [];
var isGetReportFreqFirst = true;
var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ["00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"],
        datasets: [{
                label: "Sessions",
                lineTension: 0.3,
                backgroundColor: "rgba(2,117,216,0.2)",
                borderColor: "rgba(2,117,216,1)",
                pointRadius: 5,
                pointBackgroundColor: "rgba(2,117,216,1)",
                pointBorderColor: "rgba(255,255,255,0.8)",
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(2,117,216,1)",
                pointHitRadius: 20,
                pointBorderWidth: 2
            }]
    },
    options: {
        scales: {
            xAxes: [{
                    time: {
                        unit: 'hour'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 7
                    }
                }],
            yAxes: [{
                    ticks: {
                        min: 0,
                        max: 100,
                        maxTicksLimit: 5
                    },
                    gridLines: {
                        color: "rgba(0, 0, 0, .125)"
                    }
                }]
        },
        legend: {
            display: false
        }
    }
});

setInterval(buildReportFreqChart, 2500);
function buildReportFreqChart() {
    console.log("result");
    $.ajax({
        url: "Dashboard?opt=getReportFreqTimeSeries",
        data: {date: moment().format("YYYY-MM-DD")},
        success: function (result) {
            reportFreqSeries = JSON.parse(result);
//            if (isGetReportFreqFirst) {
//                isGetReportFreqFirst = false;
//            } else {
//
//            }
//            myLineChart.data.labels = [reportFreqSeries[0]];
//            myLineChart.data.datasets[0].data = [reportFreqSeries[1]];
//            myLineChart.update();
            console.log(reportFreqSeries);
        }
    });
}


// -- Pie Chart Example
var ctx = document.getElementById("myPieChart");
var myPieChart;
var codeSeries = [];
var isGetStatusFirst = true;
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
                        labels: ["Awaiting", "Going", "Rescuing", "Closed"],
                        datasets: [{
                                data: codeSeries,
                                backgroundColor: ['#dc3545', '#ffc107', '#007bff', '#28a745']
                            }]
                    }
                });
                isGetStatusFirst = false;
            } else {
                myPieChart.data.datasets[0].data = codeSeries;
                myPieChart.update();
            }
        }
    });
}
