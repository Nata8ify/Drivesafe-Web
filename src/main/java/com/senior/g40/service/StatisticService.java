/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.google.gson.Gson;
import com.senior.g40.utils.ConnectionBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class StatisticService {

    private static StatisticService statisticService;

    public static StatisticService getInstance() {
        if (statisticService == null) {
            statisticService = new StatisticService();
        }
        return statisticService;
    }

    // Get Number of Accident Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfAccidentViaDate(Date beginDate, Date lastDate) {
        HashMap<Date, Integer> accStatHashMap = null;
        Calendar cal = null;
        Date iterDate = null;
        SimpleDateFormat sdf = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            cal = Calendar.getInstance();
            cal.setTime(beginDate);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sqlCmd;
            PreparedStatement pstm;
            ResultSet rs;
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ?;";
                pstm = conn.prepareStatement(sqlCmd);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new HashMap<Date, Integer>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    return accStatHashMap;
                }

                System.out.println(sdf.format(cal.getTime()) + " > " + rs.getInt(1));
                cal.add(Calendar.DATE, 1);
                System.out.println("\nNext Date : " + sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) != 1);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accStatHashMap;
    }

    //Get Total Accident Location via GeoCoordinate.
    public List<GeoCoordinate> getTotalAccidentGeoStatistic() {
        List<GeoCoordinate> accGeoCList = null;
        GeoCoordinate accGeoC = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {

            String sqlCmd = "SELECT latitude, longitude FROM `accident`;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                if(accGeoCList == null){ accGeoCList = new ArrayList<GeoCoordinate>();}
                accGeoC = new GeoCoordinate();
                accGeoC.setLatitude(Double.valueOf(rs.getFloat("latitude")));
                accGeoC.setLongitude(Double.valueOf(rs.getFloat("longitude")));
                accGeoCList.add(accGeoC);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
            return accGeoCList;
    }
    
    //Get Statistic of Accident by Day Time Period.
    public HashMap<Date, Integer> getDayTimePeriodOfAccidentStatistic(){
        return null;
    }

    /**
     * ***Dealing with JSON*****
     */
    //Parse Number of Accident Statistic on Specific Period Date into JSON Format.
    public String parseDateAccidentStatisticToJSON(HashMap<Date, Integer> accStatHashMap) {
        // Return as String is easier and saving more memory resource. 
        // Gson is no need Exception handler for dealing with JSON.
        return new Gson().toJson(accStatHashMap);
    }
   //Parse Accident GeoCoordinate Statistic into JSON Format.
    public String parseAccidentGeoCStatisticToJSON(List<GeoCoordinate> accGeoCList) {
        // Return as String is easier and saving more memory resource. 
        // Gson is no need Exception handler for dealing with JSON.
        return new Gson().toJson(accGeoCList);
    }
    
    /**
     * ***Debug Out*****
     */
    /**
     * ***Inner Class*****
     */
    private class GeoCoordinate {

        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

    }
}
