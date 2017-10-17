/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.senior.g40.model.extras.Hospital;
import com.senior.g40.service.SettingService;
import com.senior.g40.utils.App;
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
public class HospitalSettingServlet extends HttpServlet {

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
        this.request = request;
        this.response = response;
        SettingService settingService = SettingService.getInstance();
        String option = request.getParameter("opt");
        Gson gson = new Gson();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            switch (option) {
                case "get_all_hospitals":
                    out.print(gson.toJson(settingService.getAllHospital()));
                    break;
                case "register_hospital": //<- new
                    Hospital hospital = (Hospital) settingService.saveHospital(new Hospital(getAsString("name"), getAsDouble("latitude"), getAsDouble("longitude"))).getObj();
                    out.print(gson.toJson(hospital));
                    break;
                case "get_nearest_hospital_one": //<- new
                    out.print(gson.toJson(settingService.getNearByHospitalByHospital(new Hospital(null, getAsDouble("latitude"), getAsDouble("longitude")))));
                    break;    
                case "delete_hospital":
                    out.print(settingService.deleteHospital(getAsInteger("hospitalId")));
                    break;
                default:;
            }
        }
    }
    
    private void goTo(String destination) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }
    
    private String getAsString(String param) {
        return request.getParameter(param);
    }
    
    private char getAsChar(String param) {
        return request.getParameter(param).charAt(0);
    }
    
    private long getAsLong(String param) {
        return Long.valueOf(request.getParameter(param));
    }
    
    private int getAsInteger(String param) {
        return Integer.valueOf(request.getParameter(param));
    }
    
    private float getAsFloat(String param) {
        return Float.valueOf(request.getParameter(param));
    }
    
    private double getAsDouble(String param) {
        return Double.valueOf(request.getParameter(param));
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
