<%-- 
    Document   : stat
    Created on : Mar 11, 2017, 12:45:22 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/sb-admin.css" rel="stylesheet">
        
        
        <jsp:include page="includes/bslibraries.jsp"/>
        <!--moment.js-->
        <script src="https://momentjs.com/downloads/moment.min.js"></script>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
        <script src="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.js"></script>
        
        <title>หน้าสถิติ</title>
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
                        <a class="nav-link" href="#To?opt=main">
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
          
          
          
        <div class="container">
            <!--<h3><a href="#acc-stat-chart">Number of Accident Statistic</a> <a href="#acc-stat-map">Visualizing Accident Location</a></h3><br/>-->
            <br/><br/><br/><br/>
            <div class="well" >
                <legend>โปรดระบุช่วงเวลาการเกิดอุบัติเหตที่ท่านต้องการ <b id="acc-period-title"></b></legend>
                <form action="#">
                    <div class="row">
                        <div class="alert alert-danger" id="alrt-invalid-period" hidden="">
                            <strong>ท่านใส่ช่วงเวลาผิดพลาด! : </strong><span id="alrt-ip-msg"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3"><b>วันที่เริ่ม : </b><input type="date" name="bDate" id="input-b-date" class="form-control"/></div>
                        <div class="col-sm-3"><b>วันสุดท้าย </b><input type="date" name="bDate" id="input-e-date" class="form-control"/></div>
                        <div class="col-sm-6">
                            <!--                            <nav class="navbar navbar-collapse navbar-default" style="z-index: 1">
                                                            <div class="container-fluid">
                                                                <ul class="nav navbar-nav ">
                                                                    <li class="active"><a href="#" ><input type="checkbox" id="acc-opt-normalacc" checked=""/> Normal</a></li>
                                                                    <li><a href="#"><input type="checkbox" id="acc-opt-false"/> False</a></li>
                                                                    <li><a href="#" ><input type="checkbox" id="acc-opt-sysfalse"/> System False</a></li>
                                                                    <li><a href="#" ><input type="checkbox" id="acc-opt-usrfalse"/> User False</a></li>
                                                                </ul>
                                                            </div>
                                                        </nav>-->
                        </div>
                    </div>

                </form>
                </fieldset>
                <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
                <hr/>
                <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>
                <br/>

            </div>
            <script>
                <jsp:include page="monitor_js/accident_stat.js"/>
            </script>
            <script async defer
                    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
            </script>

    </body>
</html>
