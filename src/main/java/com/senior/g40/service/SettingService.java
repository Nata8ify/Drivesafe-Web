/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

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
                result = new Result(true, "Organization is Created");
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

    public int getLatestOrganizationId() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int organizationId = 0;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT LAST_INSERT_ID() FROM `organization`;";
            pstm = conn.prepareStatement(sqlCmd);
            rs = pstm.executeQuery();
            if (rs.next()) {
                organizationId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return organizationId;
    }

    /* Object-relation Mapping */
    private void getOrganizationresult(ResultSet rs, Organization organization) throws SQLException {
        organization.setOrganizationId(rs.getInt("organizationId"));
        organization.setOrganizationName(rs.getString("organizationName"));
        organization.setOrganizationDescription(rs.getString("organizationDesc"));
    }
}
