/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.service;

import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import com.senior.g40.model.extras.Feed;
import com.senior.g40.utils.ConnectionBuilder;
import com.senior.g40.utils.ConnectionHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class FeedService {

    private static FeedService feedService;

    public static FeedService getInstance() {
        if (feedService == null) {
            FeedService.feedService = new FeedService();
        }
        return FeedService.feedService;
    }

    public void save(long userId, long accidentId, char updatedAccCode) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "INSERT INTO `feed`(`updatedAccCode`, `accidentId`, `userId`) VALUES (?, ?, ?);";
            pstm = conn.prepareStatement(sqlCmd);
            pstm.setString(1, String.valueOf(updatedAccCode));
            pstm.setLong(2, accidentId);
            pstm.setLong(3, userId);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, null);
        }
    }

    /**
     * @param date Specify Date of Feeds (Total Feeds by Default if 'date' is
     * null).
     */
    public List<Feed> getFeeds(Date date, Integer limit) {
        List<Feed> feeds = null;
        Feed feed = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = ConnectionBuilder.getConnection();
            String sqlCmd = "SELECT * FROM `feed`"
                    + (date == null ? "" : "WHERE DATE(`timestamp`) = ?")
                    + " ORDER BY feedId DESC"
                    + (limit==null?";":" LIMIT ?;");
            pstm = conn.prepareStatement(sqlCmd);
            System.out.println(sqlCmd);
            if (date != null) {
                pstm.setDate(1, date);
            }
            if (limit != null) {
                pstm.setInt(2, limit);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (feeds == null) {
                    feeds = new ArrayList<>();
                }
                feed = new Feed();
                long feedId = rs.getLong("feedId");
                char updatedAccCode = rs.getString("updatedAccCode").charAt(0);
                long accidentId = rs.getLong("accidentId");
                long userId = rs.getLong("userId");
                Timestamp timestamp =  rs.getTimestamp("timestamp");
                System.out.println(feedId+" : "+updatedAccCode+" : "+accidentId+" : "+userId+" : "+timestamp);
                feed.setFeedId(feedId);
                feed.setAccident(AccidentService.getInstance().getAccidentById(accidentId));
                feed.setTimestamp(timestamp);
                feed.setUpdatedAccCode(updatedAccCode);
                String userName = UserService.getInstance().getProfileByUserId(userId).getName();
                if(updatedAccCode!=Accident.ACC_CODE_A){
                    feed.setRscrName(userName);
                } else {
                    feed.setReporterName(userName);
                }
                feeds.add(feed);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionHandler.closeSQLProperties(conn, pstm, rs);
        }
        return feeds;
    }

    private void setFeed(ResultSet rs, Feed fd) throws SQLException {

    }

    private void setAccident(ResultSet rs, Accident ac) throws SQLException {
        ac.setAccidentId(rs.getLong("accidentId"));
        ac.setUserId(rs.getInt("userId"));
        ac.setDate(rs.getDate("date"));
        ac.setTime(rs.getString("time"));
        ac.setLatitude(rs.getFloat("latitude"));
        ac.setLongtitude(rs.getFloat("longitude"));
        ac.setAccCode(rs.getString("accCode").charAt(0));
        ac.setAccType(rs.getByte("accType"));
        ac.setResponsibleRescr(rs.getLong("responsibleRescr"));
    }

    private static void setProfile(ResultSet rs, Profile pf) throws SQLException {
        pf.setUserId(rs.getLong("userId"));
        pf.setFirstName(rs.getString("firstName"));
        pf.setLastName(rs.getString("lastName"));
        pf.setPersonalId(rs.getLong("personalId"));
        pf.setPhoneNumber(rs.getString("phone"));
        pf.setAddress1(rs.getString("address1"));
        pf.setAddress2(rs.getString("address2"));
        pf.setAge(rs.getInt("age"));
        pf.setGender(rs.getString("gender").charAt(0));
    }
}
