/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.model.extras;

import com.google.gson.Gson;

/**
 *
 * @author PNattawut
 */
public class Organization {
    private int organizationId;
    private String organizationName;
    private String organizationDescription;

    public Organization(int organizationId, String organizationName, String organizationDescription) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationDescription = organizationDescription;
    }

    public Organization( String organizationName, String organizationDescription) {
        this.organizationName = organizationName;
        this.organizationDescription = organizationDescription;
    }
    
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    
    
    @Override
    public String toString() {
        return "Organization{" + "organizationId=" + organizationId + ", organizationName=" + organizationName + ", organizationDescription=" + organizationDescription + '}';
    }
    
    public String toJson(){
    return new Gson().toJson(this);
    }
}
