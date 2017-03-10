/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.utils.ConnectionBuilder;
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
            String sqlCmd = "SELECT EXISTS(SELECT * FROM drivesafe.user WHERE username = ? AND password = ? AND userType = ?);";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, username);
            pstm.setString(2, password);
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
            long personalId, int phoneNumber, String address1, String address2, int age, char gender) {
        try {
            Connection conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `profile` "
                    + "(`firstName`, `lastName`, `personalId`, `phone`, `address1`, `address2`, `age`, `gender`) "
                    + "VALUES (?,  ?,  ?,  ?, ?, ?,  ?,  ?);";
            PreparedStatement pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setLong(3, personalId);
            pstm.setInt(4, phoneNumber);
            pstm.setString(5, address1);
            pstm.setString(6, address2);
            pstm.setInt(7, age);
            pstm.setString(8, String.valueOf(gender));
            if(pstm.executeUpdate() != 0){
                conn.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    private void createUser(String username, String password, char userType) {

    }
}
