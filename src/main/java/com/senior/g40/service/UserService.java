/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.Encrypt;
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
public class UserService {

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public boolean login(String username, String password, char userType) {
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM drivesafe.user WHERE username = ? AND password = ? AND userType = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, username);
            pstm.setString(2, Encrypt.toMD5(password));
            pstm.setInt(3, userType);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {//If there is account existed.
                //TODO
                conn.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean createAccount(String firstName, String lastName,
            long personalId, String phoneNumber, String address1,
            String address2, int age, char gender,
            String username, String password, char userType) {
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `profile` "
                    + "(`firstName`, `lastName`, `personalId`, `phone`, `address1`, `address2`, `age`, `gender`) "
                    + "VALUES (?,  ?,  ?,  ?, ?, ?,  ?,  ?);";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setLong(3, personalId);
            pstm.setString(4, phoneNumber);
            pstm.setString(5, address1);
            pstm.setString(6, address2);
            pstm.setInt(7, age);
            pstm.setString(8, String.valueOf(gender));
            if (pstm.executeUpdate() != 0) {
                conn.close();
                createUser(username, password, userType);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean createUser(String username, String password, char userType) throws SQLException {
        Connection conn = ConnectionBuilder.getConnection();
        String sqlCmd = "INSERT INTO `user` (`userId`, `username`, `password`, `userType`) VALUES (?, ?, ?, ?);";
        PreparedStatement pstm = conn.prepareStatement(sqlCmd);
        pstm.setLong(1, getLatestUserId());
        pstm.setString(2, username);
        pstm.setString(3, Encrypt.toMD5(password));
        pstm.setString(4, String.valueOf(userType));
        if (pstm.executeUpdate() != 0) {
            conn.close();   
            return true;
        } else {
            return false;
        }
    }
    
    public boolean createAbsAccount(){return false;}
    
    private long getLatestUserId() throws SQLException{
        Connection conn = ConnectionBuilder.getConnection();
        String sqlCmd = "SELECT MAX(userId) AS latestId FROM `profile`;";
        PreparedStatement pstm = conn.prepareStatement(sqlCmd);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            long latestId = rs.getLong("latestId");
            conn.close();
            return latestId;
        } else {
            conn.close();
            throw new SQLException("Latest userId is N/A.");
        }
    }
}
