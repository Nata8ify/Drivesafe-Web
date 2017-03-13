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

    private long userId;
    private Date date;
    private String time;
    private float latitude;
    private float longtitude;
    private float forceDetect;
    private float speedDetect;
    //-- accCode is have very importance role.
    private char accCode;
    //A[Accident]: Pending for rescue, 
    //R[Resecue]: Rescuer is on the way, 
    //C[Clear]: Rescue received, marking will be cleared next time.  

    private static Accident accident;

    public Accident() {
    }

    public Accident(long userId, Date date, String time, float latitude, float longtitude, float forceDetect, float speedDetect, char accCode) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.forceDetect = forceDetect;
        this.speedDetect = speedDetect;
        this.accCode = accCode;
    }

    public static Accident getInsatance() {
        if (accident == null) {
            accident = new Accident();
        }
        return accident;
    }

    public long getUserId() {
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
