/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.senior.g40.model.Accident;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.StatisticService;
import java.sql.Date;
import java.sql.SQLException;
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
//        StatisticService statService = StatisticService.getInstance();
//        AccidentService accidentService = AccidentService.getInstance();
//        System.out.println(StatisticService.getInstance().getQuantitiesOfAccidentViaDate(null, null));
//        System.out.println("isEmpty: " + (StatisticService.getInstance().getQuantitiesOfAccidentViaDate(Date.valueOf("2017-03-19"), new Date(System.currentTimeMillis())) == null));
//          Gson gson = new Gson();
//          String gsonAcc = gson.toJson((StatisticService.getInstance().getQuantitiesOfAccidentViaDate(Date.valueOf("2017-03-19"), new Date(System.currentTimeMillis()))));
//          System.out.println("gson: "+gsonAcc);
//        System.out.println("getTotalAccidentGeoStatistic() : " + St//                AccidentService.getInstance().getOnRequestAccidents());atisticService.getInstance().parseAccidentGeoCStatisticToJSON(StatisticService.getInstance().getTotalAccidentGeoStatistic()));
//        System.out.println("Stat TimeDay : " + statService.parseAccidentTimeDayStatisticToJSON(statService.getByDayTimePeriodOfAccidentStatistic(Date.valueOf("2017-03-19"))));
//        System.out.println(AccidentService.getInstance().getCurrentDateInBoundAccidents(1));
//System.out.println(((Accident)accidentService.saveCrashedAccident(new Accident(12, Date.valueOf("2017-04-30"), "11:22", 100.11104f,100.000f, 13, 13)).getObj()).toString());;
//        accidentService.boardcastRescueRequest(new Accident(12, Date.valueOf("2017-04-30"), "11:22", 100.11104f, 100.000f, 13, 13));
        System.out.println(Date.valueOf("2017-03-30").compareTo(Date.valueOf("2017-04-02")));
        System.out.println(Date.valueOf("2017-03-31").compareTo(Date.valueOf("2017-04-02")));
        System.out.println(Date.valueOf("2017-04-01").compareTo(Date.valueOf("2017-04-02")));
        System.out.println(Date.valueOf("2017-04-02").compareTo(Date.valueOf("2017-04-02")));
    }
}
