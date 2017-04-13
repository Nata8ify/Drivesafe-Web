/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.extras.LatLng;
import com.senior.g40.utils.Result;

/**
 *
 * @author PNattawut
 */
public class SettingService {
    private static SettingService settingService;
    
    public SettingService getInstance(){
        if(settingService == null){
            settingService = new SettingService();
        }
        return settingService;
    }
    
    public Result storeOpertingLocation(LatLng latLng, float boundRadius){
        return null;
    }
}
