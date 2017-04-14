/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.extras.LatLng;
import com.senior.g40.service.SettingService;
import com.senior.g40.utils.Result;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class SettingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String option = request.getParameter("opt");
 Result rs;
        SettingService settingService = SettingService.getInstance();
        switch (option) {
            case "storeOpLocation":
                rs = settingService.storeOpertingLocation(
                        new LatLng(request.getParameter("lat"), request.getParameter("lng")),
                        Integer.valueOf(request.getParameter("boundRds")),
                        Long.valueOf(request.getParameter("userId")));
                break;
            case "supdateOpLocation":
                rs = settingService.updateOpertingLocation(
                        new LatLng(request.getParameter("lat"), request.getParameter("lng")),
                        Integer.valueOf(request.getParameter("boundRds")),
                        Long.valueOf(request.getParameter("userId")));
                break;
            case "getOpLocation":
                settingService.getOpertingLocation(Long.valueOf(request.getParameter("userId"))); //<-- Countinue this
                break;
            default: ;
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
