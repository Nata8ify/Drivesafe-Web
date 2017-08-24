/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model.extras;

import com.senior.g40.model.Accident;
import com.senior.g40.model.Profile;
import java.sql.Timestamp;

/**
 *
 * @author PNattawut
 */
public class Feed {
    private long feedId;
    private char updatedAccCode;
    private String rscrName;
    private String reporterName;
    private Accident accident;
    private Timestamp timestamp;

    public long getFeedId() {
        return feedId;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
    }

    public char getUpdatedAccCode() {
        return updatedAccCode;
    }

    public void setUpdatedAccCode(char updatedAccCode) {
        this.updatedAccCode = updatedAccCode;
    }

    
    
    public String getRscrName() {
        return rscrName;
    }

    public void setRscrName(String rscrName) {
        this.rscrName = rscrName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Feed{" + "feedId=" + feedId + ", updatedAccCode=" + updatedAccCode + ", rscrName=" + rscrName + ", reporterName=" + reporterName + ", accident=" + accident + ", timestamp=" + timestamp + '}';
    }

    
}
