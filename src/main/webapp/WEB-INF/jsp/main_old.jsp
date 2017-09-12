<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.senior.g40.model.Profile"%>

<% Profile pf = (Profile) request.getSession(true).getAttribute("pf");
    if (pf != null) {
        pageContext.setAttribute("pf", pf);
    } else {
        response.sendRedirect("To?opt=index");
    }
%>
<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>  ตารางอุบัติเหตุ  </title>

        <script src="https://code.jquery.com/jquery-3.2.0.min.js" integrity="sha256-JAW99MJVpJBGcbzEuXk4Az05s/XyDdBomFqNlM3ic+I=" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>    

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

       
        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/sb-admin.css" rel="stylesheet">
        
        
        <link href="css/bootstrap.css" rel="stylesheet" />
        <link href="css/ct-paper.css" rel="stylesheet"/>
        <link href="css/demo.css" rel="stylesheet" /> 
        
        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <style>
            #acctable { 
                background-color: rgba(0, 0, 0, 0.03);
                cursor: pointer
            }
        </style>
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
        
      
            
        <div class="wrapper">
            <div class="landing-header section" style="background-color:#9A958D">
                <div class="container">

                    <div class="">                                                                              
                        <div>
                            <table id="acctable" style="width: 100%;"> 
                                <thead> 
                                    <tr>
                                        <th>เวลา</th>
                                        <th>ประเภท</th>
                                        <th>สถานที่เกิดอุบัติเหต</th>
                                        <th>สถานะ</th>
                                        <th>ระบบนำทาง</th>
                                    </tr>
                                </thead>
                                <tbody style="text-align: left">
                                </tbody>
                            </table>
                        </div>                 
                    </div>
                    <br/>
                </div>    
            </div>                               
            <div class="main">
                <div class="section text-center landing-section">
                    <div class="container-fluid">
                        <div class="row">
                            <br/>
                            <i id="callback-msg"></i>
                            <!--<div class="col-md-8 col-md-offset-2">-->
                            <div style="padding:0px">
                                <div id="map" style="background-color: #ddd;width: 100%;height: 500px;"></div><br/>                             
                                <br/><hr/>
                                <script>
                                    <%@include file="monitor_js/main_accnavigate.js"  %>
                                    <%@include file="monitor_js/main_accupdater.js"  %>
                                </script>
                                <script async defer
                                        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&libraries=places&callback=initMap">
                                </script>
                                <br/>
                            </div>
                        </div>
                    </div>
                </div>    
            </div>     
        </div>
        <footer class="footer-demo section-dark">
            <div class="container">
                <nav class="pull-left">

                </nav>
                <div class="copyright pull-right">

                </div>
            </div>
        </footer>
    </body>
</html>