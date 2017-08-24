/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.service.FeedService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class DashboardFeedServlet extends HttpServlet {

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
        FeedService fs = FeedService.getInstance();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String opt = getAsString("opt");
            switch(opt){
                case "get" : out.print(fs.getFeeds(null)); break;
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

    private Integer getAsInteger(String param) {
        if (request.getParameter(param) == null) {
            return null;
        }
        return Integer.valueOf(request.getParameter(param));
    }

    private Long getAsLong(String param) {
        if (request.getParameter(param) == null) {
            return null;
        }
        return Long.valueOf(request.getParameter(param));
    }

    private Float getAsFloat(String param) {
        if (request.getParameter(param) == null) {
            return null;
        }
        return Float.valueOf(request.getParameter(param));
    }
    
    private Date getAsDate(String param) {
        if (request.getParameter(param) == null) {
            return null;
        }
        return Date.valueOf(request.getParameter(param));
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
