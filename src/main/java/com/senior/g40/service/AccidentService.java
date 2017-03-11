/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PNattawut
 */
public class AccidentService {

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
