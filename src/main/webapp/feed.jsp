<%-- 
    Document   : feed
    Created on : Aug 25, 2017, 11:14:07 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Feed</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <script src="https://momentjs.com/downloads/moment.js" type="text/javascript"></script>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
            <a class="navbar-brand" href="To?opt=main">Weeworh</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav navbar-sidenav">
                    <li class="nav-item active" data-toggle="tooltip" data-placement="right" title="Dashboard">
                        <a class="nav-link" href="To?opt=main">
                            <i class="fa fa-fw fa-dashboard"></i>
                            <span class="nav-link-text">
                                Dashboard</span>
                        </a>
                    </li> 
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
                        <a class="nav-link" href="To?opt=dayReport">
                            <i class="fa fa-fw fa-table"></i>
                            <span class="nav-link-text">
                                Day Report</span>
                        </a>
                    </li>
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
                        <a class="nav-link" href="To?opt=stat">
                            <i class="fa fa-fw fa-area-chart"></i>
                            <span class="nav-link-text">
                                Charts</span>
                        </a>
                    </li>
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
                        <a class="nav-link" href="To?opt=sett">
                            <i class="fa fa-fw fa-cog"></i>
                            <span class="nav-link-text">
                                Setting</span>
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">

                    <li class="nav-item">
                        <a class="nav-link" href="Logout">
                            <i class="fa fa-fw fa-sign-out"></i>
                            Logout</a>
                    </li>
                </ul>
            </div>
        </nav><br/><br/><br/><br/>
        <!--Feed Content-->
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <input type="date" id="input-feed-date" class="form-control" style="margin-bottom: 10px;"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <!-- Example Notifications Card -->
                    <div class="card mb-3">
                        <div class="list-group list-group-flush small">
                            <div id='append-feed'></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <nav class="navbar navbar-default navbar-fixed-bottom" role="navigation-demo" id="demo-navbar-bottom">
            <div class="container">

            </div>
        </nav>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/popper/popper.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
        <script>

            $("document").ready(function () {
                $("#input-feed-date").val(moment().format("YYYY-MM-DD"));
            });

            /* If Date is Changed */
            $("#input-feed-date").change(function () {
                var date = $(this).val();
                if (moment(date, "YYYY-MM-DD").isAfter(moment())) {
                    alert("ไม่พบเหตุการณ์สำหรับอนาคต");
                    return;
                }
                getFeeds(date);
            });

            /* Feed Here (Below)  */
            var feeds;
            setTimeout(function () {
                getFeeds(moment().format("YYYY-MM-DD"));
                //setInterval(getFeeds, 10000);
            }, 10);
            function getFeeds(date) {
                feeds = [];
                $.ajax({
                    url: "DashboardFeed?opt=get_fordate",
                    data: {date: date},
                    success: function (resultFeeds) {
                        console.log(resultFeeds);
                        if (resultFeeds === "null") {
                            $("#append-feed").empty();
                            feedContent = $("<span href='#' class='list-group-item list-group-item-action'><div class='media'>ไม่พบเหตุการณ์ในวันที่ระบุ ("+moment(date).locale("th").format('DD-MM-YYYY')+")</div></span>");
                            $("#append-feed").append(feedContent);
                            return;
                        }
                        feeds = JSON.parse(resultFeeds);
                        var feedBodyMessage;
                        var feedContent;
                        $.each(feeds, function (index, feed) {
                            $("#append-feed").empty();
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
                                                feedBodyMessage = " กำลังเดินทางไปช่วยที่ ".concat(place);
                                                break;
                                            case "R" :
                                                feedBodyMessage = " กำลังช่วยเหลือผู้ประสบภัยที่ ".concat(place);
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
                                    feedContent = $("<span href='#' class='list-group-item list-group-item-action'><div class='media'><img class='d-flex mr-3 rounded-circle' src='image/color/" + feed.updatedAccCode.toLowerCase() + ".PNG' width='50px' alt=''><div class='media-body'><strong>" + ((feed.updatedAccCode === 'A' || feed.updatedAccCode === 'U') ? feed.reporterName : feed.rscrName) + "</strong>" + feedBodyMessage + "<div class='text-muted smaller'>" + feed.timestamp + "</div></div></div> </span>");

                                    $("#append-feed").append(feedContent);
                                }
                            });
                        });
                    }
                });
            }
        </script>
    </body>
</html>
