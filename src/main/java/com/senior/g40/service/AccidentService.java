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
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
import com.senior.g40.utils.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PNattawut
 */
public class AccidentService {

    private List<Accident> currentDateBoundedAccidents;
    private List<Accident> currentDateAccidents;
    private static AccidentService accService;

    public static AccidentService getInstance() {
        if (accService == null) {
            accService = new AccidentService();
        }
        return accService;
    }

    public AccidentService() {
        currentDateBoundedAccidents = new ArrayList<>();
    }

    //------------------------------------About INSERT/ADD. - START
//Stage
    public Result saveCrashedAccident(Accident acc) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Result result = null;
        try {

            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `accident` "
                    + "(`userId`, `date`, `time`, `latitude`, `longitude`, `accCode`, `accType`) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            prepareIncidentStatement(pstm, acc);
            if (pstm.executeUpdate() != 0) {
                acc = getLatestAccident(conn);
                if (acc != null) {
                    result = new Result(true, "Crash Information Saved", acc);
                    saveFeed(acc.getUserId(), acc.getAccidentId(), Accident.ACC_CODE_A);
                    saveCrashDetail(acc.getAccidentId(), acc.getForceDetect(), acc.getSpeedDetect());
                }
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        boardcastRescueRequest(acc);
        return result;
    }

    public void saveCrashDetail(long accId, double forceDetect, float speedDetect) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCmd = "INSERT INTO `crash_accdetails` (`accidentId`, `forceDetect`, `speedDetect`) "
                + "VALUES (?, ?, ?);";
        try {
            conn = ConnectionBuilder.getConnection();
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, accId);
            pstm.setDouble(2, forceDetect);
            pstm.setFloat(3, speedDetect);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
    }

    public Result saveNonCrashAccident(Accident acc) {
        if (!isNotNearByDuplicate(new LatLng(acc.getLatitude(), acc.getLongitude()), acc.getAccType())) {
            return new Result(false, "E01 Incident is Already Reported", acc);
        }
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Result result = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `accident` "
                    + "(`userId`, `date`, `time`, `latitude`, `longitude`, `accCode`, `accType`) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            prepareIncidentStatement(pstm, acc);
            if (pstm.executeUpdate() != 0) {
                ConnectionHandler.closeSQLProperties(null, pstm, null);
                sqlCmd = "SELECT * FROM `accident` WHERE accidentId = LAST_INSERT_ID();";
                pstm = conn.prepareStatement(sqlCmd);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    setAccident(rs, acc);
                    saveFeed(acc.getUserId(), acc.getAccidentId(), Accident.ACC_CODE_A);
                    result = new Result(true, "Saved", acc);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        boardcastRescueRequest(acc);
        return result;
    }

    //------------------------------------About INSERT/ADD. - END
    //------------------------------------About UPDATE. - START
    public Result updateOnRequestRescueAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_A);
    }

    public Result updateOnGoingAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_G);
    }

    public Result updateOnRescuingAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_R);
    }

    public Result updateClosedRescueAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_C);
    }

    public Result updateOnUserFalseAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_ERRU);
    }

    public Result updateOnSystemFalseAccc(long rescuerId, long accId) {
        return updateAccCodeStatus(rescuerId, accId, Accident.ACC_CODE_ERRS);
    }

    public Result updateAccCodeStatus(long rescuerId, long accId, char accCode) {

        Connection conn = null;
        PreparedStatement pstm = null;
        Result result = null;
        try {
            Accident onUpdateCodeAccident = getAccidentById(accId);
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE accident SET `accCode`= ?, `responsibleRescr` = ? WHERE accidentId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(accCode));
            pstm.setLong(2, rescuerId);
            pstm.setLong(3, accId);
            if (pstm.executeUpdate() != 0) {
                result = new Result(true, "Update Success!");
                saveFeed(rescuerId, accId, accCode);
                //boardcastUpdateRescueRequest(onUpdateCodeAccident);
            }
        } catch (SQLException ex) {
            result = new Result(false, "Update 'accCode' Failed", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    //------------------------------------About UPDATE. - END
    //------------------------------------About DELETE. - START
    public Result deleteIncidentById(long accId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        Result result = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "DELETE FROM accident WHERE accidentId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, accId);
            result = new Result(pstm.executeUpdate() != 0, "Delete Success!");
        } catch (SQLException ex) {
            result = new Result(false, "Delete Failed", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    //------------------------------------About DELETE. - END
    //-------------------------------- About QUERY - START
    private Accident getLatestAccident(Connection conn) {
        Accident accident = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCmd;
        try {
            sqlCmd = "SELECT * FROM `accident` WHERE accidentId = LAST_INSERT_ID();";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (rs.next()) {
                accident = new Accident();
                setAccident(rs, accident);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(null, pstm, rs);
        }
        return accident;
    }

    public long getLatestAccidentId() {
        Connection conn = null;
        long accidentId = 0L;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCmd;
        try {
            conn = ConnectionBuilder.getConnection();
            sqlCmd = "SELECT MAX(accidentId) FROM `accident`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (rs.next()) {
                accidentId = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(null, pstm, rs);
        }
        return accidentId;
    }

    //Its name says anythings.
    public Accident getAccidentById(long accidentId) {
        Connection conn = null;
        Accident accident = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCmd;
        try {
            conn = ConnectionBuilder.getConnection();
            sqlCmd = "SELECT * FROM `accident` WHERE accidentId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, accidentId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                accident = new Accident();
                setAccident(rs, accident);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accident;
    }

    //should we have an area code for each rescue operation center?. [yes, we should.. USE IPGEOLOCATION]
    public List<Accident> getAllAccidents() {
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    public List<Accident> getCurrentDateAccidents() {
        // means "get only accident that occured today"
        Date today = new Date(System.currentTimeMillis());
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE date = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, today);
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        if (accidents == null) {
            return null;
        }
//        if(currentDateAccidents == null ){currentDateAccidents = new ArrayList<>();}
//        currentDateAccidents.clear();
//        currentDateAccidents.addAll(accidents);
        return accidents;
    }

    public List<Accident> getCurrentDateInBoundAccidents(long rescueSideUserId) {
        // means "get only accident that occured today"
        Date today = new Date(System.currentTimeMillis());
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE date = CURDATE() AND  accCode IN (?, ?, ?, ?) ORDER BY FIELD(`accCode`, 'A', 'G', 'R', 'C'), `time`";
            pstm = conn.prepareStatement(sqlCmd);
//            pstm.setDate(1, today);
            pstm.setString(1, String.valueOf(Accident.ACC_CODE_A));
            pstm.setString(2, String.valueOf(Accident.ACC_CODE_G));
            pstm.setString(3, String.valueOf(Accident.ACC_CODE_R));
            pstm.setString(4, String.valueOf(Accident.ACC_CODE_C));
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                if (isBoundWithin(rescueSideUserId, accident)) {
                    accidents.add(accident);
                }
            }
            currentDateBoundedAccidents.clear();
            currentDateBoundedAccidents.addAll(accidents);
            System.out.println(currentDateBoundedAccidents);
            resetOP();
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }

        return accidents;
    }

    public List<Accident> getActiveAccidents() {
        // means "get any accident that still need for resuce or being rescues."
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode IN (?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(Accident.ACC_CODE_A));
            pstm.setString(2, String.valueOf(Accident.ACC_CODE_G));
            pstm.setString(3, String.valueOf(Accident.ACC_CODE_R));
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    public List<Accident> getOnRequestAccidents() {
        // means "get only accident that request for resuce immediately."
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(Accident.ACC_CODE_A));
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    public List<Accident> getOnGoingForAccidents() {
        // means "get only accident that rescuer are o the way for rescue."
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(Accident.ACC_CODE_G));
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    public List<Accident> getOnRescueAccidents() {
        // means "get only accident that rescuer are performing rescue on the accident area."
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(Accident.ACC_CODE_R));
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    public List<Accident> getClosedAccidents() {
        // means "get only accident that rescuer are already rescued"
        return null;
    }

    public List<Accident> getAccidentByLatLng(float lat, float lng) {
        List<Accident> accidents = null;
        Accident accident = null;

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE latitude = ? AND longtitude = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setFloat(1, lat);
            pstm.setFloat(2, lng);
            rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return accidents;
    }

    //-------------------------------- About QUERY - END
    //-------------------------------- Accident Value Setup
//    private static final DecimalFormat fmtLatLng = new DecimalFormat("#.####");
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

    private void prepareIncidentStatement(PreparedStatement pstm, Accident acc) throws SQLException {
        pstm.setLong(1, acc.getUserId());
        pstm.setDate(2, acc.getDate());
        pstm.setString(3, acc.getTime());
        pstm.setDouble(4, acc.getLatitude());
        pstm.setDouble(5, acc.getLongitude());
        pstm.setString(6, String.valueOf(Accident.ACC_CODE_A));
        pstm.setByte(7, acc.getAccType());
    }
//    --------------------------------- Dealing with JSON

    public JSONObject convertAccidentToJSON(Accident accident) {
        try {
            if (accident != null) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", accident.getUserId());
                jsonObj.put("date", accident.getDate());
                jsonObj.put("time", accident.getTime());
                jsonObj.put("latitude", accident.getLatitude());
                jsonObj.put("longitude", accident.getLongitude());
//                jsonObj.put("forceDetect", Double.valueOf(accident.getForceDetect()));
//                jsonObj.put("speedDetect", Float.valueOf(accident.getSpeedDetect()));
                jsonObj.put("accCode", Character.valueOf(accident.getAccCode()));
                jsonObj.put("accType", accident.getAccType());
                jsonObj.put("accidentId", accident.getAccidentId());
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject convertAccidentToJSONForMonitorTable(Accident accident) {
        try {
            if (accident != null) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("date", accident.getDate());
                jsonObj.put("time", accident.getTime());
                jsonObj.put("accType", accident.getAccType());
                jsonObj.put("latitude", accident.getLatitude());
                jsonObj.put("longitude", accident.getLongitude());
                jsonObj.put("accCode", Character.valueOf(accident.getAccCode()));
                jsonObj.put("accidentId", accident.getAccidentId());
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    --------------------------------- Dealing with JSON
//    --------------------------------- Other
    private final double DR = Math.PI / 180; //DEG_TO_RAD
    private final int RADIAN_OF_EARTH_IN_KM = 6371;
    private final int RADIAN_OF_EARTH_IN_M = 6371000;
    private final double NEAR_RANGE = 30; //Meters

    public boolean isNotNearByDuplicate(LatLng latest, byte accType) { //<-- Untest
        currentDateAccidents = getCurrentDateAccidents();
        if (currentDateAccidents == null) {
            return true;
        }
        System.out.println(currentDateAccidents.toString());
        for (Accident accident : currentDateAccidents) {
            double dLat = DR * (accident.getLatitude() - latest.getLatitude());
            double dLng = DR * (accident.getLongitude() - latest.getLongitude());
            double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2))
                    + (Math.cos(latest.getLatitude() * DR) * Math.cos(accident.getLatitude() * DR))
                    * (Math.sin(dLng / 2) * Math.sin(dLng / 2));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = ((c * RADIAN_OF_EARTH_IN_M));
            System.out.println("distance " + distance);
            System.out.println("is NEAR_RANGE " + (distance < NEAR_RANGE));
            if (distance < NEAR_RANGE) {
                if (accType == accident.getAccType()) {
                    //if accCode Check?
                    return false;
                }
            }
        }
        return true;
    }

    private OperatingLocation ol;

    private boolean isBoundWithin(long userId, Accident acc) {
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
                double dLat = DR * (ol.getLatLng().getLatitude() - acc.getLatitude());
                double dLng = DR * (ol.getLatLng().getLongitude() - acc.getLongitude());
                double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2))
                        + (Math.cos(acc.getLatitude() * DR) * Math.cos(ol.getLatLng().getLatitude() * DR))
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

    //Boardcast Rescue Request to Rescuer-Mobile Application [WheeWhor-Rescuer]
    private final String KEY_SERVER = "key=AAAAxdi1-iE:APA91bFgKGtyC8n5foSKwYdQfVDUjOZGT0yTv0JDOqDm7cLFOi1xnqnuG8FEmarC-iRsD3oYMr9iAt21WotVHgMZ1W6y0j2X1uCZPEv1h5mkh0hxoKrLtPgngE0Zjt0hZWCCIMlToCro";
    private final String TOPIC_INCIDENT = "/topics/incident";
    private final String TOPIC_UPDATE_CODE = "/topics/update";

    public Result boardcastRescueRequest(Accident acc) {
        Result result = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/fcm/send");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", KEY_SERVER);
        try {

            JSONObject message = new JSONObject();
            message.put("to", TOPIC_INCIDENT); //<-- What App Auth>
            message.put("priority", "high");

            String bLocation = getReportFromByLatLng(new LatLng(acc.getLatitude(), acc.getLongitude()));

            JSONObject notification = new JSONObject();
            notification.put("title", getIncidentByType(acc.getAccType())+" Incident is Detected");
            notification.put("body", "Reported from : " + bLocation +" (Tap for more details)");
            notification.put("click_action", "Navigate");
            message.put("notification", notification);

            JSONObject data = new JSONObject();
            data.put("accidentId", acc.getAccidentId());
            data.put("date", acc.getDate());
            data.put("time", acc.getTime());
            data.put("latitude", acc.getLatitude());
            data.put("longitude", acc.getLongitude());
            data.put("accType", acc.getAccType());
            data.put("accCode", acc.getAccCode());
            data.put("userId", acc.getUserId());
            data.put("report_from", bLocation);
//            Add more for User Profile.
            message.put("data", data);

            httpPost.setEntity(new StringEntity(message.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            result = new Result(true, httpResponse.toString());
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Result boardcastUpdateRescueRequest(Accident acc) {
        Result result = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/fcm/send");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", KEY_SERVER);
        try {

            JSONObject message = new JSONObject();
            message.put("to", TOPIC_UPDATE_CODE);
            message.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", acc.getTime());
            notification.put("body", acc.getResponsibleRescr() + " now " + acc.getAccCode() + " on " + acc.getAccType());
            notification.put("click_action", "Navigate");
            message.put("notification", notification);

            JSONObject data = new JSONObject();
            data.put("accidentId", acc.getAccidentId());
            data.put("date", acc.getDate());
            data.put("time", acc.getTime());
            data.put("latitude", acc.getLatitude());
            data.put("longitude", acc.getLongitude());
            data.put("accType", acc.getAccType());
            data.put("accCode", acc.getAccCode());
            data.put("userId", acc.getUserId());
            data.put("responsibleRescr", acc.getResponsibleRescr());
//            Add more for User Profile.
            message.put("data", data);

            httpPost.setEntity(new StringEntity(message.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            result = new Result(true, httpResponse.toString());
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getReportFromByLatLng(LatLng latLng) {
        try {
            String latLngStr = latLng.getLatitude() + "," + latLng.getLongitude();
            String url = "http://maps.googleapis.com/maps/api/geocode/json?sensor=true&latlng=" + latLngStr;
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("Response : " + httpResponse);
            //String fmtAddress = new JSONObject(EntityUtils.toString(httpResponse.getEntity())).optString("formatted_address");
            String fmtAddress = new JSONObject(EntityUtils.toString(httpResponse.getEntity())).getJSONArray("results").getJSONObject(1).getString("formatted_address");
            return fmtAddress;
        } catch (IOException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Undefined Location (Open Navigator Map for see)";
    }

    private void saveFeed(long userId, long accidentId, char updatedAccCode) {
        FeedService.getInstance().save(userId, accidentId, updatedAccCode);
    }
//    --------------------------------- Other

    public String getIncidentByType(byte accType) {
        switch (accType) {
            case Accident.ACC_TYPE_TRAFFIC:
                return "Traffic";
            case Accident.ACC_TYPE_FIRE:
                 return "Fire";
            case Accident.ACC_TYPE_PATIENT:
                 return "Coma Patient or Serious Injuring";
            case Accident.ACC_TYPE_ANIMAL:
                return "Dangerous Animal";
            case Accident.ACC_TYPE_BRAWL:
                return "Brawl";
            case Accident.ACC_TYPE_OTHER :
                return  "Other";
        }
        return "Not Available";
    }

    public List<Accident> getBoundedAccidents() {
        return currentDateBoundedAccidents;
    }

    public void setBoundedAccidents(List<Accident> currentDateBoundedAccidents) {
        this.currentDateBoundedAccidents = currentDateBoundedAccidents;
    }

}
