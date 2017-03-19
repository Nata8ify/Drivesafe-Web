/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Result saveAccident(Accident acc) {
        try {
            Result result = null;
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `accident` "
                    + "(`userId`, `date`, `time`, `latitude`, `longtitude`, `forceDetect`, `speedDetect`, `accCode`) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, acc.getUserId());
            pstm.setDate(2, acc.getDate());
            pstm.setString(3, acc.getTime());
            pstm.setFloat(4, acc.getLatitude());
            pstm.setFloat(5, acc.getLongtitude());
            pstm.setFloat(6, acc.getForceDetect());
            pstm.setFloat(7, acc.getSpeedDetect());
            pstm.setString(8, String.valueOf(Accident.ACC_CODE_A));
            if (pstm.executeUpdate() != 0) {
                sqlCmd = "SELECT * FROM `accident` WHERE accidentId = LAST_INSERT_ID();";
                pstm = conn.prepareStatement(sqlCmd);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    setAccident(rs, acc);
                    result = new Result(true, "Saved", acc);
                }
                conn.close();
                return result;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(false, ex);
        }
        return new Result(false, "saveAccident is NO EXCEPTION and row is 0 updated.");
    }

    //------------------------------------About INSERT/ADD. - END
    //------------------------------------About UPDATE. - START
    public Result updateOnRequestRescueAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_A);
    }

    public Result updateOnGoingAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_G);
    }

    public Result updateOnRescuingAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_R);
    }

    public Result updateClosedRescueAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_C);
    }

    public Result updateOnUserFalseAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_ERRU);
    }

    public Result updateOnSystemFalseAccc(long userId, long accId) {
        return updateAccCodeStatus(userId, Accident.ACC_CODE_ERRS);
    }

    public Result updateAccCodeStatus(long userId, char accCode) {
        try {
            Result result = null;
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE accident SET `accCode`= ? WHERE userId = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(accCode));
            pstm.setLong(2, userId);
            result = new Result(pstm.executeUpdate() != 0, "Update Success!");
            conn.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(false, "Update 'accCode' Failed", ex);
        }
    }

    //------------------------------------About UPDATE. - END
    //-------------------------------- About QUERY - START
    //should we have an area code for each rescue operation center?. [yes, we should.. USE IPGEOLOCATION]
    public List<Accident> getAllAccidents() {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident`;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getActiveAccidents() {
        // means "get any accident that still need for resuce or being rescues."
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode IN ('" + Accident.ACC_CODE_A
                    + "', '" + Accident.ACC_CODE_G
                    + "', '" + Accident.ACC_CODE_R + "');";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getOnRequestAccidents() {
        // means "get only accident that request for resuce immediately."
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = '" + Accident.ACC_CODE_A + "';";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getOnGoingForAccidents() {
        // means "get only accident that rescuer are o the way for rescue."
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = '" + Accident.ACC_CODE_G + "';";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getOnRescueAccidents() {
        // means "get only accident that rescuer are performing rescue on the accident area."
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE accCode = '" + Accident.ACC_CODE_R + "';";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Accident> getClosedAccidents() {
        // means "get only accident that rescuer are already rescued"
        return null;
    }

    public List<Accident> getAccidentByLatLng(float lat, float lng) {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident` WHERE latitude = ? AND longtitude = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setFloat(1, lat);
            pstm.setFloat(2, lng);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                accident = new Accident();
                if (accidents == null) {
                    accidents = new ArrayList<Accident>();
                }
                setAccident(rs, accident);
                accidents.add(accident);
            }
            return accidents;
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //-------------------------------- About QUERY - END
    //-------------------------------- Accident Value Setup
//    private static final DecimalFormat fmtLatLng = new DecimalFormat("#.####");
    private void setAccident(ResultSet rs, Accident ac) throws SQLException {
        ac.setUserId(rs.getInt("userId"));
        ac.setDate(rs.getDate("date"));
        ac.setTime(rs.getString("time"));
        ac.setLatitude(rs.getFloat("latitude"));
        ac.setLongtitude(rs.getFloat("longtitude"));
        ac.setForceDetect(rs.getFloat("forceDetect"));
        ac.setSpeedDetect(rs.getFloat("speedDetect"));
        ac.setAccCode(rs.getString("accCode").charAt(0));
        ac.setAccidentId(rs.getLong("accidentId"));
    }
//    --------------------------------- Dealing with JSON

    public JSONObject convertAccidentToJSON(Accident accident) {
        try {
            if (accident != null) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", accident.getUserId());
                jsonObj.put("date", accident.getDate());
                jsonObj.put("time", accident.getTime());
                jsonObj.put("latitude", Float.valueOf(accident.getLatitude()));
                jsonObj.put("longtitude", Float.valueOf(accident.getLongtitude()));
                jsonObj.put("forceDetect", Float.valueOf(accident.getForceDetect()));
                jsonObj.put("speedDetect", Float.valueOf(accident.getSpeedDetect()));
                jsonObj.put("accCode", accident.getAccCode());
                jsonObj.put("accidentId", accident.getAccidentId());
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    --------------------------------- Dealing with JSON
}
