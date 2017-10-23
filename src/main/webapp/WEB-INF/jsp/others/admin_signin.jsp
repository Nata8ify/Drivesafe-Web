<%-- 
    Document   : admin_signin
    Created on : Oct 23, 2017, 11:05:13 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrator Sign In</title>
    </head>
    <body>
        <fieldset>
            <legend>Sign in</legend>
            <form action="Admin" method="post">
                <input name="username" type="text" required=""/>
                <input name="password" type="password" required=""/>
                <input type="hidden" value="signin" name="opt"/>
                <input type="submit"/>
            </form>
        </fieldset>
    </body>
</html>
