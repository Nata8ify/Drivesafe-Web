<%-- 
    Document   : setting
    Created on : Apr 14, 2017, 12:52:06 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="includes/bslibraries.jsp"/>
        <link href="css/sb-admin.css" rel="stylesheet">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>หน้าตั้งค่า</title>
    </head>
    <body class="fixed-nav" id="page-top">
        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
            <a class="navbar-brand" href="#">Weeworh</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav navbar-sidenav">
                    <li class="nav-item active" data-toggle="tooltip" data-placement="right" title="Dashboard">
                        <a class="nav-link" href="#">
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
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Setting">
                        <a class="nav-link" href="To?opt=sett">
                            <i class="fa fa-fw fa-cog"></i>
                            <span class="nav-link-text">
                                Setting</span>
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav sidenav-toggler">
                    <li class="nav-item">
                        <a class="nav-link text-center" id="sidenavToggler">
                            <i class="fa fa-fw fa-angle-left" id="i-collapse-menu"></i>
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">

                    <li class="nav-item">
                        <a class="nav-link" href="Logout">
                            <i class="fa fa-fw fa-sign-out"></i>
                            ลงชื่อออก</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <a class="navbar-brand">ตั้งค่าเว็บไซต์ Weeworh</a>
            </div>
        </nav>
        <br/><br/><br/><br/><br/><br/>
    <center>
        <div id="spec-location-section">
            <div class="container">

                <div class="row">
                    <div class="col-sm-12"><i id="callback-msg"></i></div>
                </div>
                <div class="row well">
                    <div class="col-sm-8">
                        <div id="spec-location-map" style="width: 100%; height: 600px; padding: 30px;" ></div>
                    </div>
                    <div class="col-sm-4">
                        <fieldset>
                            <input type="text" value="ชื่อสถานที่" <br/>
                            <input placeholder="โปรดใส่ชื่อสถานที่ (เช่น สถานีรถไฟหัวลำโพง)" id="spec-location-input" placeholder="Enter the place" class="form-control"/>
                            <hr/>
                            <label value="ละติจูด" style="float: left"></label><br/>
                            <input type="number" id="spec-location-lat-input" placeholder="โปรดใส่ละติจูด" required="" value="${param.lat}" class="form-control"/><br/>
                            <label value="ลองจิจูด" style="float: left"></label><br/>
                            <input type="number" id="spec-location-lng-input" placeholder="โปรดใส่ลองจิจูด" required="" value="${param.lng}" class="form-control"/><br/>
                            <label value="ระยะทำการรอง (กิโลเมตร)" style="float: left"></label><br/>
                            <input type="number" id="spec-location-boundrds-input" placeholder="โปรดใส่ระยะทำการรอง" required="" value="${param.bound}" class="form-control"/><br/>
                            <label value="ระยะทำการหลัก (กิโลเมตร)" style="float: left"></label><br/>
                            <input type="number" id="spec-location-mboundrds-input" placeholder="โปรดใส่ระยะทำการหลัก" value="${param.mainBound}" class="form-control"/><br/>
                            <div class="row">
                                <!--<div class="col-sm-3"><input type="button" id="spec-location-submit" value="Submit" class="btn btn-success" style="width: 100%" /><br/><br/></div>-->
                                <div class="col-sm-6"><input type="button" id="update-location-submit" value="ตั้งค่าศูนย์ปฏิบัติการ" class="btn btn-primary" style="width: 100%"/><br/><br/></div>
                                <div class="col-sm-6"><input type="button" id="getcur-location-submit" value="ดึงค่าสถานที่ปัจจุบัน" class="btn btn-default" style="width: 100%"/><br/><br/></div>
                            </div>
                            <hr/>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </center>

    <script>
        <jsp:include page="monitor_js/setting.js" flush="true"/>
    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&libraries=places&callback=initMap">
    </script>
</body>
</html>
