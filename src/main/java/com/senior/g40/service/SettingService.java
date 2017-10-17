/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.model.extras.Hospital;
import com.senior.g40.model.extras.HospitalDistance;
import com.senior.g40.model.extras.LatLng;
import com.senior.g40.model.extras.OperatingLocation;
import com.senior.g40.model.extras.Organization;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
import com.senior.g40.utils.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class SettingService {

    private static SettingService settingService;

    public static SettingService getInstance() {
        if (settingService == null) {
            settingService = new SettingService();
        }
        return settingService;
    }

    public Result storeOpertingLocation(LatLng latLng, float boundRadius, long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `properties`(`opLat`, `opLng`, `opNeutralBound`, `userId`) VALUES (?, ?, ?, ?)";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Storing Operating Location accomplished.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Storing Operating Location failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    public Result storeOpertingLocationandOrganization(LatLng latLng, float boundRadius, long userId, int organizationId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `properties`(`opLat`, `opLng`, `opNeutralBound`, `userId`, `opOrganization`) VALUES (?, ?, ?, ?, ?)";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            pstm.setInt(5, organizationId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Storing Operating Location accomplished.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Storing Operating Location failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    public Result updateOpertingLocation(LatLng latLng, float boundRadius, long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE `properties` SET `opLat`= ?,`opLng`= ?,`opNeutralBound`= ?, opMainBound = 0 WHERE `userId`= ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setDouble(3, boundRadius);
            pstm.setLong(4, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Operating Location updated. [" + latLng.getLatitude() + " , " + latLng.getLongitude() + "]");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Operating Location update is failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    public Result update2LevelBoundOpertingLocation(LatLng latLng, int nuetralBoundRadius, int mainBoundRadius, long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "UPDATE `properties` SET `opLat`= ?,`opLng`= ?,`opNeutralBound`= ?,`opMainBound`= ? WHERE `userId`= ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setDouble(1, latLng.getLatitude());
            pstm.setDouble(2, latLng.getLongitude());
            pstm.setInt(3, nuetralBoundRadius);
            pstm.setInt(4, mainBoundRadius);
            pstm.setLong(5, userId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Operating Location updated. [" + latLng.getLatitude() + " , " + latLng.getLongitude() + ", Resposible Bound (Main/Normal)" + mainBoundRadius + ":" + nuetralBoundRadius + "]");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Operating Location update is failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    public Result getOpertingLocation(long userId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `properties` WHERE userId = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Result(true, "Getting Operating Laocation Success", new OperatingLocation(
                        new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                        rs.getInt("opNeutralBound"), rs.getInt("opMainBound")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Getting Operating Location Failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return result;
    }

    public Result getAllOpertingLocation() {
        Result result = null;
        List<OperatingLocation> locations = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `properties` ORDER BY `properties`.`opOrganization` ASC;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (locations == null) {
                    locations = new ArrayList<>();
                }
                locations.add(new OperatingLocation(
                        new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                        rs.getInt("opNeutralBound"), rs.getInt("opMainBound"), rs.getInt("opOrganization"), rs.getLong("userId")));
            }
            result = new Result(true, "Getting Operating Laocation Success", locations);

        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(false, "Getting Operating Location Failed.", ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return result;
    }

    /* Organiaztion Module */
    public Result createOrganization(Organization organization) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "INSERT INTO `organization` (`organizationId`, `organizationName`, `organizationDesc`) VALUES (NULL, ?, ?);";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, organization.getOrganizationName());
            pstm.setString(2, organization.getOrganizationDescription());
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Organization is Created", getLatestOrganizationId(conn));
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Result updateOrganization(Organization organization) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "UPDATE `organization` SET ,`organizationName`= ? ,`organizationDesc`= ? WHERE `organizationId`= ?;";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, organization.getOrganizationName());
            pstm.setString(2, organization.getOrganizationDescription());
            pstm.setInt(3, organization.getOrganizationId());
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Organization is Created");
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Result deleteOrganization(int organizationId) {
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "DELETE FROM `organization` WHERE `organizationId`= ?;";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, organizationId);
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Organization is Deleted");
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Organization> getOrganizations() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Organization> organizations = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `organization`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (organizations == null) {
                    organizations = new ArrayList<>();
                }
                organizations.add(new Organization(rs.getInt("organizationId"), rs.getString("organizationName"), rs.getString("organizationDesc")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        System.out.println(organizations);
        return organizations;
    }

    public Organization getOrganizationById(int organizationId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Organization organization = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `organization` WHERE `organizationId` = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setInt(1, organizationId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                getOrganizationresult(rs, organization);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        System.out.println(organization);
        return organization;
    }

    public OperatingLocation getOperatingLocationByUserId(long userId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        OperatingLocation location = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `properties` WHERE `userId` = ?;";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setLong(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                location = new OperatingLocation(
                        new LatLng(rs.getDouble("opLat"), rs.getDouble("opLng")),
                        rs.getInt("opNeutralBound"), rs.getInt("opMainBound"), rs.getInt("opOrganization"), rs.getLong("userId"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return location;
    }

    public int getLatestOrganizationId(Connection conn) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sqlCmd = "SELECT LAST_INSERT_ID() FROM `organization`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return 0;
    }

    /* Hospital */
    public Result saveHospital(Hospital hospital) {
        Hospital nearByHospital = getNearByHospitalByHospital(hospital);
        if (nearByHospital != null) {
            addHospitalScore(nearByHospital.getHospitalId());
            return new Result(true, "Hospital Score is add", nearByHospital);
        }
        Result result = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "INSERT INTO `hospital`(`name`, `latitude`, `longitude`, `score`) VALUES (?, ?, ?, 1);";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, hospital.getName());
            pstm.setDouble(2, hospital.getLatitude());
            pstm.setDouble(3, hospital.getLongitude());
            if (pstm.executeUpdate() == 1) {
                result = new Result(true, "Hospital is Saved", hospital);
            }
        } catch (SQLException ex) {
            result = new Result(false, ex);
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return result;
    }

    public boolean addHospitalScore(int hospitalId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        boolean isDelSuccess = false;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "UPDATE `hospital` SET `score`= `score` + 1 WHERE `hospitalId` = ?;";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, hospitalId);
            isDelSuccess = pstm.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
        return isDelSuccess;
    }

    public boolean deleteHospital(int hospitalId){
       Connection conn = null;
        PreparedStatement pstm = null;
        boolean isDelSuccess = false;
        try {
            conn = ConnectionBuilder.getConnection();
            String sql = "DELETE FROM `hospital` WHERE `hospitalId` = ?;";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, hospitalId);
            isDelSuccess = pstm.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        } 
        return isDelSuccess;
    }
    
    public List<Hospital> getAllHospital() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Hospital> hospitals = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `hospital`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (hospitals == null) {
                    hospitals = new ArrayList<>();
                }
                hospitals.add(new Hospital(rs.getInt("hospitalId"), rs.getString("name"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("score")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return hospitals;
    }

    //    --------------------------------- Other
    private final double DR = Math.PI / 180; //DEG_TO_RAD
    private final int RADIAN_OF_EARTH_IN_KM = 6371;
    private final int RADIAN_OF_EARTH_IN_M = 6371000;
    private final double HOSPITAL_NEAR_RANGE = 100; //meters

    public Hospital getNearByHospitalByHospital(Hospital regisHospital) {

        // Haversine Formula Here. > http://www.movable-type.co.uk/scripts/latlong.html
        for (Hospital itrHospital : getAllHospital()) {
            double dLat = DR * (itrHospital.getLatitude() - regisHospital.getLatitude());
            double dLng = DR * (itrHospital.getLongitude() - regisHospital.getLongitude());
            double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2))
                    + (Math.cos(regisHospital.getLatitude() * DR) * Math.cos(itrHospital.getLatitude() * DR))
                    * (Math.sin(dLng / 2) * Math.sin(dLng / 2));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = c * RADIAN_OF_EARTH_IN_KM;
            System.out.println("Distance : " + distance);
            if (distance * 1000 < HOSPITAL_NEAR_RANGE) {
                return itrHospital;
            }
        }

        return null;
    }

    public List<HospitalDistance> getNearestHospitalByRscrPosition(LatLng latLng) {
        List<Hospital> hospitals = getAllHospital();
        if(hospitals == null){  return null;}
        List<HospitalDistance> hospitalDistances = null;
        for (Hospital itrHospital : hospitals) {
             if(hospitalDistances == null){hospitalDistances = new ArrayList<>();}
            double dLat = DR * (itrHospital.getLatitude() - latLng.getLatitude());
            double dLng = DR * (itrHospital.getLongitude() - latLng.getLongitude());
            double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2))
                    + (Math.cos(latLng.getLatitude() * DR) * Math.cos(itrHospital.getLatitude() * DR))
                    * (Math.sin(dLng / 2) * Math.sin(dLng / 2));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = c * RADIAN_OF_EARTH_IN_KM;
            hospitalDistances.add(new HospitalDistance(itrHospital, distance));
        }
        Collections.sort(hospitalDistances, new Comparator<HospitalDistance>() {
            @Override
            public int compare(HospitalDistance t, HospitalDistance t1) {
                return (int)t.getDistance() - (int)t1.getDistance();
            }
        });
        if(hospitalDistances.size() > 3){
            return hospitalDistances.subList(0, 3);
        }
        return hospitalDistances;
    }

    /* Object-relation Mapping */
    private void getOrganizationresult(ResultSet rs, Organization organization) throws SQLException {
        organization.setOrganizationId(rs.getInt("organizationId"));
        organization.setOrganizationName(rs.getString("organizationName"));
        organization.setOrganizationDescription(rs.getString("organizationDesc"));
    }
}
