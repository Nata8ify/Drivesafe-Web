/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.senior.g40.model.Profile;
import com.senior.g40.model.User;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.service.SettingService;
import com.senior.g40.service.UserService;
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
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("usrn");
        String password = request.getParameter("pswd");
        char userType = request.getParameter("utyp").charAt(0);

        Profile pf = UserService.getInstance().login(username, password, userType);
        if (pf != null) {
            request.setAttribute("msg", username);
            request.getSession(true).setAttribute("pf", pf);
            Profile.setInstance(pf);
            if (userType == User.TYPE_RESCUER_USER) {
                OperatingLocation op = SettingService.getInstance().getOperatingLocationByUserId(pf.getUserId());
                System.out.println("op :: "+op.toJSON());
                if(request.getParameter("skipSession") == null){
                    request.getSession(true).setAttribute("op", op);
                }
            }
            getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);

        } else {
            request.setAttribute("msg", "<p style='color:red'>" + username + " is not found or incorrect password</p>");
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
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
