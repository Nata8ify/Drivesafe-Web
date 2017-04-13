<%@page import="com.senior.g40.utils.A"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% if (session.getAttribute("pf") != null) {
        response.sendRedirect("To?opt=main");
    }%>

<!doctype html>
<html lang="en">
    <head>
        <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drivesafe</title>

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/ct-paper.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/demo.css" rel="stylesheet" /> 
        <link href="${pageContext.request.contextPath}/examples.css" rel="stylesheet" /> 

        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                // Add smooth scrolling to all links
                $("a").on('click', function (event) {

                    // Make sure this.hash has a value before overriding default behavior
                    if (this.hash !== "") {
                        // Prevent default anchor click behavior
                        event.preventDefault();

                        // Store hash
                        var hash = this.hash;

                        // Using jQuery's animate() method to add smooth page scroll
                        // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
                        $('html, body').animate({
                            scrollTop: $(hash).offset().top
                        }, 800, function () {

                            // Add hash (#) to URL when done scrolling (default click behavior)
                            window.location.hash = hash;
                        });
                    } // End if
                });
            });
        </script>
    </head>
    <body>

        <nav class="navbar navbar-default "  id="demo-navbar">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->

                <div class="navbar-header text-center ">   
                    <a id="head"><center> Welcome to Drivesafe System! </center></a>
                </div>
            </div><!-- /.container-->
        </nav> 

        <div class="wrapper">
            <div class="landing-header section" style="background-image: url('image/bg_landing.jpg');">
                <div class="container">
                    <div class="motto" >
                        <div class="col-md-8 col-md-offset-2">
                            <div class="panel panel-info ">
                                <fieldset id="login">
                                    <div class="panel-heading"><legend><center>Monitoring/Super User Login ${msg}</center></legend></div>

                                    <form action="Login" method="POST">
                                        <center> Username : <input name="usrn" type="text" required=""/> <br/><br/>
                                            Password : <input name="pswd" type="password"  required=""/><br/><br/>
                                            <input name="utyp" type="hidden"  value="T" required=""/>
                                            <button type="submit" class="btn btn-success">Submit</button>&nbsp;&nbsp;<button type="reset" class="btn btn-warning">Reset</button><br/><br/>
                                            <a href="#regis">Register for Driving User</a></center> &nbsp;&nbsp;
                                    </form>

                                </fieldset>
                            </div> 
                        </div>
                        <br/>
                    </div> 
                </div>    
            </div>                               
            <div class="main">
                <div class="section text-center landing-section">
                    <div class="container">
                        <div class="row">
                            <br/><br/>
                            <div class="col-md-8 col-md-offset-2">
                                <div class="panel panel-info">
                                    <fieldset id = "regis" name="regis">
                                        <div class="panel-heading"> Driving User Sign Up </div>
                                        <form action = "Signup" method = "POST">
                                            <table class="table table-striped table-responsive" style="align-content: center">
                                                    <tr>
                                                        <td>First name : </td>
                                                        <td><input name = "fname" type = "text" required = "" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Last name  : </td>
                                                        <td><input name = "lname" type = "text"  required = ""/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Personal ID : </td>
                                                        <td><input name = "pid" type = "number" required = "" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Phone : </td>
                                                        <td><input name = "phone" type = "number"  required = ""/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Address 1 : </td>
                                                        <td><input name = "addr1" type = "text" required = ""/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Address 2 : </td>
                                                        <td><input name = "addr2" type = "text"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Age : </td>
                                                        <td><input name = "age" type = "number" required = "" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gender : </td>
                                                        <td><select name = "sex" >
                                                                <option value = "M"> Male </option>
                                                                <option value = "F"> Female </option>
                                                                <option value = "O"> None of above </option>
                                                            </select></td>
                                                    </tr>
                                                </table>
                                                
                                                
                                                 
                                                 
                                                
                                                

                                                <hr/><br/>
                                                <p> Define your Username and Password for next time Sign in into the System. </p><br/>
                                                Username : <input name = "usrn" type = "text" required = "" /> <br/> <br/>
                                                Password : <input name = "pswd" type = "password"  required = ""/> <br/> <br/>
                                                <input name = "utyp" type = "hidden"  value = "M" required = ""/>
                                                <button type="submit" class="btn btn-success">Submit</button>&nbsp;&nbsp;<button type="reset" class="btn btn-warning">Reset</button> </center><br/>
                                        </form>
                                    </fieldset>
                                </div>


                                <br/>
                                <a href="#demo-navbar" class="btn btn-info btn-fill">Back to top</a><br/><br/>
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