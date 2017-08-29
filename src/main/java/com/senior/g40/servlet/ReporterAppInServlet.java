/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
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
public class ReporterAppInServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AccidentService accService;
    private UserService usrService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        accService = AccidentService.getInstance();
        usrService = UserService.getInstance();
        String option = request.getParameter("opt");
        switch (option) {
            case "login": //1. Rescuer Login Section (Same Driver) START ---- 
                Profile pf = usrService.login(
                        request.getParameter("usrn"),
                        request.getParameter("pswd"),
                        request.getParameter("utyp").charAt(0)); //Or constant'M'
                request.setAttribute("result", usrService.convertProfileToJSON(pf));
                break; //1.END ---- 
            case "report":
                /* User Send Incident Report on their Specific Type. */
                // AccType IS NOT FUNCTION BY NOW. 
                report(getAccidentData());
                break;
            case "report_crash":
                report(getCustomAccidentData(Accident.ACC_TYPE_TRAFFIC));
                break;
            case "report_fire":
                report(getCustomAccidentData(Accident.ACC_TYPE_FIRE));
                break;
            case "report_patient": //coma patient's case.
                report(getCustomAccidentData(Accident.ACC_TYPE_PATIENT));
                break;
            case "report_animal":
                report(getCustomAccidentData(Accident.ACC_TYPE_ANIMAL));
                break;
            case "report_brawl":
                report(getCustomAccidentData(Accident.ACC_TYPE_BRAWL));
                break;
            case "report_other":
                report(getCustomAccidentData(Accident.ACC_TYPE_OTHER));
                break;
            case "usr_accfalse":
                /*3. for receive/acknowledge user false positive data.*/
                Result rs = accService.updateOnUserFalseAccc(Long.valueOf(request.getParameter("userId")), Long.valueOf(request.getParameter("accid")));
                if (rs.isSuccess()) {
                    request.setAttribute("result", true);
                } else {
                    request.setAttribute("result", rs.getExcp());
                }
                break;// 3. END ------
            case "update_accident_info":
                request.setAttribute("result", new Gson().toJson(accService.getAccidentById(getAsLong("accid"))));
                break;
            default: //TODO
        }
        getServletContext().getRequestDispatcher(App.Path.JSP_RESULT_PAGE).forward(request, response);
    }

    /* Utility Methods */
    private Accident getAccidentData() {
        Date currentDate = new Date(System.currentTimeMillis());
        return new Accident(getAsLong("usrid"),
                currentDate,
                new SimpleDateFormat("HH:mm").format(currentDate),
                getAsFloat("lat"),
                getAsFloat("lng"),
                getAsByte("acctype"),
                Accident.ACC_CODE_A);
    }

    private Accident getCustomAccidentData(byte accType) {
        Date currentDate = new Date(System.currentTimeMillis());
        return new Accident(getAsLong("usrid"),
                currentDate,
                new SimpleDateFormat("HH:mm").format(currentDate),
                getAsFloat("lat"),
                getAsFloat("lng"),
                accType,
                Accident.ACC_CODE_A);
    }

    private void report(Accident acc) {
        Result rs = accService.saveNonCrashAccident(acc);
        if (rs.isSuccess()) {
            Accident savedAcc = (Accident) rs.getObj();
            request.setAttribute("result", savedAcc.toJson());
        } else {
            request.setAttribute("result", null);
        }
    }

    private String getAsString(String param) {
        return request.getParameter(param);
    }

    private byte getAsByte(String param) {
        return Byte.valueOf(request.getParameter(param));
    }

    private long getAsLong(String param) {
        return Long.valueOf(request.getParameter(param));
    }

    private float getAsFloat(String param) {
        return Float.valueOf(request.getParameter(param));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
