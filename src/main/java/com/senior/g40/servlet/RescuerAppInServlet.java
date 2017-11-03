/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.model.User;
import com.senior.g40.model.extras.Hospital;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.SettingService;
import com.senior.g40.service.UserService;
import com.senior.g40.utils.App;
import java.io.IOException;
import java.io.PrintWriter;
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
        SettingService settingService = SettingService.getInstance();
        String option = request.getParameter("opt");
        switch (option) {
            case "login": //1. Rescuer Login Section START ---- 
                Profile pf = usrService.login(
                        request.getParameter("usrn"),
                        request.getParameter("pswd"),
                        User.TYPE_RESCUER_USER); //Or constant 'T'
                request.setAttribute("result", usrService.convertProfileToJSON(pf));
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
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
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
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
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_boundactacc":
                long rescuerId = Long.parseLong(request.getParameter("userId"));
                List<Accident> boundActiveAccidents = accService.getCurrentDateInBoundAccidents(rescuerId);
                if (boundActiveAccidents != null) {
                    request.setAttribute("result", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(boundActiveAccidents));
                } else {
                    request.setAttribute("result", "WOW");
                }
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_accbyid":
                request.setAttribute("result", accService.getAccidentById(getAsLong("accidentId")).toJson());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_case_rscr" : // Get rescuer profile who responses for each incidednt case
                request.setAttribute("result", usrService.getRescuerProfileByIncidentId(getAsLong("accidentId")).toJson());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_inresponsibleacc" :
                request.setAttribute("result", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(accService.getRescuerInResponsibleAccident(getAsLong("userId")))); //userId is Id of rescuer.
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "set_customcode":
                request.setAttribute("result", accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), getAsChar(App.Param.accCode)).isSuccess());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "set_ongoing":
//                request.setAttribute("result", accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_G).isSuccess());
//                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                response.getWriter().print(accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_G).isSuccess());
                return;
            case "set_onrescue":
                request.setAttribute("result", accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_R).isSuccess());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "set_closed": 
//                request.setAttribute("result", accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_C).isSuccess());
//                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                response.getWriter().print(accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_C).isSuccess());
                return;
            case "sys_accfalse":
                request.setAttribute("result", accService.updateAccCodeStatus(getAsLong(App.Param.responsibleRescr), getAsLong(App.Param.accidentId), Accident.ACC_CODE_ERRS).isSuccess());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_userinfo":
                request.setAttribute("result", usrService.getProfileByUserId(getAsLong("userId")).toJson());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_organization_id":
                request.setAttribute("result", settingService.getOperatingLocationByUserId(getAsInteger("userId")).getOrganizationId());
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            case "get_nearest_hospital" : //<- new
                request.setAttribute("result", new Gson().toJson(settingService.getNearestHospitalByRscrPosition(new LatLng(getAsString("latitude"), getAsString("longitude")))));
                goTo(App.Path.JSP_RESULT_DIR + "result.jsp");
                break;
            default:
                request.setAttribute("result", "WOW");
                return;
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
