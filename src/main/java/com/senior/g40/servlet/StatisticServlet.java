/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.service.StatisticService;
import com.senior.g40.utils.A;
import java.io.IOException;
import java.io.PrintWriter;
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
public class StatisticServlet extends HttpServlet {

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
        {
            this.request = request;
            this.response = response;
            response.setContentType("text/html;charset=UTF-8");
            StatisticService statService = StatisticService.getInstance();
            String opt = request.getParameter("opt");
            switch (opt) {
                case "statTotalAcc":
                    String numberOfAccStatJSON = statService.parseDateAccidentStatisticToJSON(statService.getNumberOfAccidentViaDate(Date.valueOf("2017-03-19"), new Date(System.currentTimeMillis())));
                    request.setAttribute("result", numberOfAccStatJSON);
                    goTo(A.Path.JSP_RESULT_DIR+"/result.jsp");
                    return;
                case "statAccGeo":
                    String accLatLngStatJSOn = statService.parseAccidentGeoCStatisticToJSON(statService.getTotalAccidentGeoStatistic());
                    request.setAttribute("result", accLatLngStatJSOn);
                    goTo(A.Path.JSP_RESULT_DIR+"/result.jsp");
                    return;
                default:
                    System.out.println("Redirect to stat.jsp");
                    getServletContext().getRequestDispatcher(A.Path.JSP_DIR + "/stat.jsp").forward(request, response);
            }
        }
    }

    private void goTo(String path) throws ServletException, IOException{
         getServletContext().getRequestDispatcher(path).forward(request, response);
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
