/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Profile;
import com.senior.g40.model.User;
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

    public Profile login(String username, String password, char userType) {
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM drivesafe.user WHERE username = ? AND password = ? AND userType = ?;";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, username);
            pstm.setString(2, Encrypt.toMD5(password));
            pstm.setString(3, String.valueOf(userType));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {//If there is account existed.
                //TODO
                System.out.println("LOGIN!");
                Profile pf = getProfileByUserId(rs.getLong("userId"));
                conn.close();
                return pf;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
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

    public boolean createAbsAccount() {
        return false;
    }

    private long getLatestUserId() throws SQLException {
        Connection conn = ConnectionBuilder.getConnection();
        String sqlCmd = "SELECT MAX(userId) AS latestId FROM `profile`;";
        PreparedStatement pstm = conn.prepareStatement(sqlCmd);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            long latestId = rs.getLong("latestId");
            conn.close();
            return latestId;
        } else {
            conn.close();
            throw new SQLException("Latest userId is N/A.");
        }
    }

    public static Profile getProfileByUserId(long userId) throws SQLException {
        Profile pf = null;
        
        Connection conn = ConnectionBuilder.getConnection();
        String sqlCmd = "SELECT * FROM `profile` WHERE userId = ?;";
        PreparedStatement pstm = conn.prepareStatement(sqlCmd);
        pstm.setLong(1, userId);
        ResultSet rs = pstm.executeQuery();
        
        if(rs.next()){
            pf = new Profile();
            setProfile(rs, pf);
            conn.close();
            return pf;
        }
        return null;
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

    private static void setUser(ResultSet rs, User usr) throws SQLException {
        usr.setUserId(rs.getLong(""));
        usr.setUsername(rs.getString(""));
        usr.setPassword(rs.getString(""));
        usr.setUserType(rs.getString("").charAt(0));
    }
}
