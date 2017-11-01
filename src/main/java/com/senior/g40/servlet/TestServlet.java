/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.Accident;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.service.AccidentService;
import com.senior.g40.utils.App;
import com.senior.g40.utils.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/Test"})
public class TestServlet extends HttpServlet {

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
        String test = request.getParameter("test");
        Result result = null;
        switch (test) {
            case "fcmtest":
                Date current = new Date(System.currentTimeMillis());
                result = AccidentService.getInstance().boardcastRescueRequest(new Accident(12, current, new SimpleDateFormat("HH:mm").format(current), 13.646727561950684F, 100.48721313476562F, Byte.MAX_VALUE, 'C'));
                request.setAttribute("msg", result.toReformedResult());
                break;
            case "instancetest":
                System.out.println(OperatingLocation.getInstance(null).toJSON());
                request.setAttribute("msg", "op ".concat(OperatingLocation.getInstance(null).toJSON()));
                break;
            case "report":
                getServletContext().getRequestDispatcher("/ReporterAppIn?opt=report&usrid=14&lat=13.64672565460205&lng=100.48986053466797&accc=A&time=11:11&acctype=1").forward(request, response);
                break;
            case "buildchk":
                request.setAttribute("msg", "1120010817");
            default: //TODO
        }
        getServletContext().getRequestDispatcher(App.Path.JSP_RESULT_DIR + "message.jsp").forward(request, response);
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
