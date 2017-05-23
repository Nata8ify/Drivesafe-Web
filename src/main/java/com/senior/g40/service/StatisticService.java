/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.google.gson.Gson;
import com.senior.g40.model.Accident;
import com.senior.g40.utils.App;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
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

    // Get Number All of Accident Type Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfAccidentViaDate(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Byte.valueOf("0"));
    }

    //    Get Number of Total Crashed/Traffic Accident Statistic.
    public HashMap<Date, Integer> getNumberOfCrashTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_TRAFFIC);
    }

    //    Get Number of Total Fire Statistic.
    public HashMap<Date, Integer> getNumberOfFireTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_FIRE);
    }

    //    Get Number of Total Brawl Statistic.
    public HashMap<Date, Integer> getNumberOfBrawlTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_BRAWL);
    }

    //    Get Number of Total Animal Statistic.
    public HashMap<Date, Integer> getNumberOfAnimalTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_ANIMAL);
    }

    //    Get Number of Total Patient Statistic.
    public HashMap<Date, Integer> getNumberOfPatientTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_PATIENT);
    }

    //    Get Number of Total Other Statistic.
    public HashMap<Date, Integer> getNumberAnotherTypeAccident(Date beginDate, Date lastDate) {
        return getNumberOfSelectedAccidentTypeViaDate(beginDate, lastDate, Accident.ACC_TYPE_OTHER);
    }

    // Get Number All of Accident Type Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfSelectedAccidentTypeViaDate(Date beginDate, Date lastDate, byte type) {
        LinkedHashMap<Date, Integer> accStatHashMap = null;
        Calendar cal = null;
        Date iterDate = null;
        SimpleDateFormat sdf = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cal = Calendar.getInstance();
            cal.setTime(beginDate);
            sdf = new SimpleDateFormat(App.Const.DATE_FMT);
            String sqlCmd;

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {

                if (type != 0) {
                    sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ?  AND accType = ? AND accCode NOT IN('0', '1');";
                    pstm = conn.prepareStatement(sqlCmd);
                } else {
                    sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode NOT IN('0', '1');";
                    pstm = conn.prepareStatement(sqlCmd);
                }
                pstm.setDate(1, iterDate);
                if(type != 0){
                    pstm.setByte(2, type);
                }

                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    ConnectionHandler.closeSQLProperties(conn, pstm, rs);
                    return accStatHashMap;
                }
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
                pstm.clearParameters();
            } while (iterDate.compareTo(lastDate) <= 0);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
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
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            cal = Calendar.getInstance();
            cal.setTime(beginDate);
            sdf = new SimpleDateFormat(App.Const.DATE_FMT);
            String sqlCmd;

            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode IN('1', '2');";
                if (pstm == null) {
                    pstm = conn.prepareStatement(sqlCmd);
                } else {
                    pstm.clearParameters();
                }
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    ConnectionHandler.closeSQLProperties(conn, pstm, rs);
                    return accStatHashMap;
                }
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) <= 0);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accStatHashMap;
    }

    // Get Number of User False Accident Statistic on Specific Period Date.  -- Still need to reform the code better.  
    public HashMap<Date, Integer> getNumberOfAccidentViaDateAndCode(Date beginDate, Date lastDate, char accCode) {
        LinkedHashMap<Date, Integer> accStatHashMap = null;
        Calendar cal = null;
        Date iterDate = null;
        SimpleDateFormat sdf = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cal = Calendar.getInstance();
            cal.setTime(beginDate);
            sdf = new SimpleDateFormat(App.Const.DATE_FMT);
            String sqlCmd;
            iterDate = Date.valueOf(sdf.format(cal.getTime()));
            do {
                sqlCmd = "SELECT COUNT(*) AS accCount FROM accident WHERE date = ? AND accCode IN(?);";
                if (pstm == null) {
                    pstm = conn.prepareStatement(sqlCmd);
                    pstm.setString(2, String.valueOf(accCode));
                } else {
                    pstm.clearParameters();
                }
                pstm.setDate(1, iterDate);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    if (accStatHashMap == null) {
                        accStatHashMap = new LinkedHashMap<>();
                    }
                    accStatHashMap.put(iterDate, rs.getInt(1));
                } else {
                    ConnectionHandler.closeSQLProperties(conn, pstm, rs);
                    return accStatHashMap;
                }
                cal.add(Calendar.DATE, 1);
                iterDate = Date.valueOf(sdf.format(cal.getTime()));
            } while (iterDate.compareTo(lastDate) <= 0);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accStatHashMap;
    }

    //Get Total Accident Location via GeoCoordinate.
    public List<GeoCoordinate> getWeekAccidentGeoStatistic() {
        return getByDatePeriodAccidentGeoStatistic(new Date(System.currentTimeMillis() - App.Const.DATE_WEEK_FOR_SQLCMD),
                new Date(System.currentTimeMillis()));
    }

    //Get Total Accident Location via GeoCoordinate.
    public List<GeoCoordinate> getByDatePeriodAccidentGeoStatistic(Date beginDate, Date lastDate) {
        List<GeoCoordinate> accGeoCList = null;
        GeoCoordinate accGeoC = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {

            String sqlCmd = "SELECT latitude, longitude FROM `accident` WHERE date BETWEEN ? AND ? AND accCode NOT IN('1', '2');";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, beginDate);
            pstm.setDate(2, lastDate);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (accGeoCList == null) {
                    accGeoCList = new ArrayList<>();
                }
                accGeoC = new GeoCoordinate();
                accGeoC.setLatitude(rs.getFloat("latitude"));
                accGeoC.setLongitude(rs.getFloat("longitude"));
                accGeoCList.add(accGeoC);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }

        return accGeoCList;
    }

    //Get Statistic of Accident by Day Time Period.
    public List<String> getByDayTimePeriodOfAccidentStatistic(Date date) {
        List<String> byDateAccStatHassMap = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT time FROM `accident` WHERE date = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, date);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (byDateAccStatHassMap == null) {
                    byDateAccStatHassMap = new ArrayList<String>();
                }
                byDateAccStatHassMap.add(rs.getString("time"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return byDateAccStatHassMap;
    }

    //Getting Statistic of Crash Speed detected by the app since the Drivesafe system was actived.
    public Map<Float, Integer> getTotalCrashSpeedStatistic() {
        Map<Float, Integer> crashSpeedMap = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT `speedDetect`, COUNT(`speedDetect`) AS amount FROM `accident` GROUP BY `speedDetect` ORDER BY `speedDetect`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (crashSpeedMap == null) {
                    crashSpeedMap = new LinkedHashMap<Float, Integer>();
                }
                crashSpeedMap.put(rs.getFloat("speedDetect"), rs.getInt("amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
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
