package com.senior.g40.utils;

public class App {

    public class Path {

        public static final String JSP_DIR = "/WEB-INF/jsp/";
        public static final String JSP_RESULT_DIR = "/WEB-INF/jsp/result/";
        public static final String JSP_RESULT_PAGE = "/WEB-INF/jsp/result/result.jsp";
        public static final String JSP_RESULT_PAGE_2 = "/WEB-INF/jsp/result/result2.jsp";
        public static final String JSP_RESULT_PAGE_3 = "/WEB-INF/jsp/result/result3.jsp";
        public static final String JSP_OTHER_DIR = "/WEB-INF/jsp/others/";
        public static final String IMGS_DIR = "/WEB-INF/repo/imgs/";
    }

    public class Attr {

        public static final String MESSAGE = "msg";
        public static final String RESULT = "result";
    }

    public class Const {

        public static final long DATE_WEEK = 1000 * 60 * 60 * 24 * 7l;
        public static final long DATE_WEEK_FOR_SQLCMD = 1000 * 60 * 60 * 24 * 6l; //just included today and 6 days before. 
        public static final String DATE_FMT = "yyyy-MM-dd";
    }

    public class Mode {

        public static final char VERBOSE = 'v';
        public static final char DEBUG = 'd';
        public static final char NORMAL = 'n';
    }

    public class Param {

        public static final String accidentId = "accidentId";
        public static final String responsibleRescr = "responsibleRescr";
        public static final String accCode = "accCode";
    }

    public class ErCode {
        public static final String ER01_ALREADY_REPORTED = "E01";
    }
}
