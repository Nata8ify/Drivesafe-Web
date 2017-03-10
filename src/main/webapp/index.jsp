<%-- 
    Document   : index
    Created on : Mar 10, 2017, 12:19:24 PM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Welcome to Drivesafe System!</h1><br/>
        <fieldset>
            <legend>Login ${msg}</legend>
            <form action="Login" method="POST">
                Username : <input name="usrn" type="text" required=""/><br/><br/>
                Password : <input name="pswd" type="password"  required=""/><br/><br/>
                <input name="utyp" type="hidden"  value="T" required=""/>
                <input type="submit"/>&nbsp;&nbsp;<input type="reset"/><br/><br/>
                <a href="#">Register for Driving User</a>&nbsp;&nbsp;
                <a href="#">Are you a Monitoring/Super User?</a>&nbsp;&nbsp;<br/><br/>
                <a href="#">SKIP</a>
            </form>
        </fieldset>
    </body>
</html>

