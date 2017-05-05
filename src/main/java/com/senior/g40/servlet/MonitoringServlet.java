/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.service.AccidentService;
import com.senior.g40.utils.A;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author PNattawut
 */
public class MonitoringServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            List<Accident> accidents;
            AccidentService accService = AccidentService.getInstance();
            String opt = request.getParameter("opt");
            Profile pf = (Profile) request.getSession(false).getAttribute("pf");
            switch (opt) {
                case "quickacc":
                    accidents = accService.getActiveAccidents();
                    if (accidents != null) {
                        JSONArray accsJson = null;
                        for (Accident acc : accidents) {
                            if (accsJson == null) {
                                accsJson = new JSONArray();
                            }
                            accsJson.put(accService.convertAccidentToJSONForMonitorTable(acc));
                        }
                        System.out.println(accsJson);
                        request.setAttribute("result", accsJson);

                    }
                case "currentDate":
                    //แสดงเฉพาะวันที่นั้นๆ
                    accidents = accService.getCurrentDateAccidents();
                    if (accidents != null) {
                        JSONArray accsJson = null;
                        for (Accident acc : accidents) {
                            if (accsJson == null) {
                                accsJson = new JSONArray();
                            }
                            accsJson.put(accService.convertAccidentToJSONForMonitorTable(acc));
                        }
                        System.out.println(accsJson);
                        request.setAttribute("result", accsJson);

                    } else {
                        request.setAttribute("result", "WOW");
                    }
                    break;
                case "currentDateInBoundReq":
                    //แสดงเฉพาะวันที่นั้นๆ
                    accidents = accService.getCurrentDateInBoundAccidents(pf.getUserId());
                    if (accidents != null) {
                        JSONArray accsJson = null;
                        for (Accident acc : accidents) {
                            if (accsJson == null) {
                                accsJson = new JSONArray();
                            }
                            accsJson.put(accService.convertAccidentToJSONForMonitorTable(acc));
                        }
                        request.setAttribute("result", accsJson);

                    } else {
                        request.setAttribute("result", "WOW");
                    }
                    break;
            }

            getServletContext().getRequestDispatcher(A.Path.JSP_RESULT_DIR + "result.jsp").forward(request, response);
        }
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
