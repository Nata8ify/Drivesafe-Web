///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.senior.g40.test;
//
//import com.senior.g40.model.Accident;
//import com.senior.g40.model.Profile;
//import com.senior.g40.model.User;
//import com.senior.g40.service.AccidentService;
//import com.senior.g40.service.SettingService;
//import com.senior.g40.service.StatisticService;
//import com.senior.g40.service.UserService;
//import java.sql.Date;
//import org.junit.Test;
//
///**
// *
// * @author PNattawut
// */
//public class TestClass {
//
//    private AccidentService as;
//    private StatisticService ss;
//    private UserService us;
//    private SettingService st;
//
//    public TestClass() {
//        as = AccidentService.getInstance();
//        ss = StatisticService.getInstance();
//        us = UserService.getInstance();
//        st = SettingService.getInstance();
//    }
//
////    @Test
////    public void isRootLogin(){
//////        assert(UserService.getInstance().login("root", "root", 'T'));
////    }
//    //คนใช้งานทั่วไปสร้างบัญชี สำเร็จหรือไม่?
//    @Test
//    public void testUserSignUp() {
//        assert true;
//    }
//
//    //คนใช้งานทั่วไปเข้าสู่ระบบ สำเร็จหรือไม่?
//    @Test
//    public void testUserLogin() {
//        Profile pf = us.login("user003", "user003", 'M');
//        System.out.println("User Profile : " + pf.toString());
//        assert pf.getUserId() == 14 && pf.getFirstName().equals("Pollen");
//    }
//
//    //ทีมงานกู้ชีพเข้าสู่ระบบ สำเร็จหรือไม่?
//    @Test
//    public void testRescuerLogin() {
//        Profile pf = us.login("root", "master", 'T');
//        System.out.println("Master Profile : " + pf.toString());
//        assert pf.getUserId() == 1 && pf.getFirstName().equals("NATTAWUT");
//    }
//
//    //ทีมงานกู้ชีพออกจากระบบ สำเร็จหรือไม่?
//    @Test
//    public void testMonitoringUserLogout() {
//        
//        assert true;
//    }
//
//    //บันทึกข้อมูลอุบัติเหตุรถชนลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testSaveCrashedAccident() {
//        Accident acc = new Accident(0, 12, new Date(System.currentTimeMillis()), "00:00", 13.646977424621582, 100.48725128173828, 0, 0, Byte.parseByte("1"), 'A');
//        assert as.saveCrashedAccident(acc).isSuccess();
//    }
//
//    //บันทึกข้อมูลเหตุอื่นลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testSaveNonCrashedAccident() {
//        assert true;
//    }
//    
//    //ลบข้อมูลอุบัติเหตุหรือเหตุภัยรลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testDeleteIncidentById() {
//        assert true;
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุเป็นข้อมูลที่ผิดพลาดจากระบบ สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnSystemFalseAccc() {
//        assert true;
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุเป็นข้อมูลที่ผิดพลาดจากผู้ใช้งานเอง สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnUserFalseAccc() {
//        assert true;
//    }
//
//    //ทีมงานกู้ชีพเรียกดูอุบัติเหตุสถานะร้องขอความช่วยเหลือ, กำลังเข้าไปให้ความช่วยเหลือ และกำลังช่วยเหลือ ในวีนที่ปัจุบัน พบผลลัพธ์หรือไม่?
//    @Test
//    public void testGetCurrentDateAccidents() {
//        assert true;
//    }
//
//    //ทีมงานกู้ชีพเรียกดูอุบัติเหตุสถานะร้องขอความช่วยเหลือ, กำลังเข้าไปให้ความช่วยเหลือ และกำลังช่วยเหลือในวีนที่ปัจุบันที่อยู่ในเขตรับผิดชอบ 10 Kms. พบผลลัพธ์หรือไม่?
//    @Test
//    public void testGetCurrentDateInBoundAccidents() {
//        assert true;
//    }
//
//    //เก็บบันทึกค่าที่ตั้งของสถานที่ปฏิบัติการณ์ว่าสำเร็จหรือไม่?
//    @Test
//    public void testStoreOpertingLocation() {
//        assert true;
//    }
//
//    //แก้ไขการตั้งค่าที่ตั้งของสถานที่ปฏิบัติการณ์โดยกำหนดระยะปฏิบัติการสองระดับว่าสำเร็จหรือไม่?
//    @Test
//    public void testUpdate2LevelBoundOpertingLocation() {
//        assert true;
//    }
//
//    //แก้ไขการตั้งค่าที่ตั้งของสถานที่ปฏิบัติการณ์โดยกำหนดระยะปฏิบัติการระดับเดียวว่าสำเร็จหรือไม่?
//    @Test
//    public void testUpdateBoundOpertingLocation() {
//        assert true;
//    }
//
//    //เรียกดูการตั้งค่าปัจจุบันว่าสำเร็จหรือไม่?
//    @Test
//    public void testGetOpertingLocation() {
//        assert true;
//    }
//
//    //เรียกดูสถิติของเหตุทั้งหมดตามช่วง 7 วันว่ามีผลลัพธ์หรือไม่? เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetNumberOfAccidentViaByWeek() {
//        assert true;
//    }
//
//    //เรียกดูสถิติของเหตุทั้งหมดตามช่วงวันที่กำหนดว่ามีผลลัพธ์หรือไม่? (เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetNumberOfAccidentViaDate() {
//        assert true;
//    }
//
//    //เรียกดูสถิติของสถานที่ที่เกิดเหตุทั้งหมดตามช่วงวันที่กำหนดว่ามีผลลัพธ์หรือไม่? (เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetByDatePeriodAccidentGeoStatistic() {
//        assert true;
//    }
//}
