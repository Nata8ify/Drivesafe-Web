/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.UserService;
import com.senior.g40.utils.A;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author PNattawut
 */
public class RescuerAppInServlet extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request;
        this.response = response;
        AccidentService accService = AccidentService.getInstance();
        UserService usrService = UserService.getInstance();
        String option = request.getParameter("opt");
        switch (option) {
            case "login": //1. Rescuer Login Section START ---- 
                Profile pf = usrService.login(
                        request.getParameter("usrn"),
                        request.getParameter("pswd"),
                        request.getParameter("utyp").charAt(0)); //Or constant 'T'
                request.setAttribute("result", usrService.convertProfileToJSON(pf));
                goTo(A.Path.JSP_RESULT_DIR + "result.jsp");
                break; //1.END ---- 
            case "getaccs":
                List<Accident> accidents = accService.getAllAccidents();
                if (accidents != null) {
                    JSONArray accsJson = null;
                    for (Accident acc : accidents) {
                        if (accsJson == null) {
                            accsJson = new JSONArray();
                        }
                        accsJson.put(accService.convertAccidentToJSON(acc));
                    }
                    System.out.println(accsJson);
                    request.setAttribute("result", accsJson);
                } else {
                    request.setAttribute("result", "WOW");
                }
                goTo(A.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_activeaccs":
                List<Accident> activeAccidents = accService.getActiveAccidents();
                if (activeAccidents != null) {
                    JSONArray accsJson = null;
                    for (Accident acc : activeAccidents) {
                        if (accsJson == null) {
                            accsJson = new JSONArray();
                        }
                        accsJson.put(accService.convertAccidentToJSON(acc));
                    }
                    System.out.println(accsJson);
                    request.setAttribute("result", accsJson);
                } else {
                    request.setAttribute("result", "WOW");
                }
                goTo(A.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_boundactacc":
                long rescuerId = Long.parseLong(request.getParameter("userId"));
                List<Accident> boundActiveAccidents = accService.getCurrentDateInBoundAccidents(rescuerId);
                if (boundActiveAccidents != null) {
                    request.setAttribute("result", new Gson().toJson(boundActiveAccidents));
                } else {
                    request.setAttribute("result", "WOW");
                }
                goTo(A.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "sys_accfalse":
                break;
            default:
                    request.setAttribute("result", "WOW");
                return;
        }
    }

    private void goTo(String destination) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }

    private String getS(String param) {
        return request.getParameter(param);
    }

    private long getL(String param) {
        return Long.valueOf(request.getParameter(param));
    }

    private float getF(String param) {
        return Float.valueOf(request.getParameter(param));
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
