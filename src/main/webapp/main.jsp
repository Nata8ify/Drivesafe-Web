<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.senior.g40.model.Profile"%>

<% Profile pf = (Profile) request.getSession(true).getAttribute("pf");
    if (pf != null) {
        pageContext.setAttribute("pf", pf);
    } else {
        response.sendRedirect("To?opt=index");
    }
%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <meta name="description" content="">
        <meta name="author" content="">
        <title>Dashboard - Weeworh</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <style>
            #dataTable tr:hover{
                cursor: pointer;
            }
        </style>
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
                    <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
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

        <div class="content-wrapper py-3">

            <div class="container-fluid">
                <!-- Icon Cards -->
                <div class="row">
                    <div class="col-xl-7 col-sm-6 mb-7" >
                        <div id="map" style="background-color: #ddd;width: 100%;height: 500px;" class="img img-thumbnail"></div>
                    </div>
                    <div class="col-xl-5 col-sm-6 mb-5">
                        <div class="row">
                            <div class="col-sm-12" style="align-content: center; padding: 10px">
                                <iframe src="http://free.timeanddate.com/clock/i5v17a87/n28/tlec/fs30/tt0/tw0/tb2" frameborder="0" width="100%" height="38"></iframe>
                            </div>
                        </div>
                        <!-- Example Tables Card -->
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fa fa-table"></i>
                                ภาพรวมของการเกิดอุบัติเหต
                            </div>
                            <div class="table-responsive">
                                <table class="table table-bordered" width="100%" id="dataTable" cellspacing="0">
                                    <thead style="display: none;">
                                        <tr>
                                            <th>เวลา</th>
                                            <th>ประเภทของอุบัติเหต</th>
                                            <th>สถานที่เกิด</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td colspan="3">กำลังโหลดข้อมูล กรุณารอสักครู่</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="card-footer small text-muted" id="acctable-lastest-update">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-8">
                        <!-- Example Notifications Card -->
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fa fa-bell-o"></i>
                                เหตุการณ์ต่างๆที่เกิดขึ้น
                            </div>
                            <div class="list-group list-group-flush small">
                                <div id='append-feed'></div>
                                <a href="To?opt=feed" class="list-group-item list-group-item-action">
                                    ดูเหตุการณ์ที่เกิดขึ้นทั้งหมด
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <!-- Example Pie Chart Card -->
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fa fa-pie-chart"></i>
                                แผนภูมิวงกลมแสดงสถานะของอุบัติเหตภายในวันนี้
                            </div>
                            <div class="card-body">
                                <canvas id="myPieChart" width="100%" height="100"></canvas>
                            </div>
                            <div class="card-footer small text-muted" id="acccode-lastest-update">

                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <!-- Area Chart Example -->
            <div class="card mb-3">
                <div class="card-header">
                    <i class="fa fa-area-chart"></i>
                    แผนภูมิสถิติแสดงจำนวนของอุบัติเหตภายในวันนี้
                </div>
                <div class="card-body">
                    <canvas id="myBarChart" width="100%" height="15"></canvas>
                </div>
                <div class="card-footer small text-muted" id="history-report-lastest-update">

                </div>
            </div>



        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /.content-wrapper -->

    <!-- Scroll to Top Button -->
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

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper/popper.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for this template -->
    <script src="js/moment.js"></script>
    <script src="dashboard_js/main_accnavigate.js"></script>
    <script src="dashboard_js/main_accupdater.js"></script>
    <script src="js/sb-admin.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&libraries=places&callback=initMap">
    </script>

    <script>
        $("#i-collapse-menu").click();
    </script>
</body>

</html>
