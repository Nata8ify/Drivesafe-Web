/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.UserService;
import com.senior.g40.utils.App;
import com.senior.g40.utils.Result;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class DriverAppInServlet extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request;
        this.response = response;
        AccidentService accService = AccidentService.getInstance();
        UserService usrService = UserService.getInstance();
        String option = request.getParameter("opt");
        Result rs;
        switch (option) {
            case "login": //1. Driver Login Section START (Same User) ---- 
                Profile pf = usrService.login(
                        request.getParameter("usrn"),
                        request.getParameter("pswd"),
                        request.getParameter("utyp").charAt(0)); //Or constant'M'
                request.setAttribute("result", usrService.convertProfileToJSON(pf));
                break; //1.END ---- 
            case "acchit": //2. Driver got an accident and save accident data Section START ----
                rs = accService.saveCrashedAccident(getAccidentData());
                if (rs.isSuccess()) {
                    // **PRIORITY** DONE! -> will system return 'Accident' Data back? 
                    Accident acc = (Accident) rs.getObj();
                    request.setAttribute("result", accService.convertAccidentToJSON(acc));
                } else {
                    request.setAttribute("result", rs.getExcp());
                }
                break; //2. END ---- 
            case "usr_accfalse": //3. for receive/acknowledge user false positive data.
                rs = accService.updateOnUserFalseAccc(0, Long.valueOf(request.getParameter("accid")));
                if (rs.isSuccess()) {
                    request.setAttribute("result", true);
                } else {
                    request.setAttribute("result", rs.getExcp());
                }
                break;// 3. END ------
            case "sys_accfalse": //4. for receive/acknowledge system false positive data.
                rs = accService.updateOnSystemFalseAccc(getAsLong(App.Param.responsibleRescr) ,Long.valueOf(request.getParameter("accid")));
                if (rs.isSuccess()) {
                    request.setAttribute("result", true);
                } else {
                    request.setAttribute("result", rs.getExcp());
                }
                break;//4. END ------
            default:
                System.out.println("N/A");
                return;
        }

        getServletContext().getRequestDispatcher(App.Path.JSP_RESULT_DIR + "result.jsp").forward(request, response);
    }

    private Accident getAccidentData() {
        Date currentDate = new Date(System.currentTimeMillis());
        return new Accident(getAsLong("usrid"),
                currentDate,
                new SimpleDateFormat("HH:mm").format(currentDate),
                getAsFloat("lat"),
                getAsFloat("lng"),
                getAsFloat("fdt"),
                getAsFloat("sdt"),
                Accident.ACC_CODE_A,
                Accident.ACC_TYPE_TRAFFIC);
    }

    private String getAsString(String param) {
        return request.getParameter(param);
    }

    private long getAsLong(String param) {
        return Long.valueOf(request.getParameter(param));
    }

    private float getAsFloat(String param) {
        return Float.valueOf(request.getParameter(param));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
