/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.google.gson.Gson;
import com.senior.g40.utils.A;
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
        LinkedHashMap<Date, Integer> accStatHashMap = null;
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

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
//                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ?;";
//                Only select accidents that isn't cause by UserFalse or SystemFalse.
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode NOT IN('0', '1');";
                pstm = conn.prepareStatement(sqlCmd);
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<Date, Integer>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    return accStatHashMap;
                }

                System.out.println(sdf.format(cal.getTime()) + " => " + rs.getInt(1));
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) != 1);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accStatHashMap;
    }

    // Get Number of False Accident Statistic (including UserFalse and SystemFalse) on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfFalseAccidentViaDate(Date beginDate, Date lastDate) {
        LinkedHashMap<Date, Integer> accStatHashMap = null;
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

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode IN('1', '2');";
                pstm = conn.prepareStatement(sqlCmd);
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<Date, Integer>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    return accStatHashMap;
                }

                System.out.println(sdf.format(cal.getTime()) + " => " + rs.getInt(1));
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) != 1);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accStatHashMap;
    }

    // Get Number of User False Accident Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfUserFalseAccidentViaDate(Date beginDate, Date lastDate) {
        LinkedHashMap<Date, Integer> accStatHashMap = null;
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

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode NOT IN('1');";
                pstm = conn.prepareStatement(sqlCmd);
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<Date, Integer>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    return accStatHashMap;
                }

                System.out.println(sdf.format(cal.getTime()) + " => " + rs.getInt(1));
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) != 1);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accStatHashMap;
    }

    // Get Number of System False Accident Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfSysFalseAccidentViaDate(Date beginDate, Date lastDate) {
        LinkedHashMap<Date, Integer> accStatHashMap = null;
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

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode NOT IN('2');";
                pstm = conn.prepareStatement(sqlCmd);
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<Date, Integer>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    return accStatHashMap;
                }

                System.out.println(sdf.format(cal.getTime()) + " => " + rs.getInt(1));
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
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
            while (rs.next()) {
                if (accGeoCList == null) {
                    accGeoCList = new ArrayList<GeoCoordinate>();
                }
                accGeoC = new GeoCoordinate();
                accGeoC.setLatitude(Double.valueOf(rs.getFloat("latitude")));
                accGeoC.setLongitude(Double.valueOf(rs.getFloat("longitude")));
                accGeoCList.add(accGeoC);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accGeoCList;
    }

    //Get Total Accident Location via GeoCoordinate.
    public List<GeoCoordinate> getWeekAccidentGeoStatistic() {
        List<GeoCoordinate> accGeoCList = null;
        GeoCoordinate accGeoC = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {

            String sqlCmd = "SELECT latitude, longitude FROM `accident` WHERE date BETWEEN ? AND ? AND accCode NOT IN('1', '2');";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, new Date(System.currentTimeMillis() - A.Const.DATE_WEEK_FOR_SQLCMD));
            pstm.setDate(2, new Date(System.currentTimeMillis()));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accGeoCList == null) {
                    accGeoCList = new ArrayList<GeoCoordinate>();
                }
                accGeoC = new GeoCoordinate();
                accGeoC.setLatitude(Double.valueOf(rs.getFloat("latitude")));
                accGeoC.setLongitude(Double.valueOf(rs.getFloat("longitude")));
                accGeoCList.add(accGeoC);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accGeoCList;
    }

    //Get Total Accident Location via GeoCoordinate.
    public List<GeoCoordinate> getByDatePeriodAccidentGeoStatistic(Date beginDate, Date lastDate) {
        List<GeoCoordinate> accGeoCList = null;
        GeoCoordinate accGeoC = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {

            String sqlCmd = "SELECT latitude, longitude FROM `accident` WHERE date BETWEEN ? AND ? AND accCode NOT IN('1', '2');";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, beginDate);
            pstm.setDate(2, lastDate);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accGeoCList == null) {
                    accGeoCList = new ArrayList<GeoCoordinate>();
                }
                accGeoC = new GeoCoordinate();
                accGeoC.setLatitude(Double.valueOf(rs.getFloat("latitude")));
                accGeoC.setLongitude(Double.valueOf(rs.getFloat("longitude")));
                accGeoCList.add(accGeoC);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accGeoCList;
    }

    //Get Statistic of Accident by Day Time Period.
    public List<String> getByDayTimePeriodOfAccidentStatistic(Date date) {
        List<String> byDateAccStatHassMap = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            String sqlCmd = "SELECT time FROM `accident` WHERE date = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, date);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (byDateAccStatHassMap == null) {
                    byDateAccStatHassMap = new ArrayList<String>();
                }
                byDateAccStatHassMap.add(rs.getString("time"));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byDateAccStatHassMap;
    }

    //Getting Statistic of Crash Speed detected by the app since the Drivesafe system was actived.
    public Map<Float, Integer> getTotalCrashSpeedStatistic() {
        Map<Float, Integer> crashSpeedMap = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            String sqlCmd = "SELECT `speedDetect`, COUNT(`speedDetect`) AS amount FROM `accident` GROUP BY `speedDetect` ORDER BY `speedDetect`;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (crashSpeedMap == null) {
                    crashSpeedMap = new LinkedHashMap<Float, Integer>();
                }
                crashSpeedMap.put(rs.getFloat("speedDetect"), rs.getInt("amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crashSpeedMap;
    }

    /**
     * ***Dealing with JSON***** Return as String is easier and saving more
     * memory resource. Gson is no need Exception handler for dealing with JSON.
     */
    //Parse Number of Accident Statistic on Specific Period Date into JSON Format.
    public String parseDateAccidentStatisticToJSON(Map<Date, Integer> accStatHashMap) {
        return new Gson().toJson(accStatHashMap);
    }

    //Parse Accident GeoCoordinate Statistic into JSON Format.
    public String parseAccidentGeoCStatisticToJSON(List<GeoCoordinate> accGeoCList) {
        return new Gson().toJson(accGeoCList);
    }

    //Parse Accident Day Time Statistic into JSON Format.
    public String parseAccidentDayTimeStatisticToJSON(List<String> timeDayAccList) {
        return new Gson().toJson(timeDayAccList);
    }

    //Parse Number of Accident Statistic on Specific Period Date into JSON Format.
    public String parseCrashSpeedStatisticToJSON(Map<Float, Integer> crashSpeedMap) {
        return new Gson().toJson(crashSpeedMap);
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
