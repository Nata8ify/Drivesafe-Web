/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.test;

import com.senior.g40.model.Accident;
import com.senior.g40.service.AccidentService;
import java.sql.Date;
import org.junit.Test;

/**
 *
 * @author PNattawut
 */
public class AccidentServiceTestClass {

    private Accident tempAccident;
    private AccidentService ACC_SERVICE;

    public AccidentServiceTestClass() {
        ACC_SERVICE = AccidentService.getInstance();
    }

    @Test
    public void testStoreAccident() {
        tempAccident = (Accident) ACC_SERVICE.saveCrashedAccident(new Accident(12L, Date.valueOf("2017-05-01"), "23:50", 13.646636962890625, 100.48831176757812, (byte)1, 'A')).getObj();
        System.out.println(tempAccident.getAccidentId());
        ACC_SERVICE.deleteIncidentById(tempAccident.getAccidentId()).isSuccess();
        assert tempAccident != null;
    }

//    @Test
//    public void testDeleteAccident() {
//        assert ACC_SERVICE.deleteIncidentById(tempAccident.getAccidentId()).isSuccess();
//    }
}
