/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.utils.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class TestConnectServlet extends HttpServlet {

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
            String str = "";
            try {
                /* TODO output your page here. You may use following sample code. */
                Connection conn = ConnectionBuilder.getConnection();
                String sql = "SELECT * FROM drivesafe.user WHERE username = 'root' AND password = '63a9f0ea7bb98050796b649e85481845' AND userType = 'T'";
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                rs.first();
                str = rs.getString("username");
            } catch (SQLException ex) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet TestConnectServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Message" + ex.getLocalizedMessage() + "</h1>");
                out.println("<p>" + getStackTrace(ex) + "</p>");
                out.println("</body>");
                out.println("</html>");
                Logger.getLogger(TestConnectServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestConnectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestConnectServlet at " + str + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
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
