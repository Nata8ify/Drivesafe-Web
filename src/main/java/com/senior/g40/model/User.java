/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model;

import com.google.gson.Gson;

/**
 *
 * @author PNattawut
 */
public class User {

    private long userId;
    private String username;
    private String password;
    private char userType;
    //User Type are specify by 
    //'T' is a Rescuer/Monitoring and 
    //'M' is a General User.

    private static User user;

    public User(String username, String password, char userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User() {
    }

    public static User getInsatance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", userType=" + userType + '}';
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
    
    public static final char TYPE_GENERAL_USER = 'M';
    public static final char TYPE_RESCUER_USER = 'T'; 
}
