/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.servlet;

import com.google.gson.Gson;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.StatisticService;
import com.senior.g40.utils.App;
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
public class DashboardServlet extends HttpServlet {

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
        this.request = request;
        this.response = response;
        String opt = getAsString("opt");
        StatisticService ss = StatisticService.getInstance();
        AccidentService as = AccidentService.getInstance();
        Gson gson = new Gson();
        //opt = "getDayOfWeekAccsFreq";
        try(PrintWriter writer = response.getWriter()){
        switch (opt) {
            case "getTodayAccsInBound": //Today Inbounded Accident find by userId
//                request.setAttribute(App.Attr.RESULT, gson.toJson(as.getCurrentDateInBoundAccidents(getAsLong("userId"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(as.getCurrentDateInBoundAccidents(getAsLong("userId"))));
                break;
            case "getDayOfWeekAccsFreq": //Frequency of Accidents in a day of week that was occured can filter a result by month or year.
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getDayOFWeekAccidentFreq(getAsInteger("month"), getAsInteger("year"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getDayOFWeekAccidentFreq(getAsInteger("month"), getAsInteger("year"))));
                break;
            case "getDayMonthlyAccsFreq": //Frequency of Accidents in a day of month that was occured can filter a result by year
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getDayMonthlyAccidentFreq(getAsInteger("year"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getDayMonthlyAccidentFreq(getAsInteger("year"))));
                break;
            case "getTodayOnDutyRscrPf": //Get the total on duty rescuer by current date (on duty is not 'A')
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getOnDutyRescuerProfiles(null)));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getOnDutyRescuerProfiles(null)));
                break;
            case "getTotalAccidentLatLng": //Get Total Accident Latitude & Longitude (For Google Marker or another purpose) (Inbound Filter is Optional by userId)
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getTotalAccidentLatLng(getAsLong("userId"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getTotalAccidentLatLng(getAsLong("userId"))));
                break;
            case "getByDateAccidentLatLng": //Get Total Accident Latitude & Longitude By Date (For Google Marker or another purpose) (Date Filter is Optional by date) (Inbound Filter is Optional by userId)
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getByDateAccidentLatLng(getAsDate("date"), getAsLong("userId"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getByDateAccidentLatLng(getAsDate("date"), getAsLong("userId"))));
                break;
            case "getReportFreqTimeSeries": //Get Report Time Series.
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getReportFreqSeries(getAsDate("date"))));
//                goTo(App.Path.JSP_RESULT_PAGE);
                writer.print(gson.toJson(ss.getReportFreqSeries(getAsDate("date"))));
                break;
            case "getStatusPercentage": // Get Percentage of Accident Status / Code order by "A G R C", Can be filtered by Specifically Date 
//                request.setAttribute(App.Attr.RESULT, gson.toJson(ss.getStatusPercentage(getAsDate("date"))));
//                goTo(App.Path.JSP_RESULT_PAGE_2);
                writer.print(gson.toJson(ss.getStatusPercentage(getAsDate("date"))));
                break;
            default:
                throw new IllegalStateException("No Such Option Available.");
        }
        writer.close();
        } 
        return;
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
