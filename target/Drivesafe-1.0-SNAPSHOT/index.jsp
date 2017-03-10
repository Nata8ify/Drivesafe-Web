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
                <input name="utyp" type="hidden"  value="M" required=""/>
                <input type="submit"/>&nbsp;&nbsp;<input type="reset"/><br/><br/>
                <a href="#regis">Register for Driving User</a>&nbsp;&nbsp;
                <a href="#">Are you a Monitoring/Super User?</a>&nbsp;&nbsp;<br/><br/>
                <a href="#">SKIP</a>
            </form>
        </fieldset>
        <br/><br/><br/>
        <fieldset id="regis">
            <legend>Driving User Sign up</legend>
            <form action="Signup" method="POST">
                First name : <input name="fname" type="text" required=""/><br/><br/>
                Last name : <input name="lname" type="text"  required=""/><br/><br/>
                Personal ID : <input name="pid" type="number" required=""/><br/><br/>
                Phone : <input name="phone" type="number"  required=""/><br/><br/>
                Address 1 : <input name="addr1" type="text" required=""/><br/><br/>
                Address 2 : <input name="addr2" type="text" /><br/><br/>
                Age : <input name="age" type="number" required=""/><br/><br/>
                Gender : <select name="sex">
                    <option value="M">Male</option>
                    <option value="F">Female</option>
                    <option value="O">None of above</option>
                </select><br/><br/>
                <hr/>
                <p>Define your Username and Password for next time Sign in into the System.</p>
                Username : <input name="usrn" type="text" required=""/><br/><br/>
                Password : <input name="pswd" type="password"  required=""/><br/><br/>
                <input name="utyp" type="hidden"  value="M" required=""/>
                <input type="submit"/>&nbsp;&nbsp;<input type="reset"/><br/>
            </form>
        </fieldset>
    </body>
</html>

