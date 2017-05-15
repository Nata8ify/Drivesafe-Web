/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.senior.g40.model.Accident;
import com.senior.g40.service.AccidentService;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.json.JSONException;

/**
 *
 * @author PNattawut
 */
public class Area51 {

    // For test a thing.
    public static void main(String[] args) throws SQLException, JSONException {
        AccidentService accidentService = AccidentService.getInstance();
//        Date current = new Date(System.currentTimeMillis());
//        accidentService.boardcastRescueRequest(new Accident(12, current, new SimpleDateFormat("HH:mm").format(current), 100.11104f, 100.000f, 13, 13));
        System.out.println("accId "+accidentService.getLatestAccidentId());;
        System.out.println(accidentService.getCurrentDateInBoundAccidents(1));
    }
}
