/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.utils.A;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class ToServlet extends HttpServlet {
// ToServlet handling on in-comming request to dispatcher to another web resource.

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request;
        this.response = response;
        String option = request.getParameter("opt");

        switch (option) {
            case "main":
                to(A.Path.JSP_DIR + "main.jsp");
                break;
            case "index":
                to("/index.jsp");
                break;
            case "stat":
                to("/Statistic");
                break;
            case "sett":
                to(A.Path.JSP_DIR + "/setting.jsp");
                break;
            case "rregis":
                to(A.Path.JSP_OTHER_DIR + "rregis.jsp");
                break;
            default:
                return;
        }

    }

    private void to(String destination) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
