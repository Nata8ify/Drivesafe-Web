///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.senior.g40.test;
//
//import com.senior.g40.model.Accident;
//import com.senior.g40.model.Profile;
//import com.senior.g40.model.extras.LatLng;
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
//        us = UserService.getInstance();
//        as = AccidentService.getInstance();
//        ss = StatisticService.getInstance();
//        st = SettingService.getInstance();
//    }
//
//    /**
//     * UserService.java Test *
//     */
//    // ทดสอบสร้าง Instance User 
//    @Test
//    public void isUserInstanceCreated() {
//        assert us != null;
//    }
//
//    @Test
//    public void isRootLogin() {
//        assert (us.login("root", "master", 'T') != null);
//    }
//
//    //คนใช้งานทั่วไปสร้างบัญชี สำเร็จหรือไม่?
//    @Test
//    public void testUserSignUp() {
//        assert us.createAccount("UnitTest", "ByJUnit", 1522144141458L, "+6698752511", "Dummy Address 1", "Dummy Address 1", 36, 'M', "default", "kogy9i8u", 'M');
//    }
//
//    //ปิดบัญชีคนใช้งานทั่วไปและเจ้าหน้าที่ สำเร็จหรือไม่?
//    @Test
//    public void testDeleteAccount() {
//        assert us.deleteProfileByUserId(us.getLatestUserId()).isSuccess();
//    }
//
//    //คนใช้งานทั่วไปเข้าสู่ระบบ สำเร็จหรือไม่?
//    @Test
//    public void testUserLogin() {
//        Profile pf = us.login("user003", "user003", 'M');
//        assert pf.getUserId() == 14 && pf.getFirstName().equals("Pollen");
//    }
//
//    //ทีมงานกู้ชีพเข้าสู่ระบบ สำเร็จหรือไม่?
//    @Test
//    public void testRescuerLogin() {
//        Profile pf = us.login("root", "master", 'T');
//        assert pf.getUserId() == 1 && pf.getFirstName().equals("NATTAWUT");
//    }
//
//    //ทีมงานกู้ชีพออกจากระบบ สำเร็จหรือไม่?
//    // ทดสอบสร้าง Instance Accident 
//    @Test
//    public void isAccidentInstanceCreated() {
//        assert as != null;
//    }
//
//    /**
//     * AccidentService.java Test *
//     */
//    //บันทึกข้อมูลอุบัติเหตุรถชนลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testSaveCrashedAccident() {
//        Accident acc = new Accident(0, 12, new Date(System.currentTimeMillis()), "00:00", 13.646977424621582, 100.48725128173828, 0, 0, Byte.parseByte("1"), 'A');
//        assert acc != null;
//    }
//
//    //บันทึกข้อมูลเหตุอื่นลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testSaveNonCrashedAccident() {
//        Accident acc = new Accident(12, new Date(System.currentTimeMillis()), "00:00", 13.646977424621582, 100.48725128173828, Byte.parseByte("2"), 'A');
//        assert acc != null;
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุเป็นข้อมูลที่ผิดพลาดจากระบบ สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnSystemFalseAccc() {
//        assert as.updateOnSystemFalseAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุเป็นข้อมูลที่ผิดพลาดจากผู้ใช้งานเอง สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnUserFalseAccc() {
//        assert as.updateOnUserFalseAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุเป็นขอความช่วยเหลือ สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnRequestAccc() {
//        assert as.updateOnRequestRescueAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุว่ากำลังเดินทางไปจุดหมาย สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnGoingAccc() {
//        assert as.updateOnGoingAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุว่ากำลังให้ความช่วยเหลือ สำเร็จหรือไม่?
//    @Test
//    public void testUpdateOnRescueAccc() {
//        assert as.updateOnRescuingAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //แก้ไขข้อมูลของอุบัติเหตุว่าปิดภารกิจ สำเร็จหรือไม่?
//    @Test
//    public void testUpdateToClearAccc() {
//        assert as.updateClosedRescueAccc(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //ลบข้อมูลอุบัติเหตุหรือเหตุภัยรลงฐานข้อมูล สำเร็จหรือไม่?
//    @Test
//    public void testDeleteIncidentById() {
//        assert as.deleteIncidentById(as.getLatestAccidentId()).isSuccess();
//    }
//
//    //ทีมงานกู้ชีพเรียกดูอุบัติเหตุสถานะร้องขอความช่วยเหลือ, กำลังเข้าไปให้ความช่วยเหลือ และกำลังช่วยเหลือ ในวีนที่ปัจุบัน พบผลลัพธ์หรือไม่?
//    @Test
//    public void testGetCurrentDateAccidents() {
//        assert !as.getOnRequestAccidents().isEmpty();
//    }
//
//    //ทีมงานกู้ชีพเรียกดูอุบัติเหตุสถานะร้องขอความช่วยเหลือ, กำลังเข้าไปให้ความช่วยเหลือ และกำลังช่วยเหลือในวีนที่ปัจุบันที่อยู่ในเขตรับผิดชอบ 10 Kms. พบผลลัพธ์หรือไม่?
//    @Test
//    public void testGetCurrentDateInBoundAccidents() {
//        assert as.getCurrentDateInBoundAccidents(1) != null;
//    }
//
//    //ทีมงานกู้ชีพไม่พบอุบัติเหตุสถานะร้องขอความช่วยเหลือ, กำลังเข้าไปให้ความช่วยเหลือ และกำลังช่วยเหลือในวีนที่ปัจุบันที่อยู่ในเขตรับผิดชอบ 10 Kms. พบผลลัพธ์หรือไม่?
//    @Test
//    public void testGetEmptyCurrentDateInBoundAccidents() {
//        assert as.getCurrentDateInBoundAccidents(1) == null;
//    }
//   
//
//    /**
//     * StatisticService.java Test *
//     */
//    // ทดสอบสร้าง Instance Accident 
//    @Test
//    public void isStatisticInstanceCreated() {
//        assert ss != null;
//    }
//
//    //เรียกดูสถิติของเหตุทั้งหมดตามช่วง 7 วันว่ามีผลลัพธ์หรือไม่? เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetNumberOfAccidentViaByWeek() {
//        assert !ss.getNumberOfAccidentViaDate(Date.valueOf("2017-04-15"), Date.valueOf("2017-04-22")).isEmpty();
//    }
//
//    //เรียกดูสถิติของเหตุทั้งหมดตามช่วงวันที่กำหนดว่ามีผลลัพธ์หรือไม่? (เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetNumberOfAccidentViaDate() {
//        assert !ss.getNumberOfAccidentViaDate(Date.valueOf("2017-04-15"), Date.valueOf("2017-05-01")).isEmpty();
//    }
//
//    //เรียกดูสถิติของสถานที่ที่เกิดเหตุทั้งหมดตามช่วงวันที่กำหนดว่ามีผลลัพธ์หรือไม่? (เมื่อในฐานข้อมูลมี)
//    @Test
//    public void testGetByDatePeriodAccidentGeoStatistic() {
//        assert !ss.getByDatePeriodAccidentGeoStatistic(Date.valueOf("2017-04-15"), Date.valueOf("2017-05-01")).isEmpty();
//    }
//    
//    /**
//     * SettingService.java Test *
//     */
//    // ทดสอบสร้าง Instance Accident 
//    @Test
//    public void isSettingInstanceCreated() {
//        assert st != null;
//    }
//
//     
//    //เก็บบันทึกค่าที่ตั้งของสถานที่ปฏิบัติการณ์ว่าสำเร็จหรือไม่? เมื่อตั้งเขตรับผิดชอบเท่ากับ 15 kms
//    //พิกัด เท่ากับ Lat: 13.646977424621582, Lng: 100.48725128173828
//    @Test
//    public void testStoreOpertingLocation() {
//        assert st.storeOpertingLocation(new LatLng(13.646977424621582, 100.48725128173828)
//                , 5
//                , 10).isSuccess();
//    }
//
//    /* แก้ไขการตั้งค่าที่ตั้งของสถานที่ปฏิบัติการณ์โดยกำหนดระยะปฏิบัติการสองระดับว่าสำเร็จหรือไม่?  เมื่อแก้ไขเขตรับผิดชอบนอกเท่ากับ 15 kms และเขตรับผิดชอบในเท่ากับ 5 kms
//    พิกัด เท่ากับ Lat: 13.646977424621582, Lng: 100.48725128173828 */
//    @Test
//    public void testUpdate2LevelBoundOpertingLocation() {
//                assert st.update2LevelBoundOpertingLocation(new LatLng(13.646977424621582, 100.48725128173828)
//                , 15, 5, 10).isSuccess();
//    }
//
//    //แก้ไขการตั้งค่าที่ตั้งของสถานที่ปฏิบัติการณ์โดยกำหนดระยะปฏิบัติการระดับเดียวว่าสำเร็จหรือไม่? เมื่อแก้ไขเขตรับผิดชอบเท่ากับ 10 kms
//    //พิกัด เท่ากับ Lat: 13.646977424621582, Lng: 100.48725128173828
//    @Test
//    public void testUpdateBoundOpertingLocation() {
//                assert st.updateOpertingLocation(new LatLng(13.646977424621582, 100.48725128173828)
//                , 5
//                , 10).isSuccess();
//    }
//
//    //เรียกดูการตั้งค่าปัจจุบันว่าสำเร็จหรือไม่?
//    @Test
//    public void testGetOpertingLocation() {
//        assert st.getOpertingLocation(10).isSuccess();
//    }
//    /**
//     * *
//     */
//    /**
//     * *
//     */
//}
