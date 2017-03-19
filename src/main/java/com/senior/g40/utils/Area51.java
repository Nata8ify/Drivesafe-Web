/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.senior.g40.service.AccidentService;
import com.senior.g40.service.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PNattawut
 */
public class Area51 {

    enum accCode {
        Rs, Cs, Gs
    };

    // For test a thing.
    public static void main(String[] args) throws SQLException, JSONException {
        //        UserService.getInstance().login("root", "root", 'T');
        //          UserService.getInstance().createAccount("Tom", "Riddle", 1234567891011L, "+66845705977", "BLANK", "BLANK", 21, 'M', "user", "user", 'M');
        //        System.out.println(new UserService().getLatestUserId());
        //        System.out.println(UserService.getProfileByUserId(10).toString());
        //        JSONObject js = new JSONObject();
//        //        System.out.println(js);
//        ArrayList<Integer> nums = new ArrayList<Integer>();
//        nums.add(1);
//        nums.add(2);
//        nums.add(3);
//        nums.add(4);
//        Object obj = nums;
//        System.out.println(((ArrayList<Integer>) obj).toString());
//        System.out.println(accCode.Cs);
//        System.out.println(
//                AccidentService.getInstance().getOnRequestAccidents());
        float f = 1234.65431123f;
        System.out.println(String.format("%.5f", f));
    }
}
