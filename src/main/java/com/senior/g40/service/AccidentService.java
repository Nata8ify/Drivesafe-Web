/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.utils.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean saveAccident(Accident acc) {
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `accident` "
                    + "(`userId`, `date`, `time`, `latitude`, `longtitude`, `accCode`, `forceDetect`, `speedDetect`) "
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
            pstm.setString(8, String.valueOf(acc.getAccCode()));
            if (pstm.executeUpdate() != 0) {
                conn.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //should we have an area code for each rescue operation center?. [yes, we should..]
    public List<Accident> getAllAccidents() {
        List<Accident> accidents = null;
        Accident accident = null;
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `accident`;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            ResultSet rs  = pstm.executeQuery();
            while(rs.next()){
                accident = new Accident();
                if(accidents == null){
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

    private void setAccident(ResultSet rs, Accident ac) throws SQLException {
        ac.setUserId(rs.getInt("userId"));
        ac.setDate(rs.getDate("date"));
        ac.setTime(rs.getString("time"));
        ac.setLatitude(rs.getFloat("latitude"));
        ac.setLongtitude(rs.getFloat("longtitude"));
        ac.setForceDetect(rs.getFloat("forceDetect"));
        ac.setSpeedDetect(rs.getFloat("speedDetect"));
        ac.setAccCode(rs.getString("accCode").charAt(0));
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
                jsonObj.put("longtitude", accident.getLongtitude());
                jsonObj.put("forceDetect", accident.getForceDetect());
                jsonObj.put("speedDetect", accident.getSpeedDetect());
                jsonObj.put("accCode", accident.getAccCode());
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    --------------------------------- Dealing with JSON
}
