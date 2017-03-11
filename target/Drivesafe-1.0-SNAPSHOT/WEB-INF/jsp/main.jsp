<%-- 
    Document   : main
    Created on : Mar 10, 2017, 12:21:22 PM
    Author     : PNattawut
--%>

<%@page import="com.senior.g40.model.Profile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Profile pf = (Profile)request.getSession(true).getAttribute("pf"); 
if(pf != null){
pageContext.setAttribute("pf", pf); 
} else {response.sendRedirect("To?opt=index");}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
    <body>
        <h1>Welcome! ${pf.firstName} ${pf.lastName}</h1>
        <p>This is your profile : ${pf}</p>
        <a href="To?opt=stat">Open Statistics</a><a href="Logout">Logout / Invalidate</a><br/><hr/>
        <div id="mornitorMap"></div>
        <br/><hr/>
    </body>
</html>
