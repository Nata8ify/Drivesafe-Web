/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model;

import java.sql.Date;

/**
 *
 * @author PNattawut
 */
public class Accident {
    private int userId;
    private Date date;
    private String time;
    private float latitude;
    private float longtitude;
    private float forceDetect;
    private float speedDetect;
    private char accCode;

    private static Accident accident;
    public static Accident getInsatance(){
        if(accident == null){
            accident = new Accident();
        }
        return accident;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public float getForceDetect() {
        return forceDetect;
    }

    public void setForceDetect(float forceDetect) {
        this.forceDetect = forceDetect;
    }

    public float getSpeedDetect() {
        return speedDetect;
    }

    public void setSpeedDetect(float speedDetect) {
        this.speedDetect = speedDetect;
    }

    public char getAccCode() {
        return accCode;
    }

    public void setAccCode(char accCode) {
        this.accCode = accCode;
    }

    @Override
    public String toString() {
        return "Accident{" + "userId=" + userId + ", date=" + date + ", time=" + time + ", latitude=" + latitude + ", longtitude=" + longtitude + ", forceDetect=" + forceDetect + ", speedDetect=" + speedDetect + ", accCode=" + accCode + '}';
    }
    
    
}
