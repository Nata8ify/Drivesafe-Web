/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.google.gson.Gson;
import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.utils.App;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
                if (type != 0) {
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

    public List<DateTypeIncident> getIncidentTypeByDatePeriod(Date beginDate, Date lastDate) {
        List<DateTypeIncident> dtis = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCmd;
        try {
            sqlCmd = "SELECT date, accType, COUNT(accType) FROM accident WHERE accCode NOT IN('1', '2') AND (date BETWEEN ? AND ?) GROUP BY date, accType;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, beginDate);
            pstm.setDate(2, lastDate);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if(dtis == null){ dtis = new ArrayList<>();}
                dtis.add(new DateTypeIncident(rs.getDate(1), rs.getByte(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return dtis;
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

    public List<Accident> getMonthlyYearAccident(int month, int year) {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sql = "SELECT * FROM `accident` WHERE MONTH(`date`) = ? AND YEAR(`date`) = ?;";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, month);
            pstm.setInt(2, year);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                accident = new Accident();
                setAccident(rs, accident);
                accidents.add(accident);
            }
            conn.close();
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // 1 < dayOfWeek <= 7 && 1 < month <= 12
    public List<Accident> getDayOfWeekMonthlyAccident(int dayOfWeek, int month) {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sql = "SELECT * FROM `accident` WHERE DAYOFWEEK(`date`) = ? AND MONTH(`date`) = ?;";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, dayOfWeek);
            pstm.setInt(2, month);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                accident = new Accident();
                setAccident(rs, accident);
                accidents.add(accident);
            }
            conn.close();
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getDayOfWeekAccident(int dayOfWeek) {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sql = "SELECT * FROM `accident` WHERE DAYOFWEEK(`date`) = ?;";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, dayOfWeek);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                accident = new Accident();
                setAccident(rs, accident);
                accidents.add(accident);
            }
            conn.close();
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public HashMap<String, Integer> getDayOFWeekAccidentFreq(Integer month, Integer year) {
        HashMap<String, Integer> accidentsFreq = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sql = "SELECT DATE_FORMAT(`date`, '%D'), COUNT(*) FROM `accident`"
                    + (month != null || year != null ? " WHERE " : "")
                    + (month == null ? "" : " MONTH(`date`) = ?")
                    + (month != null && year != null ? " AND " : "")
                    + (year == null ? "" : " YEAR(`date`) = ?")
                    + " GROUP BY  DATE_FORMAT(`date`, '%D');";
            PreparedStatement pstm = conn.prepareStatement(sql);
            if (month != null) {
                pstm.setInt(1, month);
            }
            if (year != null) {
                pstm.setInt(1, year);
            }
            if (month != null && year != null) {
                pstm.setInt(1, month);
                pstm.setInt(2, year);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accidentsFreq == null) {
                    accidentsFreq = new HashMap<>();
                }
                accidentsFreq.put(rs.getString(1), rs.getInt(2));
            }
            conn.close();
            return accidentsFreq;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @param year Filter a result By Specifically year. (Default is included
     * result for every year)
     */
    public HashMap<String, Integer> getDayMonthlyAccidentFreq(Integer year) {
        HashMap<String, Integer> accidentsFreq = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sql = "SELECT DATE_FORMAT(`date`, '%D %M'), COUNT(*) FROM `accident`" + (year == null ? "" : " WHERE YEAR(`date`) = ?") + " GROUP BY  DATE_FORMAT(`date`, '%D %M');";
            PreparedStatement pstm = conn.prepareStatement(sql);
            if (year != null) {
                pstm.setInt(1, year);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (accidentsFreq == null) {
                    accidentsFreq = new HashMap<>();
                }
                accidentsFreq.put(rs.getString(1), rs.getInt(2));
            }
            conn.close();
            return accidentsFreq;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

            String sqlCmd = "SELECT latitude, longitude, accType FROM `accident` WHERE date BETWEEN ? AND ? AND accCode NOT IN('1', '2');";
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
                accGeoC.setAccType(rs.getByte("accType"));
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

    /**
     * date is optional
     *
     * @param date specifically date (current date as default value)
     */
    public List<Profile> getOnDutyRescuerProfiles(Date date) {
        List<Profile> onDutyRescuers = null;
        Profile profile = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT p.* FROM `profile` p JOIN `accident` a ON a.responsibleRescr = p.userId WHERE a.date = "
                    + date == null ? "CURDATE()" : "?"
                            + " AND a.accCode != 'A';";
            pstm = conn.prepareStatement(sqlCmd);
            if (date != null) {
                pstm.setDate(1, date);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (onDutyRescuers == null) {
                    onDutyRescuers = new ArrayList<>();
                }
                profile = new Profile();
                setProfile(rs, profile);
                onDutyRescuers.add(profile);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return onDutyRescuers;
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

    public ArrayList<Double[]> getTotalAccidentLatLng(Long userId) {
        ArrayList<Double[]> latLngList = null;
        Double[] latLng = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT `latitude`, `longitude` FROM `accident`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (userId != null) {
                while (rs.next()) {
                    if (latLngList == null) {
                        latLngList = new ArrayList<>();
                    }
                    latLng = new Double[]{rs.getDouble(1), rs.getDouble(2)};
                    if (isBoundWithin(userId, latLng[0], latLng[1])) {
                        latLngList.add(latLng);
                    }
                }
            } else {
                while (rs.next()) {
                    if (latLngList == null) {
                        latLngList = new ArrayList<>();
                    }
                    latLng = new Double[]{rs.getDouble(1), rs.getDouble(2)};
                    latLngList.add(latLng);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return latLngList;
    }

    public ArrayList<Double[]> getByDateAccidentLatLng(Date date, Long userId) {
        ArrayList<Double[]> latLngList = null;
        Double[] latLng = null;
        Connection conn = ConnectionBuilder.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT `latitude`, `longitude` FROM `accident` WHERE date = "
                    + (date == null ? "CURDATE();" : "?;");
            pstm = conn.prepareStatement(sqlCmd);
            if (date != null) {
                pstm.setDate(1, date);
            }
            rs = pstm.executeQuery();
            if (userId != null) {
                while (rs.next()) {
                    if (latLngList == null) {
                        latLngList = new ArrayList<>();
                    }
                    latLng = new Double[]{rs.getDouble(1), rs.getDouble(2)};
                    if (isBoundWithin(userId, latLng[0], latLng[1])) {
                        latLngList.add(latLng);
                    }
                }
            } else {
                while (rs.next()) {
                    if (latLngList == null) {
                        latLngList = new ArrayList<>();
                    }
                    latLng = new Double[]{rs.getDouble(1), rs.getDouble(2)};
                    latLngList.add(latLng);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return latLngList;
    }

    public Integer[] getStatusPercentage(Date date) {
        Integer[] statusCount = null;
        for (Accident acc : AccidentService.getInstance().getBoundedAccidents()) {
            if (statusCount == null) {
                statusCount = new Integer[]{0, 0, 0, 0}; //A G R C
            }
            switch (acc.getAccCode()) {
                case Accident.ACC_CODE_A:
                    statusCount[0] += 1;
                    break;
                case Accident.ACC_CODE_G:
                    statusCount[1] += 1;
                    break;
                case Accident.ACC_CODE_R:
                    statusCount[2] += 1;
                    break;
                case Accident.ACC_CODE_C:
                    statusCount[3] += 1;
                    break;
            }
        }
        return statusCount;
    }

    public int[] getReportFreqSeries(Date date) {
        int[] freqSeries = null;
        ArrayList<String> freqSeriesStrs = null;
//        Connection conn = ConnectionBuilder.getConnection();
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        try {
//            String sqlCmd = "SELECT `time` FROM `accident` WHERE `date` = "
//                    + (date == null ? "CURDATE()" : "?");
//            pstm = conn.prepareStatement(sqlCmd);
//            if (date != null) {
//                pstm.setDate(1, date);
//            }
//            rs = pstm.executeQuery();
//            while (rs.next()) {
//                if (freqSeriesStrs == null) {
//                    freqSeriesStrs = new ArrayList<>();
//                }
//                freqSeriesStrs.add(rs.getString(1));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
//        }
        for (Accident acc : AccidentService.getInstance().getBoundedAccidents()) {
            if (freqSeriesStrs == null) {
                freqSeriesStrs = new ArrayList<>();
            }
            freqSeriesStrs.add(acc.getTime());
        }
        if (freqSeriesStrs != null) {
            freqSeries = getPeriodReportRequently(freqSeriesStrs);
        }
        return freqSeries;
    }

    private int[] getPeriodReportRequently(ArrayList<String> times) {
        int[] series = new int[23];
        Time javaTime;
        for (String time : times) {
            javaTime = Time.valueOf(time.concat(":00"));
            if (javaTime.after(Time.valueOf("00:00:00")) && javaTime.before(Time.valueOf("01:00:00"))) {
                series[0] += 1;
            } else if (javaTime.after(Time.valueOf("01:00:00")) && javaTime.before(Time.valueOf("02:00:00"))) {
                series[1] += 1;
            } else if (javaTime.after(Time.valueOf("02:00:00")) && javaTime.before(Time.valueOf("03:00:00"))) {
                series[2] += 1;
            } else if (javaTime.after(Time.valueOf("03:00:00")) && javaTime.before(Time.valueOf("04:00:00"))) {
                series[3] += 1;
            } else if (javaTime.after(Time.valueOf("04:00:00")) && javaTime.before(Time.valueOf("05:00:00"))) {
                series[4] += 1;
            } else if (javaTime.after(Time.valueOf("05:00:00")) && javaTime.before(Time.valueOf("06:00:00"))) {
                series[5] += 1;
            } else if (javaTime.after(Time.valueOf("07:00:00")) && javaTime.before(Time.valueOf("08:00:00"))) {
                series[6] += 1;
            } else if (javaTime.after(Time.valueOf("08:00:00")) && javaTime.before(Time.valueOf("09:00:00"))) {
                series[7] += 1;
            } else if (javaTime.after(Time.valueOf("09:00:00")) && javaTime.before(Time.valueOf("10:00:00"))) {
                series[8] += 1;
            } else if (javaTime.after(Time.valueOf("10:00:00")) && javaTime.before(Time.valueOf("11:00:00"))) {
                series[9] += 1;
            } else if (javaTime.after(Time.valueOf("11:00:00")) && javaTime.before(Time.valueOf("12:00:00"))) {
                series[10] += 1;
            } else if (javaTime.after(Time.valueOf("12:00:00")) && javaTime.before(Time.valueOf("13:00:00"))) {
                series[11] += 1;
            } else if (javaTime.after(Time.valueOf("13:00:00")) && javaTime.before(Time.valueOf("14:00:00"))) {
                series[12] += 1;
            } else if (javaTime.after(Time.valueOf("14:00:00")) && javaTime.before(Time.valueOf("15:00:00"))) {
                series[13] += 1;
            } else if (javaTime.after(Time.valueOf("15:00:00")) && javaTime.before(Time.valueOf("16:00:00"))) {
                series[14] += 1;
            } else if (javaTime.after(Time.valueOf("16:00:00")) && javaTime.before(Time.valueOf("17:00:00"))) {
                series[15] += 1;
            } else if (javaTime.after(Time.valueOf("17:00:00")) && javaTime.before(Time.valueOf("18:00:00"))) {
                series[16] += 1;
            } else if (javaTime.after(Time.valueOf("18:00:00")) && javaTime.before(Time.valueOf("19:00:00"))) {
                series[17] += 1;
            } else if (javaTime.after(Time.valueOf("19:00:00")) && javaTime.before(Time.valueOf("20:00:00"))) {
                series[18] += 1;
            } else if (javaTime.after(Time.valueOf("20:00:00")) && javaTime.before(Time.valueOf("21:00:00"))) {
                series[19] += 1;
            } else if (javaTime.after(Time.valueOf("21:00:00")) && javaTime.before(Time.valueOf("22:00:00"))) {
                series[20] += 1;
            } else if (javaTime.after(Time.valueOf("22:00:00")) && javaTime.before(Time.valueOf("23:00:00"))) {
                series[21] += 1;
            } else {
                series[22] += 1;
            }
        }
        return series;
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

    private void setAccident(ResultSet rs, Accident ac) throws SQLException {
        ac.setAccidentId(rs.getLong("accidentId"));
        ac.setUserId(rs.getInt("userId"));
        ac.setDate(rs.getDate("date"));
        ac.setTime(rs.getString("time"));
        ac.setLatitude(rs.getFloat("latitude"));
        ac.setLongtitude(rs.getFloat("longitude"));
//        ac.setForceDetect(rs.getFloat("forceDetect"));
//        ac.setSpeedDetect(rs.getFloat("speedDetect"));
        ac.setAccCode(rs.getString("accCode").charAt(0));
        ac.setAccType(rs.getByte("accType"));
        ac.setResponsibleRescr(rs.getLong("responsibleRescr"));
    }

    private static void setProfile(ResultSet rs, Profile pf) throws SQLException {
        pf.setUserId(rs.getLong("userId"));
        pf.setFirstName(rs.getString("firstName"));
        pf.setLastName(rs.getString("lastName"));
        pf.setPersonalId(rs.getLong("personalId"));
        pf.setPhoneNumber(rs.getString("phone"));
        pf.setAddress1(rs.getString("address1"));
        pf.setAddress2(rs.getString("address2"));
        pf.setAge(rs.getInt("age"));
        pf.setGender(rs.getString("gender").charAt(0));
    }

    //    --------------------------------- Other
    private OperatingLocation ol;
    private final double DR = Math.PI / 180; //DEG_TO_RAD
    private final int RADIAN_OF_EARTH_IN_KM = 6371;

    //Minify Version
    private boolean isBoundWithin(long userId, double latitude, double longitude) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean isBoundWithin = false;
        if (ol == null) {
            try {
                conn = ConnectionBuilder.getConnection();
                String sqlCmd = "SELECT * FROM `properties` WHERE `userId` = ?;";
                pstm = conn.prepareStatement(sqlCmd);
                pstm.setLong(1, userId);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    ol = new OperatingLocation(new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                            rs.getInt("opNeutralBound"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ConnectionHandler.closeSQLProperties(conn, pstm, rs);
            }
        }
        {
            // Haversine Formula Here. > http://www.movable-type.co.uk/scripts/latlong.html
            if (ol != null) {
                int opBound = ol.getNeutralBound();
                double dLat = DR * (ol.getLatLng().getLatitude() - latitude);
                double dLng = DR * (ol.getLatLng().getLongitude() - longitude);
                double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2))
                        + (Math.cos(latitude * DR) * Math.cos(ol.getLatLng().getLatitude() * DR))
                        * (Math.sin(dLng / 2) * Math.sin(dLng / 2));
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double distance = c * RADIAN_OF_EARTH_IN_KM;
                if (distance < opBound) {
                    isBoundWithin = true;
                }
            }
        }
        return isBoundWithin;
    }

    private void resetOP() {
        this.ol = null;
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

        public GeoCoordinate(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public GeoCoordinate() {
        }

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

        private byte accType;

        public byte getAccType() {
            return accType;
        }

        public void setAccType(byte accType) {
            this.accType = accType;
        }
    }
    
    
    private class DateTypeIncident{
        private Date date;
        private byte accType;
        private int freqs;

        public DateTypeIncident(Date date, byte accType, int freqs) {
            this.date = date;
            this.accType = accType;
            this.freqs = freqs;
        }

        @Override
        public String toString() {
            return "DateTypeIncident{" + "date=" + date + ", accType=" + accType + ", freqs=" + freqs + '}';
        }
        
        
    }
}
