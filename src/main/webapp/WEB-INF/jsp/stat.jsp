<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title> สถิติ </title>
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

        <!--moment.js-->
        <script src="https://momentjs.com/downloads/moment.min.js"></script>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
        <script src="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.js"></script>


    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper/popper.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    </head>
    <body class="fixed-nav" id="page-top">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
            <a class="navbar-brand" href="#">Weeworh</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav navbar-sidenav">
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
                        <a class="nav-link" href="To?opt=main">
                            <i class="fa fa-fw fa-dashboard"></i>
                            <span class="nav-link-text">
                                Dashboard</span>
                        </a>
                    </li> 
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Overview">
                        <a class="nav-link" href="To?opt=dayReport">
                            <i class="fa fa-fw fa-table"></i>
                            <span class="nav-link-text">Overview</span>
                        </a>
                    </li>
                    <li class="nav-item active" data-toggle="tooltip" data-placement="right" title="Report Statistic">
                        <a class="nav-link" href="To?opt=stat">
                            <i class="fa fa-fw fa-area-chart"></i>
                            <span class="nav-link-text">Report Statistic</span>
                        </a>
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.op != null}">
                            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Setting">
                            <center>
                                <a class="nav-link" href="To?opt=sett">
                                    <i class="fa fa-fw fa-cog"></i>
                                    <span class="nav-link-text">Setting</span>
                                </a>
                            </center>
                                
                            </li>
                        </c:when>
                    </c:choose>
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
                <!--<h3><a href="#acc-stat-chart">Number of Accident Statistic</a> <a href="#acc-stat-map">Visualizing Accident Location</a></h3><br/>-->

                <div class="card-header" >
                    <legend> ระบุช่วงเวลาการเกิดอุบัติเหตที่ต้องการ <b id="acc-period-title"></b></legend>
                </div>
                <div class="card-body" >
                    <form action="#">
                        <div class="row">
                            <div class="alert alert-danger" id="alrt-invalid-period" hidden="">
                                <strong>ท่านใส่ช่วงเวลาผิดพลาด! : </strong><span id="alrt-ip-msg"></span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3"><b style="float: left">วันที่เริ่ม : </b><input type="date" name="bDate" id="input-b-date" class="form-control"/></div>
                            <div class="col-sm-3"><b style="float: left">วันสุดท้าย : </b><input type="date" name="bDate" id="input-e-date" class="form-control"/></div>
                            <!--  <div class="col-sm-6">
                                                        <nav class="navbar navbar-collapse navbar-default" style="z-index: 1">
                                                               <div class="container-fluid">
                                                                   <ul class="nav navbar-nav ">
                                                                       <li class="active"><a href="#" ><input type="checkbox" id="acc-opt-normalacc" checked=""/> Normal</a></li>
                                                                       <li><a href="#"><input type="checkbox" id="acc-opt-false"/> False</a></li>
                                                                       <li><a href="#" ><input type="checkbox" id="acc-opt-sysfalse"/> System False</a></li>
                                                                       <li><a href="#" ><input type="checkbox" id="acc-opt-usrfalse"/> User False</a></li>
                                                                   </ul>
                                                               </div>
                                                           </nav>
                           </div>-->
                        </div>

                    </form>
                </div>
                <div class="ct-chart ct-major-twelfth" id="acc-stat-chart"></div>
                <br/>
                <div id="acc-stat-map" style="width: 100%; height: 600px; padding: 30px;"></div>
                <br/>

            </div>
        </div>
    </center>
    <script>
        <jsp:include page="monitor_js/accident_stat.js"/>
    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
    </script>
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Select "Logout" below if you are ready to end your current session.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>


    <!-- Plugin JavaScript -->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for this template -->
    <script src="js/moment.js"></script>
    <script src="js/sb-admin-stat.js"></script>
    <script>
        $("#i-collapse-menu").click();
    </script>
</body>
</html>
