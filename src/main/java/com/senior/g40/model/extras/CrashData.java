/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model.extras;

/**
 *
 * @author PNattawut
 */
public class CrashData {

    private long accidentId;
    private double forceDetect;
    private float speedDetect;

    public CrashData(long accidentId, double forceDetect, float speedDetect) {
        this.accidentId = accidentId;
        this.forceDetect = forceDetect;
        this.speedDetect = speedDetect;
    }

    
    
    public long getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(long accidentId) {
        this.accidentId = accidentId;
    }

    public double getForceDetect() {
        return forceDetect;
    }

    public void setForceDetect(double forceDetect) {
        this.forceDetect = forceDetect;
    }

    public float getSpeedDetect() {
        return speedDetect;
    }

    public void setSpeedDetect(float speedDetect) {
        this.speedDetect = speedDetect;
    }

    @Override
    public String toString() {
        return "CrashData{" + "accidentId=" + accidentId + ", forceDetect=" + forceDetect + ", speedDetect=" + speedDetect + '}';
    }
    
    
}
