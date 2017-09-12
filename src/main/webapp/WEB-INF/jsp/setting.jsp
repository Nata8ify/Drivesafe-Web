<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <title> ตั้งค่า </title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <meta name="description" content="">
        <meta name="author" content="">
        

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/sb-admin.css" rel="stylesheet">
        
        
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>


    </head>
    <body class="fixed-nav" id="page-top">

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
            <a class="navbar-brand" href="#">Weeworh</a>
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



        
    <center>
        <div class="content-wrapper py-3">
            <div class="container-fluid">
                <div class="card-header">
                    <legend> กำหนดจุดศุนย์กลางของหน่วยกู้ภัย </legend> 
                   
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-sm-8">
                            <div id="spec-location-map" style="width: 100%; height: 600px; padding: 30px;" ></div>
                        </div>
                        
                        
                        <div class="col-sm-4">
                            <br/>
                            <fieldset>
                                <p style="float: left"> ชื่อสถานที่ </p><br/>
                                <input placeholder="โปรดใส่ชื่อสถานที่ (เช่น สถานีรถไฟหัวลำโพง)" id="spec-location-input" placeholder="Enter the place" class="form-control"/><br/>                          
                                <p style="float: left"> ละติจูด </p><br/>
                                <input type="number" id="spec-location-lat-input" placeholder="โปรดใส่ละติจูด" required="" value="${param.lat}" class="form-control"/><br/>
                                <p style="float: left"> ลองจิจูด </p><br/>
                                <input type="number" id="spec-location-lng-input" placeholder="โปรดใส่ลองจิจูด" required="" value="${param.lng}" class="form-control"/><br/>         
                                <p style="float: left">ระยะทำการหลัก (กิโลเมตร)</p><br/>
                                <input type="number" id="spec-location-mboundrds-input" placeholder="โปรดใส่ระยะทำการหลัก" value="${param.mainBound}" class="form-control"/><br/>
                                <p style="float: left">ระยะทำการรอง (กิโลเมตร)</p><br/>
                                <input type="number" id="spec-location-boundrds-input" placeholder="โปรดใส่ระยะทำการรอง" required="" value="${param.bound}" class="form-control"/><br/>
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
