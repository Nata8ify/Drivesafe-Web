/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model.extras;

import com.google.gson.Gson;

/**
 *
 * @author PNattawut
 */
public class OperatingLocation {
    private LatLng latLng;
    private int bound;

    public OperatingLocation(LatLng latLng, int bound) {
        this.latLng = latLng;
        this.bound = bound;
    }
    
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }
    
    public String toJSON(){
        return new Gson().toJson(this);
    }
}
