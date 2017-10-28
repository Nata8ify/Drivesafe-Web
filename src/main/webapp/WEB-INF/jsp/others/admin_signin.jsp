<%-- 
    Document   : admin_signin
    Created on : Oct 23, 2017, 11:05:13 AM
    Author     : PNattawut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">-->
        <link rel="stylesheet" href="css/style2.css">             
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />               
        <title> ผู้ดูแลระบบ </title>     
    </head>
    <body>

        
        <div class="form" >



            <div class="tab-content" >                



                <div id="login" >   
                    <h1> ผู้ดูแลเข้าสู่ระบบ </h1>                  

                    <form action="Admin" method="post">

                        <div class="field-wrap">


                            <label>
                                ชื่อผู้ใช้<span class="req">*</span>
                            </label>
                            <input name="username" type="text" required=""/>

                        </div>

                        <div class="field-wrap">


                            <label>
                                รหัสผ่าน<span class="req">*</span>
                            </label>
                            <input name="password" type="password" required=""/>


                        </div>
                        <input type="hidden" value="signin" name="opt"/>


                        <center><button type="submit" class="button3 button-block"/>เข้าใช้งาน</button></center>



                    </form>

                </div>
                <div id="empty">   
                </div>



            </div><!-- tab-content -->

        </div>

        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script  src="js/index.js"></script>
    </body>
</html>
