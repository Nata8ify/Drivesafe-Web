/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senior.g40.service.StatisticService;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.json.JSONException;

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
//        float f = 1234.65431123f;
//        System.out.println(String.format("%.5f", f));
//        System.out.println(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));
        StatisticService statService = StatisticService.getInstance();
//        System.out.println(StatisticService.getInstance().getQuantitiesOfAccidentViaDate(null, null));
//        System.out.println("isEmpty: " + (StatisticService.getInstance().getQuantitiesOfAccidentViaDate(Date.valueOf("2017-03-19"), new Date(System.currentTimeMillis())) == null));
//          Gson gson = new Gson();
//          String gsonAcc = gson.toJson((StatisticService.getInstance().getQuantitiesOfAccidentViaDate(Date.valueOf("2017-03-19"), new Date(System.currentTimeMillis()))));
//          System.out.println("gson: "+gsonAcc);
//        System.out.println("getTotalAccidentGeoStatistic() : " + StatisticService.getInstance().parseAccidentGeoCStatisticToJSON(StatisticService.getInstance().getTotalAccidentGeoStatistic()));
//        System.out.println("Stat TimeDay : " + statService.parseAccidentTimeDayStatisticToJSON(statService.getByDayTimePeriodOfAccidentStatistic(Date.valueOf("2017-03-19"))));
    }
}
