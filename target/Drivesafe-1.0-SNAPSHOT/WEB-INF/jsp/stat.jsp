<%-- 
    Document   : stat
    Created on : Mar 11, 2017, 12:45:22 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
        <script src="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.js"></script>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Statistic Page</title>
    </head>
    <body>
        <h3><a href="#acc-stat-chart">Number of Accident Statistic</a> <a href="#acc-stat-map">Visualizing Accident Location</a></h3><br/>
        <h3 id="nAccStatTitle">Number of Accident Statistic</h3>
        <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
        <hr/>
        <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>

        <script>
            //Do Number of Accident Chart.
            var labelsDate = [];
            var seriesAccTimes = [];
            var beginDate;
            var lastDate;
            var statTotalAcc = $.getJSON({url: "Statistic?opt=statTotalAcc"}).done(function (json) {
                $.each(json, function (index, element) {
                    labelsDate.push(index);
                    seriesAccTimes.push(element);
                    if (beginDate === undefined) {
                        beginDate = index;
                    } else {
                        lastDate = index;
                    }
                })
                $('#nAccStatTitle').append(" [" + beginDate + " to " + lastDate + "]");
            });
            var data = {
                // A labels array that can contain any sort of values
                labels: labelsDate,
                // Our series array that contains series objects or in this case series data arrays
                series: [
                    seriesAccTimes
                ]
            };
            setTimeout(function () {
                new Chartist.Line('.ct-chart', data, {
                    axisY: {
                        type: Chartist.AutoScaleAxis,
                        onlyInteger: true
                    }
                });
            }, 500);
        </script>        
        <script>
            //Do Geo Accident Map Stat
            var nAccStatMap;
            var NEARSIT_LATLNG = {lat: 13.652277, lng: 100.494457};
            function initMap() {
                nAccStatMap = new google.maps.Map(document.getElementById('acc-stat-map'), {
                    zoom: 10,
                    center: NEARSIT_LATLNG
                });
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
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>

    </body>
</html>
