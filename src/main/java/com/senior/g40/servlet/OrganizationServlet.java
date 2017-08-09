/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.senior.g40.model.extras.Organization;
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
public class OrganizationServlet extends HttpServlet {

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
        SettingService settingService = SettingService.getInstance();
        String opt = request.getParameter("opt");
        switch(opt){
            case "create" :
                if(settingService.createOrganization(getParameterOrganization()).isSuccess()){
                    request.setAttribute("result", true);
                } else {
                    request.setAttribute("result", false);
                }
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "update" :
                if(settingService.updateOrganization(getParameterOrganization()).isSuccess()){
                    request.setAttribute("result", true);
                } else {
                    request.setAttribute("result", false);
                }
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");    
                break;
            case "delete_all" :
                break;
            case "delete_byid" :
                break;    
            case "get_all" :
                request.setAttribute("result", new Gson().toJson(settingService.getOrganizations()));
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");    
                break;
            case "get_byid" :
                request.setAttribute("result", new Gson().toJson(settingService.getOrganizationById(getAsInteger("organizationId"))));
                break;
        }
    }

    private void goTo(String destination) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }

    /* Bind Request Parameter into Organization Object */
    private Organization getParameterOrganization(){
        return new Organization(getAsInteger("organizationId") , getAsString("organizationName"), getAsString("organizationDescription"));
    }
    
    private String getAsString(String param) {
        return request.getParameter(param);
    }
    
    private char getAsChar(String param) {
        return request.getParameter(param).charAt(0);
    }
    
     private int getAsInteger(String param) {
        return request.getParameter(param)==null?0:Integer.valueOf(request.getParameter(param));
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
