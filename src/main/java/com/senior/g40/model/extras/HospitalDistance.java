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
public class HospitalDistance {
    private Hospital hospital;
    private double distance;

    public HospitalDistance(Hospital hospital, double distance) {
        this.hospital = hospital;
        this.distance = distance;
    }
    
    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
}
