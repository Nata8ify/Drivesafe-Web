/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PNattawut
 */
public class Encrypt {

    private static final String SALT = "howitzer";

    public static String toMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] strBytes = str.getBytes();
//            md.update(SALT.getBytes());
            StringBuilder strBuilder = new StringBuilder();
            for(byte b : md.digest(strBytes)){
                strBuilder.append(String.format("%02x", b & 0xff));
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static String toSHA1(String str) {
        return "";
    }
}
