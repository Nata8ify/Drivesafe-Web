/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Profile;
import com.senior.g40.model.User;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
import com.senior.g40.utils.Encrypt;
import com.senior.g40.utils.Result;
import java.lang.annotation.Documented;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class UserService {

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public Profile login(String username, String password, char userType) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Profile pf = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM user WHERE username = ? AND password = ? AND userType = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, username);
            pstm.setString(2, Encrypt.toMD5(password));
            pstm.setString(3, String.valueOf(userType));
            rs = pstm.executeQuery();
            if (rs.next()) {//If there is account existed.
                //TODO
                pf = getProfileByUserId(rs.getLong("userId"));
                if(userType == User.TYPE_RESCUER_USER){
                    sqlCmd = "SELECT * FROM `properties` WHERE `userId` = ?;";
                    pstm = conn.prepareStatement(sqlCmd);
                    pstm.setLong(1, pf.getUserId());
                    rs = pstm.executeQuery();
                    rs.first();
                    /* "rs.get..(NUMBER)" NUMBER is column number (start from 1) which can be use instead of column name  */
                    OperatingLocation.getInstance(new OperatingLocation(new LatLng(rs.getDouble(1), rs.getDouble(2)), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                    System.out.println(OperatingLocation.getInstance(null));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return pf;
    }

    // Need to improve the code -> too many parameters need to receive value as Profile Object instead.
    public boolean createAccount(String firstName, String lastName,
            long personalId, String phoneNumber, String address1,
            String address2, int age, char gender,
            String username, String password, char userType) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `profile` "
                    + "(`firstName`, `lastName`, `personalId`, `phone`, `address1`, `address2`, `age`, `gender`) "
                    + "VALUES (?,  ?,  ?,  ?, ?, ?,  ?,  ?);";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setLong(3, personalId);
            pstm.setString(4, phoneNumber);
            pstm.setString(5, address1);
            pstm.setString(6, address2);
            pstm.setInt(7, age);
            pstm.setString(8, String.valueOf(gender));
            if (pstm.executeUpdate() != 0) {
                ConnectionHandler.closeSQLProperties(conn, pstm, null);
                createUser(username, password, userType);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return false;
    }

    public boolean createUser(String username, String password, char userType) {
        Connection conn = null;
        PreparedStatement pstm = null;
        boolean isSuccess = false;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `user` (`userId`, `username`, `password`, `userType`) VALUES (?, ?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, getLatestUserId());
            pstm.setString(2, username);
            pstm.setString(3, Encrypt.toMD5(password));
            pstm.setString(4, String.valueOf(userType));
            if (pstm.executeUpdate() != 0) {
                ConnectionHandler.closeSQLProperties(conn, pstm, null);
                isSuccess = true;
            } else {
                ConnectionHandler.closeSQLProperties(conn, pstm, null);
                isSuccess = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return isSuccess;
    }

    public boolean createAbsAccount() {
        return false;
    }

    public long getLatestUserId() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long latestId = 0L;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT MAX(userId) AS latestId FROM `profile`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (rs.next()) {
                latestId = rs.getLong("latestId");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return latestId;
    }

    public Profile getProfileByUserId(long userId) {
        Profile pf = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `profile` WHERE userId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            rs = pstm.executeQuery();

            if (rs.next()) {
                pf = new Profile();
                setProfile(rs, pf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return pf;
    }

    public Result deleteProfileByUserId(long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "DELETE FROM `profile` WHERE userId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true);
            }
        } catch (SQLException ex) {
            result = new Result(false, ex.toString());
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm,null );
        }
        return result;
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

//    --------------------------------- Dealing with JSON
    public JSONObject convertUserToJSON(User user) {
        try {
            if (user != null) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", user.getUserId());
                jsonObj.put("username", user.getUsername());
                jsonObj.put("password", user.getPassword());
                jsonObj.put("userType", user.getUserType());
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject convertProfileToJSON(Profile profile) {
        try {
            if (profile != null) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", profile.getUserId());
                jsonObj.put("firstName", profile.getFirstName());
                jsonObj.put("lastName", profile.getLastName());
                jsonObj.put("personalId", profile.getPersonalId());
                jsonObj.put("phoneNumber", profile.getPhoneNumber());
                jsonObj.put("address1", profile.getAddress1());
                jsonObj.put("address2", profile.getAddress2());
                jsonObj.put("age", profile.getAge());
                jsonObj.put("gender", Character.valueOf(profile.getGender()));
                return jsonObj;
            }
        } catch (JSONException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    --------------------------------- Dealing with JSON

}
