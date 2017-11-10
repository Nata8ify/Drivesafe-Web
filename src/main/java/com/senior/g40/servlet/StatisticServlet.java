/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.StatisticService;
import com.senior.g40.utils.App;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PNattawut
 */
public class StatisticServlet extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        {
            this.request = request;
            this.response = response;
            response.setContentType("text/html;charset=UTF-8");
            StatisticService statService = StatisticService.getInstance();
            AccidentService accService = AccidentService.getInstance();
            String opt = request.getParameter("opt");
            try (PrintWriter writer = response.getWriter()) {
                switch (opt) {
                    case "statWeekendAcc":
                        String numberOfTotalAccStatJSON = statService.parseDateAccidentStatisticToJSON(statService.getNumberOfAccidentViaDate(new Date(System.currentTimeMillis() - App.Const.DATE_WEEK_FOR_SQLCMD), new Date(System.currentTimeMillis())));
                        request.setAttribute("result", numberOfTotalAccStatJSON);
                        goTo(App.Path.JSP_RESULT_DIR + "/result.jsp");
                        return;
                    case "statAccFreqByType":
                        writer.print(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(statService.getIncidentTypeByDatePeriod(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        writer.close();
                        return;
                    case "statSpecPeriodAcc":
                        String nAccPeriodStatJSON
                                = statService
                                        .parseDateAccidentStatisticToJSON(statService.getNumberOfAccidentViaDate(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate"))));
                        request.setAttribute("result", nAccPeriodStatJSON);
                        goTo(App.Path.JSP_RESULT_DIR + "/result.jsp");
                        break;
                    /* ----Number of Accident which Distinguish by Type START --- */
                    case "statSpecPeriodCrash":
                        request.setAttribute("result", statService
                                .parseDateAccidentStatisticToJSON(statService.getNumberOfCrashTypeAccident(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        goTo(App.Path.JSP_RESULT_DIR + "/statrs_ch1.jsp");
                        break;
                    case "statSpecPeriodFire":
                        request.setAttribute("result", statService
                                .parseDateAccidentStatisticToJSON(statService.getNumberOfFireTypeAccident(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        goTo(App.Path.JSP_RESULT_DIR + "/statrs_ch2.jsp");
                        break;
                    case "statSpecPeriodAnimal":
                        request.setAttribute("result", statService
                                .parseDateAccidentStatisticToJSON(statService.getNumberOfAnimalTypeAccident(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        goTo(App.Path.JSP_RESULT_DIR + "/statrs_ch3.jsp");
                        break;
                    case "statSpecPeriodPatient":
                        request.setAttribute("result", statService
                                .parseDateAccidentStatisticToJSON(statService.getNumberOfPatientTypeAccident(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        goTo(App.Path.JSP_RESULT_DIR + "/statrs_ch4.jsp");
                        break;
                    case "statSpecPeriodOther":
                        request.setAttribute("result", statService
                                .parseDateAccidentStatisticToJSON(statService.getNumberAnotherTypeAccident(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate")))));
                        goTo(App.Path.JSP_RESULT_DIR + "/statrs_ch5.jsp");
                        break;
                    case "statRealtimeTodayInBoundAccsFreq":
                        request.setAttribute("result", accService.getCurrentDateInBoundAccidents(Long.valueOf(request.getParameter("userId"))));
                        break;
                    /* ----Number of Accident which Distinguish by Type END --- */
                    case "statFalseAcc":
                        String nFalseAccPeriodStatJSON
                                = statService
                                        .parseDateAccidentStatisticToJSON(statService.getNumberOfFalseAccidentViaDate(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate"))));
                        request.setAttribute("result", nFalseAccPeriodStatJSON);
                        break;
                    case "statUserFalseAcc":
                        String nUsrFalseAccPeriodStatJSON
                                = statService
                                        .parseDateAccidentStatisticToJSON(statService.getNumberOfFalseAccidentViaDate(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate"))));
                        request.setAttribute("result", nUsrFalseAccPeriodStatJSON);
                        break;
                    case "statSysFalseAcc":
                        String nSysFalseAccPeriodStatJSON
                                = statService
                                        .parseDateAccidentStatisticToJSON(statService.getNumberOfFalseAccidentViaDate(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate"))));
                        request.setAttribute("result", nSysFalseAccPeriodStatJSON);
                        break;
                    case "statWeekAccGeo":
                        String weekAccLatLngStatJSOn = statService
                                .parseAccidentGeoCStatisticToJSON(statService.getWeekAccidentGeoStatistic());
                        writer.print(weekAccLatLngStatJSOn);
                        writer.close();
//                        request.setAttribute("result", weekAccLatLngStatJSOn);
//                        goTo(App.Path.JSP_RESULT_DIR + "/result.jsp");
                        break;
                    case "statPeriodAccGeo":
                        String periodAccLatLngStatJSON = statService
                                .parseAccidentGeoCStatisticToJSON(statService.getByDatePeriodAccidentGeoStatistic(Date.valueOf(request.getParameter("bDate")), Date.valueOf(request.getParameter("eDate"))));
                        writer.print(periodAccLatLngStatJSON);
                        writer.close();
//                        request.setAttribute("result", periodAccLatLngStatJSON);
//                        goTo(App.Path.JSP_RESULT_DIR + "/result.jsp");
                        break;
                    case "statSpeedDetected":
                        String statSpeedDEtectedJSON = statService
                                .parseCrashSpeedStatisticToJSON(statService
                                        .getTotalCrashSpeedStatistic());
                        request.setAttribute("result", statSpeedDEtectedJSON);
                        goTo(App.Path.JSP_RESULT_DIR + "/result.jsp");
                        break;
                    default:
                        System.out.println("Redirect to stat.jsp");
                        getServletContext().getRequestDispatcher(App.Path.JSP_DIR + "/stat.jsp").forward(request, response);
                }
            }
        }
    }

    private void goTo(String path) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(path).forward(request, response);
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
