/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
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
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `properties`(`opLat`, `opLng`, `opNeutralBound`, `userId`) VALUES (?, ?, ?, ?)";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Storing Operating Location accomplished.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Storing Operating Location failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }
    
    public Result updateOpertingLocation(LatLng latLng, float boundRadius, long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE `properties` SET `opLat`= ?,`opLng`= ?,`opNeutralBound`= ?, opMainBound = 0 WHERE `userId`= ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Operating Location updated. ["+latLng.getLatitude()+" , "+latLng.getLongitude()+"]");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Operating Location update is failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }
    
    public Result update2LevelBoundOpertingLocation(LatLng latLng, int nuetralBoundRadius, int mainBoundRadius,long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE `properties` SET `opLat`= ?,`opLng`= ?,`opNeutralBound`= ?,`opMainBound`= ? WHERE `userId`= ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setInt(3, nuetralBoundRadius);
            pstm.setInt(4, mainBoundRadius);
            pstm.setLong(5, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Operating Location updated. ["+latLng.getLatitude()+" , "+latLng.getLongitude()+", Resposible Bound (Main/Normal)"+mainBoundRadius+":"+nuetralBoundRadius+"]");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Operating Location update is failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }
    
    public Result getOpertingLocation(long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `properties` WHERE userId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Result(true, "Getting Operating Laocation Success", new OperatingLocation(
                        new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                        rs.getInt("opNeutralBound"),rs.getInt("opMainBound") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Getting Operating Location Failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return result;
    }
}
