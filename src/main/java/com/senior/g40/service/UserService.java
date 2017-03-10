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
                return true;
            } else { //Or else
                //TODO
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
