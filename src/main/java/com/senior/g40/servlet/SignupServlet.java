/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.Organization;
import com.senior.g40.service.SettingService;
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

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;
        SettingService ss = SettingService.getInstance();
        UserService us = UserService.getInstance();
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

        if (userType == 'T') {
            int organizationId = request.getParameter("organizationId") != null ? Integer.valueOf(request.getParameter("organizationId")) : 0;
            if (organizationId == 0) {
                organizationId = (int)ss.createOrganization(getParameterOrganization()).getObj();
                System.out.println(organizationId);
            }
            ss.storeOpertingLocationandOrganization(new LatLng(13.645819999104077, 100.48640727996826), 10, us.getLatestUserId(), organizationId);
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

    /* Bind Request Parameter into Organization Object */
    private Organization getParameterOrganization() {
        return new Organization(getAsInteger("organizationId"), getAsString("organizationName"), getAsString("organizationDescription"));
    }

    private String getAsString(String param) {
        return request.getParameter(param);
    }

    private char getAsChar(String param) {
        return request.getParameter(param).charAt(0);
    }

    private int getAsInteger(String param) {
        return request.getParameter(param) == null ? 0 : Integer.valueOf(request.getParameter(param));
    }

    private long getAsLong(String param) {
        return Long.valueOf(request.getParameter(param));
    }

    private float getAsFloat(String param) {
        return Float.valueOf(request.getParameter(param));
    }
}
