<%-- 
    Document   : admin_signin
    Created on : Oct 23, 2017, 11:05:13 AM
    Author     : PNattawut
--%>

<%@page import="com.senior.g40.model.Profile"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Profile admin = request.getSession(false) != null ? (Profile) request.getSession(false).getAttribute("admin") : null;
    if (admin == null) {
        response.sendRedirect("To?opt=admin");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> หน้าหลักผู้ดูแลระบบ </title>

        <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">-->
        <link rel="stylesheet" href="css/style3.css">             
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />               


    </head>
    <body>
        <%--
                    <%
                        List<Hospital> hospitals = SettingService.getInstance().getAllHospital();
                        pageContext.setAttribute("hospitals", hospitals);
                    %>
        --%>

        <header class="header-login-signup">

            <div class="header-limiter">

                <h1><a href="#"> หน้าหลักผู้ดูแลระบบ </a></h1>


                <ul>

                    <li><a href="Logout"> ลงชื่อออก </a></li>
                </ul>

            </div>



        </header>
        <div class="form-mainall">
            <div class="form-main1">
                <div class="form2">    
                    <div class="tab1">
                        <h1> การแจ้งขอถอดถอนโรงพยาบาล </h1>   
                    </div>
                </div>

                <div class="form3">
                    <table class="table-fill">
                        <thead>
                            <tr>
                                <th text-left>ชื่อโรงพยาบาล</th>
                                <th text-left>การทำงาน</th>
                            </tr>
                        </thead>
                        <tbody class="table-hover">

                            <c:choose>
                                <c:when test="${not empty flagHospitals}">
                                    <c:forEach items="${flagHospitals}" var="hospital">
                                        <tr>                              
                                            <td class="text-left">${hospital.name}</td>
                                            <td class="text-left" colspan="3">
                                                <a href="https://www.google.com/maps/search/?api=1&query=${hospital.latitude},${hospital.longitude}" target="_blank">ตรวจสอบพิกัด</a>&nbsp;
                                                <a href="Admin?opt=unflag_hospital&hospitalId=${hospital.hospitalId}">ไม่ถอดถอน</a>&nbsp;
                                                <a href="Admin?opt=del_hospital&hospitalId=${hospital.hospitalId}">ถอดถอน</a>&nbsp;
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="2">ไม่พบโรงพยาบาลในฐานข้อมูล</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

            </div>
            <div class="form-main2">
                <div class="form2">    
                    <div class="tab1">
                        <h1> แผนที่ </h1>   
                    </div>
                </div>
                <div class="form3">




                </div>
            </div>
        </div>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCbVqCqiShDFum-nR8q4aWKDtjYw-w8Hs&libraries=places&callback=initMap">
        </script>
    </body>
</html>
