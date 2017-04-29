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
        <title>  Drivesafe  </title>

        <script src="https://code.jquery.com/jquery-3.2.0.min.js" integrity="sha256-JAW99MJVpJBGcbzEuXk4Az05s/XyDdBomFqNlM3ic+I=" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>    

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

        <link href="css/bootstrap.css" rel="stylesheet" />
        <link href="css/ct-paper.css" rel="stylesheet"/>
        <link href="css/demo.css" rel="stylesheet" /> 
        

        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
        <style>
            #acctable tr:hover{ 
                background-color: #FF841E;
                cursor: pointer
            }
        </style>
    </head>
    <body>

        <nav class="navbar navbar-default" role="navigation-demo" id="demo-navbar">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <a class="navbar-brand">Welcome to Drivesafe System!</a>
                <div class="collapse navbar-collapse" id="navigation-example-2">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="To?opt=stat">Open Statistics</a>
                        </li>
                        <li>
                            <a href="To?opt=sett">Setting</a>
                        </li>
                        <li>
                            <a href="Logout">Logout / Invalidate</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>         
        <div class="alert alert-danger landing-alert">
            <div class="container text-center">
                <p>Welcome :  ${pf.firstName} ${pf.lastName} </p>
            </div>
        </div>

        <div class="wrapper">
            <div class="landing-header section" style="background-image: url('image/bg_landing.jpg');">
                <div class="container">

                    <div class="col-md-8 col-md-offset-2">                                                                              
                        <div>
                            <table id="acctable" style="width: 100%;"> 
                                <thead> 
                                    <tr>
                                        
                                        <th>Time</th>
                                        <th>Latitude</th>
                                        <th>Longitude</th>
                                        <th>Accident Status</th>
                                        <th>Nearest Hospital</th> 
                                    </tr>
                                </thead>
                                <tbody style="text-align: center">
                                </tbody>
                            </table>
                        </div>                 
                    </div>
                    <br/>
                </div>    
            </div>                               
            <div class="main">
                <div class="section text-center landing-section">
                    <div class="container">
                        <div class="row">
                            <br/>
                            <i id="callback-msg"></i>
                            <div class="col-md-8 col-md-offset-2">
                                <div id="map" style="background-color: #ddd;width: 100%;height: 400px;padding-right: 30px;">
                                </div>
                                <br/><hr/>
                                <script>
                                    <%@include file="monitor_js/map_navigate.js"  %>
                                </script>
                                <script async defer
                                        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&callback=initMap">
                                </script>
                                <script>
                                    <%@include file="monitor_js/accident_updater.js"  %>
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