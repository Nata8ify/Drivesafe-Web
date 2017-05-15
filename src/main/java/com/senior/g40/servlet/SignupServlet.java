/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.service.UserService;
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
public class SignupServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        long personalId = Long.valueOf(request.getParameter("pid"));
        String phoneNumber = request.getParameter("phone");
        String address1 = request.getParameter("addr1");
        String address2 = request.getParameter("addr2");
        int age = Integer.valueOf(request.getParameter("age"));
        char gender = request.getParameter("sex").charAt(0);

        String username = request.getParameter("usrn");
        String password = request.getParameter("pswd");
        char userType = request.getParameter("utyp").charAt(0);
        if (UserService
                .getInstance()
                .createAccount(firstName, lastName, personalId, phoneNumber, address1, address2, age, gender,
                        username, password, userType)) {
            request.setAttribute("msg", "Account for " + username + " was created!.");
        } else {
            request.setAttribute("msg", "Account for " + username + " wasn't created!. Maybe username is duplicated.");
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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
