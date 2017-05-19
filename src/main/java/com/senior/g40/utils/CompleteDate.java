package com.senior.g40.drivesafe.utils;

import java.text.SimpleDateFormat;

/**
 * Created by PNattawut on 19-May-17.
 */

public class CompleteDate {
    private static CompleteDate utilDate;
    private static java.util.Date useDate;
    private static SimpleDateFormat sdf;

    public static CompleteDate getInstance(){
        if(utilDate == null || useDate == null || sdf == null){
            utilDate = new CompleteDate();
            useDate = new java.util.Date(System.currentTimeMillis());
            sdf = new SimpleDateFormat();
        }
        return utilDate;
    }

    public String getToday(){
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(useDate);
    }

    public String getCurrentTime(){
        sdf.applyPattern("HH:mm");
        return sdf.format(useDate);
    }
}
