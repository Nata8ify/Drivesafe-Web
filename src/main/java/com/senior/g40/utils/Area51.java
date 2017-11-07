package com.senior.g40.utils;

import com.google.gson.Gson;
import com.senior.g40.model.Accident;
import com.senior.g40.model.extras.Feed;
import com.senior.g40.model.extras.Hospital;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.service.AccidentService;
import com.senior.g40.service.FeedService;
import com.senior.g40.service.SettingService;
import com.senior.g40.service.StatisticService;
import com.senior.g40.service.UserService;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.junit.Test;

public class Area51 {

    @Test
    public static void main(String[] args) throws SQLException, JSONException {
        Gson gson = new Gson();
//        System.out.println(StatisticService.getInstance().getNumberOfCrashTypeAccident(Date.valueOf("2017-05-17"), Date.valueOf("2017-05-23")));
//        System.out.println(StatisticService.getInstance().getNumberOfFireTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
//        System.out.println(StatisticService.getInstance().getNumberOfAnimalTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
//        System.out.println(StatisticService.getInstance().getNumberOfPatientTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
//        System.out.println(StatisticService.getInstance().getNumberAnotherTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
//        System.out.println(new Accident().toJson());
        //   System.out.println(new Gson().toJson(StatisticService.getInstance().getTotalAccidentLatLng(1L)));
//        int[] times = StatisticService.getInstance().getReportFreqSeries(null);
//        System.out.println(gson.toJson(times));
//        FeedService fs = FeedService.getInstance();
//        fs.save(14, 564, Accident.ACC_CODE_A);
//        fs.save(1, 564, Accident.ACC_CODE_G);
//        fs.save(14, 565, Accident.ACC_CODE_R);
//        fs.save(14, 563, Accident.ACC_CODE_A);
//        fs.save(1, 563, Accident.ACC_CODE_G);
//        fs.save(14, 562, Accident.ACC_CODE_R);
//        for(Feed feed : fs.getFeeds(new Date(System.currentTimeMillis()), 5)){
//            System.out.println(feed.toString());
//        }
//        System.out.println(FeedService.getInstance().getFeeds(new Date(System.currentTimeMillis()), 5));
//        System.out.println(AccidentService.getInstance().getRescuerInResponsibleAccident(1));
//        System.out.println(UserService.getInstance().getRescuerProfileByIncidentId(1611));
//        Hospital hospital = new Hospital();
//        hospital.setName("Kamppa");
//        hospital.setLatitude(14.007284);
//        hospital.setLongitude(99.974667);
//        Hospital hospital = new Hospital();
//        hospital.setName("โรงพยาบาล มนารมย์");
//        hospital.setLatitude(13.664788);
//        hospital.setLongitude(100.601728);
//        System.out.println(SettingService.getInstance().saveHospital(hospital));
//        System.out.println(SettingService.getInstance().getAllHospital());
 //       System.out.println(SettingService.getInstance().getNearestHospitalByRscrPosition(new LatLng(13.658289, 100.607360)));
        System.out.println(StatisticService.getInstance().getIncidentTypeByDatePeriod(Date.valueOf("2017-10-05"), Date.valueOf("2017-11-05")));
    }
}
