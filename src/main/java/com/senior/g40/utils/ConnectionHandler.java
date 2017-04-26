/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.senior.g40.service.AccidentService;
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
public class ConnectionHandler {
    private static ConnectionHandler connectionHandler;
    public static ConnectionHandler getInstance(){
        if(connectionHandler == null){
            connectionHandler = new ConnectionHandler();
        }
        return connectionHandler;
    }
    //Close the SQLProperties for preventing conection and memory leak.
//    private void closeSQLProperties(Connection conn, PreparedStatement pstm, ResultSet rs) {
//        try {
//            if (conn != null) {
//                conn.close();
//            }
//            if (pstm != null) {
//                pstm.close();
//            }
//            if (rs != null) {
//                rs.close();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AccidentService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
