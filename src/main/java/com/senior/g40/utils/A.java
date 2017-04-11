/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

/**
 *
 * @author PNattawut
 */
public class A { // A = App
    public class Path{
        public static final String JSP_DIR = "/WEB-INF/jsp/";
        public static final String JSP_RESULT_DIR = "/WEB-INF/jsp/result/";
        public static final String IMGS_DIR = "/WEB-INF/repo/imgs/"; 
    }
    public class Attr{
        public static final String MESSAGE = "msg"; 
        public static final String RESULT = "result"; 
    }
    public class Const{
        public static final long DATE_WEEK = 1000*60*60*24*7l;
        public static final long DATE_WEEK_FOR_SQLCMD = 1000*60*60*24*6l; //just included today and 6 days before. 
    }
}
