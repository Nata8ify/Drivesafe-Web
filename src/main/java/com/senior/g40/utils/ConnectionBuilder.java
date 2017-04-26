/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class ConnectionBuilder {

    public static java.sql.Connection getConnection() {
        java.sql.Connection con = null;

        try { //Connect to (Real)Database on Server First.
            String dbDriver = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost/drivesafe?useUnicode=true&characterEncoding=UTF-8";
            String user = "drvsafeany";
            String pw = "@Qwerty69";
            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = DriverManager.getConnection(dbUrl, user, pw);
        } catch (SQLException ex1) {
//            Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex1);
            System.out.println("Try... Connected with Localhost.");
            //If-Failed -> Connect to Localhost Database.
            String dbDriver = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost/drivesafe?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String pw = "";
            try {
                Class.forName(dbDriver);
                con = DriverManager.getConnection(dbUrl, user, pw);
            } catch (Exception ex) {
                Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        return con;
    }
}
