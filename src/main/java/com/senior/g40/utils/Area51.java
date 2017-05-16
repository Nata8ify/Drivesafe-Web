package com.senior.g40.utils;

import com.senior.g40.service.StatisticService;
import java.sql.Date;
import java.sql.SQLException;
import org.json.JSONException;
import org.junit.Test;

public class Area51 {

    @Test
    public static void main(String[] args) throws SQLException, JSONException {
        System.out.println(StatisticService.getInstance().getNumberOfCrashTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
        System.out.println(StatisticService.getInstance().getNumberOfFireTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
        System.out.println(StatisticService.getInstance().getNumberOfAnimalTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
        System.out.println(StatisticService.getInstance().getNumberOfPatientTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
        System.out.println(StatisticService.getInstance().getNumberAnotherTypeAccident(Date.valueOf("2017-05-01"), Date.valueOf("2017-05-16")));
    }
}
