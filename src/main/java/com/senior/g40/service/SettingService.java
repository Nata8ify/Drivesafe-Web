/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class SettingService {

    private static SettingService settingService;
    
    public static SettingService getInstance() {
        if (settingService == null) {
            settingService = new SettingService();
        }
        return settingService;
    }
    
    public Result storeOpertingLocation(LatLng latLng, float boundRadius, long userId) {
        Result rs = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            String sqlCmd = "INSERT INTO `properties`(`opLat`, `opLng`, `opBound`, `userId`) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                rs = new Result(true, "Storing Operating Laocation accomplished.");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            rs = new Result(false, "Storing Operating Laocation failed.", ex);
        }
        return rs;
    }
    
    public Result updateOpertingLocation(LatLng latLng, float boundRadius, long userId) {
        Result result = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            String sqlCmd = "UPDATE `properties` SET `opLat`= ?,`opLng`= ?,`opBound`= ? WHERE `userId`= ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Operating Laocation updated.");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Operating Laocation update is failed.", ex);
        }
        return result;
    }
    
    public Result getOpertingLocation(long userId) {
        Result result = null;
        Connection conn = ConnectionBuilder.getConnection();
        try {
            String sqlCmd = "SELECT * FROM `properties` WHERE userId = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Result(true, "Getting Operating Laocation Success", new OperatingLocation(
                        new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                        rs.getInt("opBound")));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Getting Operating Laocation Failed.", ex);
        }
        return result;
    }
    
}
