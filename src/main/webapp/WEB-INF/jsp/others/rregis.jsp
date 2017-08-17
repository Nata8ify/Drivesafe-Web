<%-- 
    Document   : rregis
    Created on : May 13, 2017, 1:30:56 AM
    Author     : PNattawut
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
    <head>
        <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeeWorh</title>

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
                    if (this.hash !== "") {
                        event.preventDefault();
                        var hash = this.hash;
                        $('html, body').animate({
                            scrollTop: $(hash).offset().top
                        }, 800, function () {
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
                    <a id="head"><center> Sign Up for Rescuer/Monitoring Rescuer </center></a>
                </div>
            </div><!-- /.container-->
        </nav> 
        <div class="wrapper">                           
            <div class="main">
                <div class="section text-center landing-section">
                    <div class="container">
                        <div class="row">
                            <br/>
                            <div class="col-md-8 col-md-offset-2">
                                <div class="panel panel-info">
                                    <fieldset id = "regis" name="regis">
                                        <form action = "Signup" method = "POST">
                                            <p> Define your Username and Password for next time Sign in into the System. </p><br/>
                                            Username : <input name = "usrn" type = "text" required = "" /> <br/> <br/>
                                            Password : <input name = "pswd" type = "password"  required = ""/> <br/> <br/>
                                            <input name = "utyp" type = "hidden"  value = "T" required = ""/>
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
                                                <tr>
                                                    <td>Organization</td>
                                                    <td><select name = "organizationId" id="select-organization">
                                                            <option value = "0"> New </option>
                                                            <!-- append organizations -->
                                                        </select>
                                                        <!-- Or display new organization form if option "New" is selected -->
                                                    </td>
                                                </tr>
                                                <tr id="tr-orgnization">
                                                    <td></td>
                                                    <td><input name="organizationName" type="text" id="organization" placeholder="Organizaztion Name" />
                                                        <br/><br/>
                                                        <textarea id="organization-desc" name="organizationDescription" cols="21" rows="5" placeholder="Description"></textarea>
                                                    </td>
                                                </tr>
                                                <tr >
                                                    <td colspan="2">
                                                        <button type="submit" class="btn btn-success">Submit</button>&nbsp;&nbsp;<button type="reset" class="btn btn-warning">Reset</button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </fieldset>
                                </div>
                                <a href="#demo-navbar" class="btn btn-info btn-fill">Back to top</a> <a href="To?opt=index" class="btn btn-default btn-fill">Index</a><br/>
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
        <script>
            $("document").ready(function () {
                $.ajax({
                    "url": "Organization?opt=get_all",
                    "success": function (results) {
                        var organizations = $.parseJSON(results);
                        console.log(organizations);
                        $.each(organizations, function (index, organization) {
                            var organizationOption = $("<option value = '" + organization.organizationId + "'>" + organization.organizationName + "</option>");
                            $("#select-organization").append(organizationOption);
                        });
                    }
                });
            });
            $("#select-organization").change(function () {
                if ($(this).val() === "0") {
                    $("#tr-orgnization").prop("hidden", false);
                } else {
                    $("#tr-orgnization").prop("hidden", true);
                }
            });
        </script>
    </body>
</html>
