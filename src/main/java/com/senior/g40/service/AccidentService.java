/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.utils.ConnectionBuilder;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PNattawut
 */
public class AccidentService {

    private static AccidentService accService;

    public static AccidentService getInstance() {
        if (accService == null) {
            accService = new AccidentService();
        }
        return accService;
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
            pstm.setLong(1, acc.getUserId());
            pstm.setDate(2, acc.getDate());
            pstm.setString(3, acc.getTime());
            pstm.setDouble(4, acc.getLatitude());
            pstm.setDouble(5, acc.getLongitude());
            pstm.setString(6, String.valueOf(Accident.ACC_CODE_A));
            pstm.setByte(7, Accident.ACC_TYPE_TRAFFIC);
            if (pstm.executeUpdate() != 0) {
                closeSQLProperties(conn, pstm, rs);
                sqlCmd = "SELECT * FROM `accident` WHERE accidentId = LAST_INSERT_ID();";
                rs = pstm.executeQuery();
                if (rs.next()) {
                    setAccident(rs, acc);
                    result = new Result(true, "Saved", acc);
                    sqlCmd = "INSERT INTO `crash_accdetails` (`accidentId`, `forceDetect`, `speedDetect`) "
                            + "VALUES (?, ?, ?);";
                    pstm = conn.prepareStatement(sqlCmd);
                    pstm.setLong(1, acc.getAccidentId());
                    pstm.setDouble(2, acc.getForceDetect());
                    pstm.setFloat(3, acc.getSpeedDetect());
                    pstm.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeSQLProperties(conn, pstm, rs);
        }
        boardcastRescueRequest(acc);
        return result;
    }

    public Result saveNonCrashAccident(Accident acc, byte accType) {
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
            pstm.setLong(1, acc.getUserId());
            pstm.setDate(2, acc.getDate());
            pstm.setString(3, acc.getTime());
            pstm.setDouble(4, acc.getLatitude());
            pstm.setDouble(5, acc.getLongitude());
            pstm.setString(6, String.valueOf(Accident.ACC_CODE_A));
            pstm.setByte(7, accType);
            if (pstm.executeUpdate() != 0) {
                closeSQLProperties(conn, pstm, rs);
                sqlCmd = "SELECT * FROM `accident` WHERE accidentId = LAST_INSERT_ID();";
                pstm = conn.prepareStatement(sqlCmd);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    setAccident(rs, acc);
                    result = new Result(true, "Saved", acc);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, ex);
        } finally {
            closeSQLProperties(conn, pstm, rs);
        }
        boardcastRescueRequest(acc);
        return result;
    }

    //------------------------------------About INSERT/ADD. - END
    //------------------------------------About UPDATE. - START
    public Result updateOnRequestRescueAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_A);
    }

    public Result updateOnGoingAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_G);
    }

    public Result updateOnRescuingAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_R);
    }

    public Result updateClosedRescueAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_C);
    }

    public Result updateOnUserFalseAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_ERRU);
    }

    public Result updateOnSystemFalseAccc(long accId) {
        return updateAccCodeStatus(accId, Accident.ACC_CODE_ERRS);
    }

    public Result updateAccCodeStatus(long userId, char accCode) {
        Connection conn = null;
        PreparedStatement pstm = null;
        Result result = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE accident SET `accCode`= ? WHERE accidentId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(accCode));
            pstm.setLong(2, userId);
            result = new Result(pstm.executeUpdate() != 0, "Update Success!");
        } catch (SQLException ex) {
            result = new Result(false, "Update 'accCode' Failed", ex);
        } finally {
            closeSQLProperties(conn, pstm, null);
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
            closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    //------------------------------------About DELETE. - END
    //-------------------------------- About QUERY - START
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
            closeSQLProperties(conn, pstm, rs);
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
            pstm.close();
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
            closeSQLProperties(conn, pstm, rs);
        }
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
            String sqlCmd = "SELECT * FROM `accident` WHERE date = ? AND  accCode IN (?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDate(1, today);
            pstm.setString(2, String.valueOf(Accident.ACC_CODE_A));
            pstm.setString(3, String.valueOf(Accident.ACC_CODE_G));
            pstm.setString(4, String.valueOf(Accident.ACC_CODE_R));
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
            resetOP();
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeSQLProperties(conn, pstm, rs);
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
            closeSQLProperties(conn, pstm, rs);
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
            closeSQLProperties(conn, pstm, rs);
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
            closeSQLProperties(conn, pstm, rs);
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
            closeSQLProperties(conn, pstm, rs);
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
            closeSQLProperties(conn, pstm, rs);
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
    private OperatingLocation ol;
    private final double DR = Math.PI / 180; //DEG_TO_RAD
    private final int RADIAN_OF_EARTH_IN_KM = 6371;

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
                closeSQLProperties(conn, pstm, rs);
            }
        }
        {
            // Haversine Formula Here. > http://www.movable-type.co.uk/scripts/latlong.html
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
        return isBoundWithin;
    }

    private void resetOP() {
        this.ol = null;
    }

    //Boardcast Rescue Request to Rescuer-Mobile Application [WheeWhor-Rescuer]
    private final String WW_FB_KEY_SERVER = "key=AAAAxdi1-iE:APA91bFgKGtyC8n5foSKwYdQfVDUjOZGT0yTv0JDOqDm7cLFOi1xnqnuG8FEmarC-iRsD3oYMr9iAt21WotVHgMZ1W6y0j2X1uCZPEv1h5mkh0hxoKrLtPgngE0Zjt0hZWCCIMlToCro";
    private final String TOPIC = "/topics/news";
    private final String FIXED_ = "euCknmtSS_Q:APA91bHHzC1QPn_uCtfMROGoHu22Gp2ipXuYsNSIkdew4yfR6JTGwSNSuxJrc5DCzzRku_k0OnpZIRi0HVOh6sucviNM69goOc7Gb45TRzjJC5X_Z8RlK_JikMnxxhfUaLyUobyKdWJ0";

    public Result boardcastRescueRequest(Accident acc) {
        Result result = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/fcm/send");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", WW_FB_KEY_SERVER);
        try {

            JSONObject message = new JSONObject();
            message.put("to", FIXED_); //<-- What App Auth>
            message.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", "Accident Alert!");
            notification.put("body", "Location : " + acc.getLatitude() + ", " + acc.getLongitude());
            message.put("notification", notification);
            httpPost.setEntity(new StringEntity(message.toString(), "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("Response : " + httpResponse);
            System.out.println("Message : " + message.toString());
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //Close the SQLProperties for preventing conection and memory leak.
    private void closeSQLProperties(Connection conn, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (conn != null) {
                conn.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    --------------------------------- Other
}
