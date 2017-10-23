<%-- 
    Document   : admin_signin
    Created on : Oct 23, 2017, 11:05:13 AM
    Author     : PNattawut
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>หน้าหลักผู้ดูแลระบบ</title>
    </head>
    <body>
        <fieldset>
            <legend>การแจ้งขอถอดถอนโรงพยาบาล</legend>
            <table>
            <thead>
                <tr>
                    <th>ชื่อโรงพยาบาล</th>
                    <th>การทำงาน</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${flagHospitals}" var="hospital">
                    <tr>
                        <td>${hospital.name}</td>
                        <td colspan="3">
                            <a href="https://www.google.com/maps/search/?api=1&query=${hospital.latitude},${hospital.longitude}" target="_blank">ตรวจสอบพิกัด</a>&nbsp;
                            <a href="Admin?opt=unflag_hospital&hospitalId=${hospital.hospitalId}">ไม่ถอดถอน</a>&nbsp;
                            <a href="Admin?opt=del_hospital&hospitalId=${hospital.hospitalId}">ถอดถอน</a>&nbsp;
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </fieldset>
        
    </body>
</html>
