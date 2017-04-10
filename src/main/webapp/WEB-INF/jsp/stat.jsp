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
        <fieldset>
            <legend>Please Specify a Period</legend>
            <form action="#noneednow">
                Begin  from : <input type="date" name="bDate" id="input-b-date"/> to&nbsp; 
                <input type="date" name="bDate" id="input-e-date"/>
            </form>
        </fieldset>
        <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
        <hr/>
        <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>
        <script>
            <jsp:include page="monitor_js/accident_stat.js"/>
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
        </script>

    </body>
</html>
