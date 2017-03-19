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
import com.senior.g40.utils.A;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author PNattawut
 */
public class RescuerAppInServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request;
        this.response = response;
        AccidentService accService = AccidentService.getInstance();
        UserService usrService = UserService.getInstance();
        String option = request.getParameter("opt");
        switch (option) {
            case "login": //1. Rescuer Login Section START ---- 
                Profile pf = usrService.login(
                        request.getParameter("usrn"),
                        request.getParameter("pswd"),
                        request.getParameter("utyp").charAt(0)); //Or constant 'T'
                request.setAttribute("result", usrService.convertProfileToJSON(pf));
                to(A.Path.JSP_RESULT_DIR+"result.jsp");
                break; //1.END ---- 
            case "getaccs":
                List<Accident> accidents = accService.getAllAccidents();
                if (accidents != null) {
                    JSONArray accsJson = null;
                    for(Accident acc : accidents){
                        if(accsJson == null){
                            accsJson = new JSONArray();
                        }
                        accsJson.put(accService.convertAccidentToJSON(acc));
                    }
                    System.out.println(accsJson);
                    request.setAttribute("result", accsJson.toString());
                } else {
                    request.setAttribute("result", "WOW");
                }
                to(A.Path.JSP_RESULT_DIR+"result.jsp");
                break;
            case "sys_accfalse" :
                break;
            default:
                return;
        }
    }

    private void to(String destination) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }

    private String getS(String param) {
        return request.getParameter(param);
    }

    private long getL(String param) {
        return Long.valueOf(request.getParameter(param));
    }

    private float getF(String param) {
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
