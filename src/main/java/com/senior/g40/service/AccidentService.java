/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PNattawut
 */
public class AccidentService {
    
    
    private void setAccident(ResultSet rs, Accident ac) throws SQLException{
        ac.setUserId(rs.getInt(""));
        ac.setDate(rs.getDate(""));
        ac.setTime(rs.getString(""));
        ac.setLatitude(rs.getFloat(""));
        ac.setLongtitude(rs.getFloat(""));
        ac.setForceDetect(rs.getFloat(""));
        ac.setSpeedDetect(rs.getFloat(""));
        ac.setAccCode(rs.getString("").charAt(0));
    }
}
