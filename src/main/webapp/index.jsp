<%@page import="com.senior.g40.utils.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% if (session.getAttribute("pf") != null) {
        response.sendRedirect("To?opt=main");
    }%>

<!doctype html>
<html lang="en">
    <head>
        <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeeWorh</title>

        <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">-->
        <link rel="stylesheet" href="css/style.css">

        
        
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

    </head>
    <body>
        <header>
            
                
            <h1> WeeWorh System </h1>
            
        </header>

        <div class="form">

            <ul class="tab-group">
                <li class="tab active"><a href="#login">เข้าสู่ระบบ</a></li>
                <li class="tab "><a href="#signup">สมัครสมาชิก</a></li>

            </ul>

            <div class="tab-content">

                <div id="login">   
                    <h1>ส่วนเข้าใช้งานสำหรับสมาชิกหน่วยกู้ภัย ${msg}</h1>                  

                    <form action="Login" method="post">

                        <div class="field-wrap">
                            
                            <!--
                            <label>
                                ชื่อผู้ใช้<span class="req">*</span>
                            </label>
                            -->
                            <input name="usrn" type="text" required="" value="root" autocomplete="off"/>                         
                        </div>

                        <div class="field-wrap">
                             
                            <!--
                            <label>
                                รหัสผ่าน<span class="req">*</span>
                            </label>
                            -->
                            <input name="pswd" type="password"  required="" value="master" autocomplete="off"/>

                        </div>
                        <input name="utyp" type="hidden"  value="T" required=""/>
                        <!--
                                                <p class="forgot"><a href="#">Forgot Password?</a></p>
                        -->
                        <center><button type="submit" class="button button-block"/>เข้าใช้งาน</button></center>
                        


                    </form>

                </div>

                <div id="signup">   
                    <h1>สมัครสมาชิกสำหรับผู้ใช้งานทั่วไป</h1>

                    <form action="Signup" method="post">

                        <div class="top-row">
                            <div class="field-wrap">
                                <label>
                                    ชื่อ<span class="req">*</span>
                                </label>                                  
                                <input name = "fname" type = "text" required = "" />                                                                                                                                     
                            </div>

                            <div class="field-wrap">
                                <label>
                                    นามสกุล<span class="req">*</span>                              
                                </label>
                                <input name = "lname" type = "text"  required = ""/>
                            </div>
                        </div>

                        <div class="field-wrap">
                            <label>
                                รหัสประชาชน<span class="req">*</span>
                            </label>
                            <input name = "pid" type = "number" required = "" />

                        </div>
                        <div class="field-wrap">
                            <label>
                                เบอร์โทรศัพท์<span class="req">*</span>
                            </label>
                            <input name = "pid" type = "number" required = "" />

                        </div>
                        <div class="field-wrap">
                            <label>
                                ที่อยู่<span class="req">*</span>
                            </label>
                            <input name = "pid" type = "text" required = "" />

                        </div>
                        <div class="field-wrap">
                        <div class="sel">
                            <select name="sex">
                                <option value="" disabled>เพศ</option>
                                <option value = "M"> ผู้ชาย </option>
                                <option value = "F"> ผู้หญิง </option>
                                <option value = "O"> ไม่เจาะจง </option>
                            </select>
                        </div>
                        </div>
                      
                        
                        <h1> ระบุชื่อผู้ใช้และรหัสผ่าน </h1>

                        <div class="field-wrap">
                            <label>
                                ชื่อผู้ใช้<span class="req">*</span>
                            </label>
                            <input name = "usrn" type = "text" required = "" />
                        </div>    
                        <div class="field-wrap">
                            <label>
                                รหัสผ่าน<span class="req">*</span>
                            </label>
                            <input name = "pswd" type = "password"  required = ""/>
                        </div>                       
                        <input name = "utyp" type = "hidden"  value = "M" required = ""/>
                        
                        
                        <button type="submit" class="button3 button-block">ยืนยัน</button>
                        <button type="reset" class="button2 button-block">รีเซ็ต</button>
                        
                        
                    </form>

                </div>
            </div><!-- tab-content -->

        </div> 
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script  src="js/index.js"></script>
    </body>
</html>